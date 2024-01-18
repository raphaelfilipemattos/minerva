package br.com.minerva.minerva.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;


@Getter
@Setter
public class CursoDTO {

    private UUID idcurso;

    @NotNull
    @Size(max = 255)
    private String nomeCurso;

    @NotNull
    @Size(max = 100)
    private String apelido;

    @NotNull
    @Digits(integer = 8, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "52.08")
    private BigDecimal valor;

    private String imagemCapa;

    private String descricaoCompleta;

    @NotNull
    private LocalDate dataIni;
    @NotNull
    private LocalDate dataFim;


    private UUID idempresa;

    private Boolean ativo;

}
