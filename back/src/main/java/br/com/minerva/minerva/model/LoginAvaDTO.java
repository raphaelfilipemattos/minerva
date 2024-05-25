package br.com.minerva.minerva.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoginAvaDTO {

    private String url;
    private String login;
    private String senha;

    public static String geraSenhaAva(UsuarioDTO usuarioDTO){
        var senhaNova = usuarioDTO.getIdusuario().toString().toUpperCase()+
                        usuarioDTO.getEmail().toLowerCase()+
                        usuarioDTO.getCpf();
        return senhaNova;
    }

}
