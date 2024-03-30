package br.com.minerva.minerva.model;

import br.com.minerva.minerva.domain.TipoRecebimentoContrato;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.UUID;

@Getter
@Setter
public class NovaPropostaContratoProfessorDTO {
    @Lob
    private String termos;
    private String assunto;

    private TipoRecebimentoContrato tipo_recebimento;
    private float valor;

}
