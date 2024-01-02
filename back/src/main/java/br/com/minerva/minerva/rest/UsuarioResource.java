package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.domain.Empresa;
import br.com.minerva.minerva.model.UsuarioDTO;
import br.com.minerva.minerva.model.UsuarioNovoDTO;
import br.com.minerva.minerva.service.UsuarioService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{idusuario}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "idusuario") final UUID idusuario) {
        return ResponseEntity.ok(usuarioService.get(idusuario));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createUsuario(@RequestBody @Valid final UsuarioNovoDTO usuarioDTO) {
        final UUID createdIdusuario = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdIdusuario, HttpStatus.CREATED);
    }

    @PutMapping("/{idusuario}")
    public ResponseEntity<UUID> updateUsuario(
            @PathVariable(name = "idusuario") final UUID idusuario,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        UUID idEmpresa = null;
        usuarioService.update(idusuario,idEmpresa, usuarioDTO);
        return ResponseEntity.ok(idusuario);
    }

    @DeleteMapping("/{idusuario}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteUsuario(
            @PathVariable(name = "idusuario") final UUID idusuario) {
        usuarioService.delete(idusuario);
        return ResponseEntity.noContent().build();
    }

}
