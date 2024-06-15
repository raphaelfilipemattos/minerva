package br.com.minerva.minerva.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InscricaoDTO {

    private UUID idinscricao;

    @NotNull
    private LocalDate data;

    @NotNull
    @Schema(type = "string", example = "18:30")
    private LocalTime hora;

    @NotNull
    private Long seq;

    @NotNull
    private UUID idcurso;

    @NotNull
    private UUID idusuario;

    @NotNull
    private UUID idpagamentocurso;


}
