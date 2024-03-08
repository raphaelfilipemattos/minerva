package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.domain.Perfil;
import br.com.minerva.minerva.model.PerfilDTO;
import br.com.minerva.minerva.model.UsuarioDTO;
import br.com.minerva.minerva.repos.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/geral", produces = MediaType.APPLICATION_JSON_VALUE)
public class GeralResource {

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping("/perfil")
    public ResponseEntity<List<PerfilDTO>> getPerfil()
    {
        return ResponseEntity.ok(this.perfilRepository.getPerfisFront().stream().map(PerfilDTO::new).toList() );
    }

}
