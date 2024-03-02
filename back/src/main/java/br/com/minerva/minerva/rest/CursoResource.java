package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.model.CursoDTO;
import br.com.minerva.minerva.service.CursoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/cursos", produces = MediaType.APPLICATION_JSON_VALUE)
public class CursoResource extends RestControlerPadrao{

    private final CursoService cursoService;

    public CursoResource(final CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<CursoDTO>> getAllCursos() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    @GetMapping("/{idcurso}")
    public ResponseEntity<CursoDTO> getCurso(@PathVariable(name = "idcurso") final UUID idcurso) {
        return ResponseEntity.ok(cursoService.get(idcurso));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> createCurso(@RequestBody  @Valid final CursoDTO cursoDTO) {
        final UUID createdIdcurso = cursoService.create(cursoDTO);
        return new ResponseEntity<>(createdIdcurso, HttpStatus.CREATED);
    }

    @PutMapping("/{idcurso}")
    public ResponseEntity<UUID> updateCurso(@PathVariable(name = "idcurso") final UUID idcurso,
            @RequestBody @Valid final CursoDTO cursoDTO) {
        cursoService.update(idcurso, cursoDTO);
        return ResponseEntity.ok(idcurso);
    }

    @DeleteMapping("/{idcurso}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCurso(@PathVariable(name = "idcurso") final UUID idcurso) {
        cursoService.delete(idcurso);
        return ResponseEntity.noContent().build();
    }

}
