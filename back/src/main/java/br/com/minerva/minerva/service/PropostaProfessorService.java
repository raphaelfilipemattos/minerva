package br.com.minerva.minerva.service;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.domain.*;
import br.com.minerva.minerva.model.*;
import br.com.minerva.minerva.repos.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class PropostaProfessorService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PropostaContratoProfessorRepository propostaContratoProfessorRepository;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private EmpresaContratoProfessorService empresaContratoProfessorService;

    @Autowired
    private Ambiente ambiente;


    @Autowired
    private SalaService salaService;

    public List<PropostaContratoProfessorDTO> getPropostaUsuario(UUID idusuario){
          return this.propostaContratoProfessorRepository.findByProfessor(idusuario).stream().map(PropostaContratoProfessorDTO::new).toList();
    }

    public UUID criaProposta(NovaPropostaContratoProfessorDTO novaPropostaContratoProfessorDTO){
        var propostaProfessor = new PropostaContratoProfessor();
        var professor = this.usuarioRepository.findById(Ambiente.getUsuarioLogado().getIdusuario()).get();
        var empresa = this.empresaRepository.findById(Ambiente.getUsuarioLogado().getIdempresa()).get();

        propostaProfessor.setProfessor(professor);
        propostaProfessor.setEmpresa(empresa);
        propostaProfessor.setAssunto(novaPropostaContratoProfessorDTO.getAssunto());
        propostaProfessor.setTermos(novaPropostaContratoProfessorDTO.getTermos());
        propostaProfessor.setStatus(StatusContrato.NAO_ENVIADO.getValor());
        propostaProfessor.setValor(novaPropostaContratoProfessorDTO.getValor());
        propostaProfessor.setTipo_recebimento( novaPropostaContratoProfessorDTO.getTipo_recebimento().getValor());
        propostaProfessor.setData_hora_criacao(LocalDateTime.now());
        return this.propostaContratoProfessorRepository.save(propostaProfessor).getId_proposta_contrato_professor();

    }

    public ErrorResponse alteraProposta(UUID idproposta, PropostaContratoProfessorDTO propostaContratoProfessorDTO){
        var propostaProfessor = this.propostaContratoProfessorRepository.findById(idproposta).get();
        var resposta = new ErrorResponse();
        if (! Arrays.asList(StatusContrato.NAO_ENVIADO.getValor(),
                            StatusContrato.AJUSTAR.getValor())
                .contains(propostaProfessor.getStatus()) ){
            resposta.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
            resposta.setMessage("Essa proposta não pode ser alterada.");
            return  resposta;
        }

        propostaProfessor.setTermos(propostaContratoProfessorDTO.getTermos());
        propostaProfessor.setValor(propostaContratoProfessorDTO.getValor());
        propostaProfessor.setTipo_recebimento(propostaContratoProfessorDTO.getTipo_recebimento().getValor());
        this.propostaContratoProfessorRepository.save(propostaProfessor);
        resposta.setHttpStatus(HttpStatus.OK.value());
        return resposta;

    }

    public ErrorResponse enviaProposta(UUID idproposta){
        var propostaProfessor = this.propostaContratoProfessorRepository.findById(idproposta).get();
        var resposta = new ErrorResponse();
        if (! Arrays.asList(StatusContrato.NAO_ENVIADO.getValor(),
                        StatusContrato.AJUSTAR.getValor())
                .contains(propostaProfessor.getStatus()) ){
            resposta.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
            resposta.setMessage("Essa proposta não pode ser alterada.");
            return  resposta;
        }

        propostaProfessor.setStatus( StatusContrato.ENVIADO.getValor());
        this.propostaContratoProfessorRepository.save(propostaProfessor);
        resposta.setHttpStatus(HttpStatus.OK.value());
        return resposta;

    }

    public ErrorResponse recusaProposta(UUID idproposta, PropostaContratoProfessorDTO propostaContratoProfessorDTO){
        var propostaProfessor = this.propostaContratoProfessorRepository.findById(idproposta).get();
        var resposta = new ErrorResponse();
        if ( ! propostaProfessor.getStatus().equals( StatusContrato.ENVIADO.getValor()) ){
            resposta.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
            resposta.setMessage("Essa proposta não pode ser recusada.");
            return  resposta;
        }
        UsuarioDTO usuarioLogado =  Ambiente.getUsuarioLogado();
        var usuarioRecusa = this.usuarioRepository.findById(usuarioLogado.getIdusuario()).get();

        if (usuarioLogado.getIdusuario().equals( propostaProfessor.getProfessor().getIdusuario())){
            throw new RuntimeException("O usuário avaliador não pode ser o mesmo que criou a proposta.");
        }

        propostaProfessor.setUsuariorecusa(usuarioRecusa);
        propostaProfessor.setRespostarecusa(propostaContratoProfessorDTO.getRespostarecusa());
        propostaProfessor.setStatus(propostaContratoProfessorDTO.getStatus().getValor());
        this.propostaContratoProfessorRepository.save(propostaProfessor);
        resposta.setHttpStatus(HttpStatus.OK.value());
        return resposta;

    }

    @Transactional
    public ErrorResponse aceitaProposta(UUID idpropostaContratoProfessor, CursoDTO cursoDTO){
        var propostaProfessor = this.propostaContratoProfessorRepository.findById(idpropostaContratoProfessor).get();
        var resposta = new ErrorResponse();
        if (! propostaProfessor.getStatus().equals(StatusContrato.ENVIADO.getValor()) ){
            resposta.setHttpStatus(HttpStatus.UNAUTHORIZED.value());
            resposta.setMessage("Essa proposta não pode ser aceita.");
            return  resposta;
        }

        if (Ambiente.getUsuarioLogado().getIdusuario().equals( propostaProfessor.getProfessor().getIdusuario())){
            throw new RuntimeException("O usuário avaliador não pode ser o mesmo que criou a proposta.");
        }

        //cria curso
        var idcurso = this.cursoService.create(cursoDTO);
        cursoDTO.setIdcurso(idcurso);

        //Gera o contrato
        this.empresaContratoProfessorService.criaContratoEmpresa(propostaProfessor,cursoDTO);

        //Altera o status para aceito.
        propostaProfessor.setStatus(StatusContrato.ACEITO.getValor());
        this.propostaContratoProfessorRepository.save(propostaProfessor);

        //Altera o perfil do usuario para ter professor também
        this.usuarioService.sincronizaMoodlePerfil(propostaProfessor.getEmpresa(),
                usuarioService.get(propostaProfessor.getProfessor().getIdusuario()),
                Perfil.PROFESSOR
        );

        //Define o professor do curso.
        this.salaService.definePapelSala(propostaProfessor.getProfessor().getIdusuario(),
                                         Perfil.PROFESSOR,
                                         idcurso,
                                         propostaProfessor.getEmpresa().getIdempresa(),
                                         this.ambiente.getWebServiceMoodle()
                                        );


        resposta.setHttpStatus(HttpStatus.OK.value());
        return resposta;

    }

    public void delete(final UUID idproposta) {
        this.propostaContratoProfessorRepository.deleteById(idproposta);
    }

}
