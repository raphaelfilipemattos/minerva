package br.com.minerva.minerva.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class EmpresaNovaDTO {

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String razaoSocial;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String nomeFantasia;

    @NotNull
    @Size(max = 14)
    @Pattern(regexp = "\\d{11,14}")
    private String cnpjCpf;

    @NotNull
    @Email
    @Size(max = 200)
    private String emailFinanceiro;

    @NotNull
    @Email
    @Size(max = 200)
    private String emailEducacional;

    private String logo;



    @NotNull
    private UUID idpacote;

    @Size(max = 255)
    private String dominio;

    @Size(max = 255)
    private String dominioava;

    @NotNull
    @Valid
    private UsuarioNovoDTO usuarioMaster;


}
