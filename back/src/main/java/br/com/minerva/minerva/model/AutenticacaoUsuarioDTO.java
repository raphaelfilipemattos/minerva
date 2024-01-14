package br.com.minerva.minerva.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record AutenticacaoUsuarioDTO(
        String token,
        UUID idusuario,
        String nome,
        String email,
        String cpf,
        UUID idempresa,
        OffsetDateTime dataCadastro,
        List<PerfilDTO> perfil
         ) {
     public AutenticacaoUsuarioDTO(String token, UsuarioDTO usuario){
        this(token, usuario.getIdusuario(),
             usuario.getNome(), usuario.getEmail(),
             usuario.getCpf(),usuario.getIdempresa(),
             usuario.getDataCadastro(),usuario.getPerfil());

    }
}
