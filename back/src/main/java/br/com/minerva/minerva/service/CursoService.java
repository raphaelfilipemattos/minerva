package br.com.minerva.minerva.service;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.domain.Curso;
import br.com.minerva.minerva.domain.Empresa;
import br.com.minerva.minerva.model.CursoDTO;
import br.com.minerva.minerva.moodle.WebServiceMoodle;
import br.com.minerva.minerva.moodle.models.CategoriaMoodleModel;
import br.com.minerva.minerva.repos.CursoRepository;
import br.com.minerva.minerva.repos.CursoSalaRepository;
import br.com.minerva.minerva.repos.EmpresaRepository;
import br.com.minerva.minerva.util.NotFoundException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;
import java.util.UUID;

import br.com.minerva.minerva.util.Utils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final EmpresaRepository empresaRepository;
    @Autowired
    private WebServiceMoodle webServiceMoodle;

    @Autowired
    private CursoSalaRepository cursoSalaRepository;

    @Autowired
    private Ambiente ambiente;

    @Autowired
    private SalaService salaService;

    public CursoService(final CursoRepository cursoRepository,
                        final EmpresaRepository empresaRepository) {
        this.cursoRepository = cursoRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<CursoDTO> findAll() {
        final List<Curso> cursoes = cursoRepository.findAllByIdempresa(this.ambiente.getEmpresaAtual().getIdempresa());
        return cursoes.stream()
                .map(curso -> mapToDTO(curso, new CursoDTO()))
                .toList();
    }

    public CursoDTO get(final UUID idcurso) {
        var cursoLocalizado =  cursoRepository.findById(idcurso)
                    .map(curso -> mapToDTO(curso, new CursoDTO()))
                    .orElseThrow(NotFoundException::new);
        if (cursoLocalizado.getIdempresa() != this.ambiente.getEmpresaAtual().getIdempresa()){
            throw new RuntimeException("Curso não é dessa empresa");
        }

        return cursoLocalizado;
    }

    @Transactional
    public UUID create(final CursoDTO cursoDTO) {
        final Curso curso = new Curso();
        this.webServiceMoodle.setEmpresa(this.ambiente.getEmpresaAtual());
        CategoriaMoodleModel categoriaMoodleModel = new CategoriaMoodleModel();
        mapToEntity(cursoDTO, curso);
        curso.setEmpresa(this.ambiente.getEmpresaAtual());
        mapToCategoriaMoodleModel(curso, categoriaMoodleModel);
        categoriaMoodleModel = webServiceMoodle.criaCategria(categoriaMoodleModel);
        curso.setIdcourseMoodle(Integer.toUnsignedLong(categoriaMoodleModel.getId()));
        this.salveImage(cursoDTO.getImagemCapa(),curso);
        var idCurso = cursoRepository.save(curso).getIdcurso();

        this.salaService.criaAtualizaSala(cursoDTO, curso, this.webServiceMoodle);
        return idCurso;
    }

    public void update(final UUID idcurso, final CursoDTO cursoDTO) {
        final Curso curso = cursoRepository.findById(idcurso)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cursoDTO, curso);
        this.salveImage(cursoDTO.getImagemCapa(),curso);

        cursoRepository.save(curso);
        this.webServiceMoodle.setEmpresa(curso.getEmpresa());

        var categoriaMoodleModel = webServiceMoodle.getCategoriabyId(curso.getIdcourseMoodle().intValue());
        mapToCategoriaMoodleModel(curso, categoriaMoodleModel);
        webServiceMoodle.atualizaCategria(categoriaMoodleModel);
        curso.setIdcourseMoodle(Integer.toUnsignedLong(categoriaMoodleModel.getId()));

        this.salaService.criaAtualizaSala(cursoDTO, curso, this.webServiceMoodle);
    }

    public void delete(final UUID idcurso) {
        cursoRepository.deleteById(idcurso);
    }

    private CursoDTO mapToDTO(final Curso curso, final CursoDTO cursoDTO) {
        cursoDTO.setIdcurso(curso.getIdcurso());
        cursoDTO.setNomeCurso(curso.getNomeCurso());
        cursoDTO.setApelido(curso.getApelido());
        cursoDTO.setValor(curso.getValor());
        cursoDTO.setDescricaoCompleta(curso.getDescricaoCompleta());
        var cursoSala = this.cursoSalaRepository.findByIdCurso(curso.getIdcurso());
        cursoDTO.setDataIni(cursoSala.getDataIni());
        cursoDTO.setDataFim(cursoSala.getDataFim());
        cursoDTO.setAtivo(curso.getAtivo());
        if (curso.getImagemCapa() != null){
            cursoDTO.setImagemCapa("geral/imagem/curso/"+curso.getImagemCapa().toString());
        }
        cursoDTO.setIdempresa(curso.getEmpresa() == null ? null : curso.getEmpresa().getIdempresa());
        return cursoDTO;
    }

    private CategoriaMoodleModel mapToCategoriaMoodleModel(final Curso curso, final CategoriaMoodleModel categoriaMoodleModel) {

        //categoriaMoodleModel.setId(curso.getIdcourseMoodle().intValue());
        categoriaMoodleModel.setName(curso.getNomeCurso());
        categoriaMoodleModel.setDescription(curso.getDescricaoCompleta());
        categoriaMoodleModel.setParent(0);
        return categoriaMoodleModel;
    }

    private Curso mapToEntity(final CursoDTO cursoDTO, final Curso curso) {
        curso.setNomeCurso(cursoDTO.getNomeCurso());
        curso.setApelido(cursoDTO.getApelido());
        curso.setValor(cursoDTO.getValor());
        curso.setDescricaoCompleta(cursoDTO.getDescricaoCompleta());

       /* final Empresa idempresa = cursoDTO.getIdempresa() == null ? null : empresaRepository.findById(cursoDTO.getIdempresa())
                .orElseThrow(() -> new NotFoundException("idempresa not found"));
        curso.setEmpresa(idempresa);

        */
        if (cursoDTO.getAtivo() == null ){
            curso.setAtivo(true);
        }else {
            curso.setAtivo(cursoDTO.getAtivo());
        }
        return curso;
    }

    private void salveImage(String imageBase64, Curso curso){
        var idImg = curso.getImagemCapa() == null ? UUID.randomUUID(): curso.getImagemCapa();
        if (imageBase64 !=null){
            Utils.ImageToBase64(imageBase64,Ambiente.localCapaCurso(),idImg);
        }
        curso.setImagemCapa(idImg);
    }

}
