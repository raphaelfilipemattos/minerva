package br.com.minerva.minerva.config;

import br.com.minerva.minerva.model.UsuarioDTO;
import br.com.minerva.minerva.repos.PerfilUsuarioEmpresaRepository;
import br.com.minerva.minerva.repos.UsuarioRepository;
import br.com.minerva.minerva.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilUsuarioEmpresaRepository perfilUsuarioEmpresaRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = getToken(request);
        if (token != null) {
            var tokenDepitografado = this.tokenService.decriptaToken(token);
            var idusuario = tokenDepitografado.getClaim("idusuario").asString();
            var idempresa = tokenDepitografado.getClaim("idempresa").asString();
            var find =  this.usuarioRepository.findById(UUID.fromString(idusuario));
            if (! find.isPresent()){
                throw new RuntimeException("Usuário não encontrado");
            }
            var usuario = find.get();
            var usuarioDTO = new UsuarioDTO(usuario, UUID.fromString( idempresa), this.perfilUsuarioEmpresaRepository );
            var authorization = new UsernamePasswordAuthenticationToken(usuarioDTO,null, usuarioDTO.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authorization);
        }
        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        var token =  request.getHeader("Authorization");
        if (token != null){
            return token.replace("Bearer ","").trim();
        }
        return null;
    }
}
