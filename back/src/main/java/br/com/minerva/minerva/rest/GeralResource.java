package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.model.LoginAvaDTO;

import br.com.minerva.minerva.model.PerfilDTO;
import br.com.minerva.minerva.model.UsuarioDTO;
import br.com.minerva.minerva.repos.PerfilRepository;
import br.com.minerva.minerva.util.Utils;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/geral", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeralResource {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private Ambiente ambiente;

    @GetMapping("/perfil")
    public ResponseEntity<List<PerfilDTO>> getPerfil()
    {
        return ResponseEntity.ok(this.perfilRepository.getPerfisFront().stream().map(PerfilDTO::new).toList() );
    }

    @GetMapping("/imagem/{tipo}/{id}")
    public ResponseEntity<String> getImagem(@PathVariable(name = "tipo")  String tipo, @PathVariable(name = "id") UUID id){
        String local = "";
        switch (tipo){
            case "curso":
                local = Ambiente.localCapaCurso();
                break;
            case "logo":
                local = Ambiente.localLogoEmpresa();
                break;

        }

        var resposta = Utils.fileToBase64(local, id);

        return ResponseEntity.ok("data:image/png;base64, "+resposta );

    }

    @GetMapping("/loginava")
    public ResponseEntity<LoginAvaDTO> getDadosLoginAva(){
        var loginava = new LoginAvaDTO();
        loginava.setLogin(Ambiente.getUsuarioLogado().getIdusuario().toString());
        loginava.setSenha( LoginAvaDTO.geraSenhaAva(Ambiente.getUsuarioLogado()) );
        loginava.setUrl( this.ambiente.getEmpresaAtual().getDominioava() );
        return ResponseEntity.ok(loginava);
    }

}
