package br.com.minerva.minerva.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotNull
    @NotBlank
    private String login;
    @NotNull
    @NotBlank
    private String senha;

    private String urlempresa;
}
