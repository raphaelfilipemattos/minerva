package br.com.minerva.minerva.model;

import br.com.minerva.minerva.domain.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class UsuarioDisplayDTO {

    private UUID idusuario;

    @NotNull
    @Size(max = 150)
    private String nome;

    @NotNull
    @Email
    @Size(max = 200)
    private String email;

    @NotNull
    @Size(max = 11)
    @Pattern(regexp = "\\d{11}")
    private String cpf;

    public UsuarioDisplayDTO(Usuario usuario){
        this.setIdusuario(usuario.getIdusuario());
        this.setNome(usuario.getNome());
        this.setEmail(usuario.getEmail());
        this.setCpf(usuario.getCpf());
    }


}
