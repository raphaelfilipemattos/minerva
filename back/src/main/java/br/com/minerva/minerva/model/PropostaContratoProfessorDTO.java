package br.com.minerva.minerva.model;

import br.com.minerva.minerva.domain.PropostaContratoProfessor;
import br.com.minerva.minerva.domain.StatusContrato;
import br.com.minerva.minerva.domain.TipoRecebimentoContrato;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PropostaContratoProfessorDTO {
    private UUID id_proposta_contrato_professor;
    private UUID idprofessor;
    private UUID idempresa;

    @Lob
    private String termos;
    private String assunto;

    private LocalDateTime data_hora_criacao;
    private StatusContrato status;

    private TipoRecebimentoContrato tipo_recebimento;
    private float valor;

    @Lob
    private String respostarecusa;

    public PropostaContratoProfessorDTO(PropostaContratoProfessor propostaContratoProfessor){
        this.setId_proposta_contrato_professor(propostaContratoProfessor.getId_proposta_contrato_professor());
        this.setStatus( StatusContrato.findByValue(propostaContratoProfessor.getStatus()));
        this.setAssunto(propostaContratoProfessor.getAssunto());
        this.setRespostarecusa(propostaContratoProfessor.getRespostarecusa());
        this.setTermos(propostaContratoProfessor.getTermos());
        this.setIdempresa(propostaContratoProfessor.getEmpresa().getIdempresa());
        this.setValor(propostaContratoProfessor.getValor());
        this.setIdprofessor(propostaContratoProfessor.getProfessor().getIdusuario());
        this.setTipo_recebimento(TipoRecebimentoContrato.findByValue(propostaContratoProfessor.getTipo_recebimento()));
        this.setData_hora_criacao(propostaContratoProfessor.getData_hora_criacao());
    }

    public PropostaContratoProfessorDTO( UUID id_proposta_contrato_professor, UUID idprofessor,
                                         UUID idempresa, String termos,String assunto,
                                         LocalDateTime data_hora_criacao, StatusContrato status,
                                         TipoRecebimentoContrato tipo_recebimento,float valor,
                                         String respostarecusa){
        this.setId_proposta_contrato_professor(id_proposta_contrato_professor);
        this.setIdprofessor(idprofessor);
        this.setIdempresa(idempresa);
        this.setTermos(termos);
        this.setAssunto(assunto);
        this.setData_hora_criacao(data_hora_criacao);
        this.setStatus(status);
        this.setTipo_recebimento(tipo_recebimento);
        this.setValor(valor);
        this.setRespostarecusa(respostarecusa);
    }


}
