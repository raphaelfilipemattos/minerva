package br.com.minerva.minerva.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UsuarioDTO extends @Valid UsuarioNovoDTO {

    private UUID idusuario;

    @NotNull
    private Long seq;

    @NotNull
    private OffsetDateTime dataCadastro;

    @NotNull
    private OffsetDateTime lastChange;

}
