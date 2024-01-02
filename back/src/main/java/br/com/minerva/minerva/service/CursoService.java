package br.com.minerva.minerva.service;

import br.com.minerva.minerva.domain.Curso;
import br.com.minerva.minerva.domain.Empresa;
import br.com.minerva.minerva.model.CursoDTO;
import br.com.minerva.minerva.moodle.WebServiceMoodle;
import br.com.minerva.minerva.moodle.models.CategoriaMoodleModel;
import br.com.minerva.minerva.moodle.models.CursoMoodleModel;
import br.com.minerva.minerva.repos.CursoRepository;
import br.com.minerva.minerva.repos.EmpresaRepository;
import br.com.minerva.minerva.util.NotFoundException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final EmpresaRepository empresaRepository;
    @Autowired
    private WebServiceMoodle webServiceMoodle;

    public CursoService(final CursoRepository cursoRepository,
                        final EmpresaRepository empresaRepository) {
        this.cursoRepository = cursoRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<CursoDTO> findAll() {
        final List<Curso> cursoes = cursoRepository.findAll(Sort.by("idcurso"));
        return cursoes.stream()
                .map(curso -> mapToDTO(curso, new CursoDTO()))
                .toList();
    }

    public CursoDTO get(final UUID idcurso) {
        return cursoRepository.findById(idcurso)
                .map(curso -> mapToDTO(curso, new CursoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final CursoDTO cursoDTO) {
        final Curso curso = new Curso();
        final Empresa empresa = this.empresaRepository.getReferenceById(cursoDTO.getIdempresa());
        this.webServiceMoodle.setEmpresa(empresa);
        CategoriaMoodleModel categoriaMoodleModel = new CategoriaMoodleModel();
        mapToEntity(cursoDTO, curso);
        mapToCategoriaMoodleModel(curso, categoriaMoodleModel);
        categoriaMoodleModel = webServiceMoodle.criaCategria(categoriaMoodleModel);
        curso.setIdcourseMoodle(Integer.toUnsignedLong(categoriaMoodleModel.getId()));
        return cursoRepository.save(curso).getIdcurso();
    }

    public void update(final UUID idcurso, final CursoDTO cursoDTO) {
        final Curso curso = cursoRepository.findById(idcurso)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cursoDTO, curso);
        cursoRepository.save(curso);
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
        cursoDTO.setIdempresa(curso.getIdempresa() == null ? null : curso.getIdempresa().getIdempresa());
        return cursoDTO;
    }

    private CategoriaMoodleModel mapToCategoriaMoodleModel(final Curso curso, final CategoriaMoodleModel categoriaMoodleModel) {

        //categoriaMoodleModel.setId(curso.getIdcourseMoodle().intValue());
        categoriaMoodleModel.setName(curso.getNomeCurso());
        categoriaMoodleModel.setDescricao(curso.getDescricaoCompleta());
        categoriaMoodleModel.setParent(0);
        return categoriaMoodleModel;
    }

    private Curso mapToEntity(final CursoDTO cursoDTO, final Curso curso) {
        curso.setNomeCurso(cursoDTO.getNomeCurso());
        curso.setApelido(cursoDTO.getApelido());
        curso.setValor(cursoDTO.getValor());
        curso.setDescricaoCompleta(cursoDTO.getDescricaoCompleta());
        final Empresa idempresa = cursoDTO.getIdempresa() == null ? null : empresaRepository.findById(cursoDTO.getIdempresa())
                .orElseThrow(() -> new NotFoundException("idempresa not found"));
        curso.setIdempresa(idempresa);
        return curso;
    }

}
