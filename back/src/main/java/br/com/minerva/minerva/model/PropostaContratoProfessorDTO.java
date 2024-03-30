package br.com.minerva.minerva.model;

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


}
