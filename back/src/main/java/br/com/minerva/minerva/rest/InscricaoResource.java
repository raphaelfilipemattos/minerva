package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.model.InscricaoDTO;
import br.com.minerva.minerva.service.InscricaoService;
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
@RequestMapping(value = "/api/inscricoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class InscricaoResource {

    private final InscricaoService inscricaoService;

    public InscricaoResource(final InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @GetMapping
    public ResponseEntity<List<InscricaoDTO>> getAllInscricaos() {
        return ResponseEntity.ok(inscricaoService.findAll());
    }

    @GetMapping("/{idinscricao}")
    public ResponseEntity<InscricaoDTO> getInscricao(
            @PathVariable(name = "idinscricao") final UUID idinscricao) {
        return ResponseEntity.ok(inscricaoService.get(idinscricao));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createInscricao(
            @RequestBody @Valid final InscricaoDTO inscricaoDTO) {
        final UUID createdIdinscricao = inscricaoService.create(inscricaoDTO);
        return new ResponseEntity<>(createdIdinscricao, HttpStatus.CREATED);
    }

    @PutMapping("/{idinscricao}")
    public ResponseEntity<UUID> updateInscricao(
            @PathVariable(name = "idinscricao") final UUID idinscricao,
            @RequestBody @Valid final InscricaoDTO inscricaoDTO) {
        inscricaoService.update(idinscricao, inscricaoDTO);
        return ResponseEntity.ok(idinscricao);
    }

    @DeleteMapping("/{idinscricao}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteInscricao(
            @PathVariable(name = "idinscricao") final UUID idinscricao) {
        inscricaoService.delete(idinscricao);
        return ResponseEntity.noContent().build();
    }

}
