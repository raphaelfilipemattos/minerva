package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.model.EmpresaDTO;
import br.com.minerva.minerva.model.EmpresaNovaDTO;
import br.com.minerva.minerva.service.EmpresaService;
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
@RequestMapping(value = "/api/empresas", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmpresaResource {

    private final EmpresaService empresaService;

    public EmpresaResource(final EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> getAllEmpresas() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    @GetMapping("/{idempresa}")
    public ResponseEntity<EmpresaDTO> getEmpresa(
            @PathVariable(name = "idempresa") final UUID idempresa) {
        return ResponseEntity.ok(empresaService.get(idempresa));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createEmpresa(@RequestBody @Valid final EmpresaNovaDTO empresaDTO) {
        final UUID createdIdempresa = empresaService.create(empresaDTO);
        return new ResponseEntity<>(createdIdempresa, HttpStatus.CREATED);
    }

    @PutMapping("/{idempresa}")
    public ResponseEntity<UUID> updateEmpresa(
            @PathVariable(name = "idempresa") final UUID idempresa,
            @RequestBody @Valid final EmpresaDTO empresaDTO) {
        empresaService.update(idempresa, empresaDTO);
        return ResponseEntity.ok(idempresa);
    }

    @DeleteMapping("/{idempresa}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEmpresa(
            @PathVariable(name = "idempresa") final UUID idempresa) {
        empresaService.delete(idempresa);
        return ResponseEntity.noContent().build();
    }

}
