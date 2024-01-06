package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.domain.Empresa;
import br.com.minerva.minerva.model.*;
import br.com.minerva.minerva.repos.EmpresaRepository;
import br.com.minerva.minerva.service.TokenService;
import br.com.minerva.minerva.service.UsuarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequestMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginResource {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ApiResponse(responseCode = "200")
    public ResponseEntity fazLogin(@RequestBody @Valid final LoginDTO loginDTO) {
        var token = new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getSenha());
        var autentication =    this.authenticationManager.authenticate(token);
        UsuarioDTO usuarioLogado = (UsuarioDTO) autentication.getPrincipal();
        var empresa = this.empresaRepository.findByDominio(loginDTO.getUrlempresa());

        this.usuarioService.sincronizaMoodle(empresa, usuarioLogado);

        usuarioLogado.setIdempresa(empresa.getIdempresa());
        usuarioLogado.setPerfil(this.usuarioService.getPerilEmpresa(usuarioLogado.getIdusuario(), empresa.getIdempresa()));

        return ResponseEntity.ok( new AutenticacaoUsuarioDTO(this.tokenService.geraToken( usuarioLogado ) , usuarioLogado));
    }
}
