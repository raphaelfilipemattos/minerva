package br.com.minerva.minerva.service;

import br.com.minerva.minerva.domain.Curso;
import br.com.minerva.minerva.domain.Inscricao;
import br.com.minerva.minerva.domain.Usuario;
import br.com.minerva.minerva.model.InscricaoDTO;
import br.com.minerva.minerva.repos.CursoRepository;
import br.com.minerva.minerva.repos.InscricaoRepository;
import br.com.minerva.minerva.repos.UsuarioRepository;
import br.com.minerva.minerva.util.NotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    public InscricaoService(final InscricaoRepository inscricaoRepository,
            final CursoRepository cursoRepository, final UsuarioRepository usuarioRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<InscricaoDTO> findAll() {
        final List<Inscricao> inscricaos = inscricaoRepository.findAll(Sort.by("idinscricao"));
        return inscricaos.stream()
                .map(inscricao -> mapToDTO(inscricao, new InscricaoDTO()))
                .toList();
    }

    public InscricaoDTO get(final UUID idinscricao) {
        return inscricaoRepository.findById(idinscricao)
                .map(inscricao -> mapToDTO(inscricao, new InscricaoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final InscricaoDTO inscricaoDTO) {
        final Inscricao inscricao = new Inscricao();
        mapToEntity(inscricaoDTO, inscricao);
        return inscricaoRepository.save(inscricao).getIdinscricao();
    }

    public void update(final UUID idinscricao, final InscricaoDTO inscricaoDTO) {
        final Inscricao inscricao = inscricaoRepository.findById(idinscricao)
                .orElseThrow(NotFoundException::new);
        mapToEntity(inscricaoDTO, inscricao);
        inscricaoRepository.save(inscricao);
    }

    public void delete(final UUID idinscricao) {
        inscricaoRepository.deleteById(idinscricao);
    }

    private InscricaoDTO mapToDTO(final Inscricao inscricao, final InscricaoDTO inscricaoDTO) {
        inscricaoDTO.setIdinscricao(inscricao.getIdinscricao());
        inscricaoDTO.setData(inscricao.getData());
        inscricaoDTO.setHora(inscricao.getHora());
        inscricaoDTO.setSeq(inscricao.getSeq());
        inscricaoDTO.setIdentificador(inscricao.getIdentificador());
        inscricaoDTO.setIdcurso(inscricao.getIdcurso() == null ? null : inscricao.getIdcurso().getIdcurso());
        inscricaoDTO.setIdusuario(inscricao.getIdusuario() == null ? null : inscricao.getIdusuario().getIdusuario());
        return inscricaoDTO;
    }

    private Inscricao mapToEntity(final InscricaoDTO inscricaoDTO, final Inscricao inscricao) {
        inscricao.setData(inscricaoDTO.getData());
        inscricao.setHora(inscricaoDTO.getHora());
        inscricao.setSeq(inscricaoDTO.getSeq());
        inscricao.setIdentificador(inscricaoDTO.getIdentificador());
        final Curso idcurso = inscricaoDTO.getIdcurso() == null ? null : cursoRepository.findById(inscricaoDTO.getIdcurso())
                .orElseThrow(() -> new NotFoundException("idcurso not found"));
        inscricao.setIdcurso(idcurso);
        final Usuario idusuario = inscricaoDTO.getIdusuario() == null ? null : usuarioRepository.findById(inscricaoDTO.getIdusuario())
                .orElseThrow(() -> new NotFoundException("idusuario not found"));
        inscricao.setIdusuario(idusuario);
        return inscricao;
    }

    public boolean identificadorExists(final String identificador) {
        return inscricaoRepository.existsByIdentificadorIgnoreCase(identificador);
    }

}
