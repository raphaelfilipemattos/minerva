package br.com.minerva.minerva.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmpresaDTO {

    private UUID idempresa;

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


}
