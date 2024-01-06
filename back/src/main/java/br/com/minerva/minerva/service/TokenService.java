package br.com.minerva.minerva.service;

import br.com.minerva.minerva.model.UsuarioDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private static final String SENHA_PRIVADA = "7bbdbe69-f4af-4b0a-a570-24468e2e0b34";
    public String geraToken(UsuarioDTO usuarioLogadoDTO){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TokenService.SENHA_PRIVADA);

            return JWT.create()
                    .withIssuer("Minerva")
                    .withSubject(usuarioLogadoDTO.getUsername())
                    .withExpiresAt(this.dataExpira())
                    .withClaim("idusuario", usuarioLogadoDTO.getIdusuario().toString() )
                   /* .withClaim("nome", usuarioLogadoDTO.getNome())
                    .withClaim("email", usuarioLogadoDTO.getEmail())
                    .withClaim("cpf", usuarioLogadoDTO.getCpf())
                    .withClaim("idempresa", usuarioLogadoDTO.getIdempresa().toString())
                    .withClaim("dataCadastro", usuarioLogadoDTO.getDataCadastro().toString() )
                    .withClaim("login", usuarioLogadoDTO.getLogin())*/
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token: "+exception.getMessage());
        }
    }


    private Instant dataExpira(){
        return LocalDateTime.now().plusHours(+3).toInstant(ZoneOffset.of("-03:00"));
    }
}
