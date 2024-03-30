package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.domain.StatusContrato;
import br.com.minerva.minerva.model.CursoDTO;
import br.com.minerva.minerva.model.NovaPropostaContratoProfessorDTO;
import br.com.minerva.minerva.model.PropostaContratoProfessorDTO;
import br.com.minerva.minerva.service.CursoService;
import br.com.minerva.minerva.service.PropostaProfessorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/propostaprofessor", produces = MediaType.APPLICATION_JSON_VALUE)
public class PropostaProfessorResource {

    private final PropostaProfessorService propostaProfessorService;

    public PropostaProfessorResource(final PropostaProfessorService propostaProfessorService) {
        this.propostaProfessorService = propostaProfessorService;
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> criaProposta(@RequestBody @Valid final NovaPropostaContratoProfessorDTO novaPropostaContratoProfessorDTO) {
        final UUID createdIdcurso = this.propostaProfessorService.criaProposta(novaPropostaContratoProfessorDTO);
        return new ResponseEntity<>(createdIdcurso, HttpStatus.CREATED);
    }

    @PutMapping("/altera/{idproposta}")
    public ResponseEntity<UUID> alteraProposta(@PathVariable(name = "idproposta") final UUID idproposta,
                                               @RequestBody @Valid final PropostaContratoProfessorDTO propostaContratoProfessorDTO) {

       var resposta = propostaProfessorService.alteraProposta(idproposta, propostaContratoProfessorDTO);
       if (resposta.getHttpStatus() == HttpStatus.OK.value() ){
           return ResponseEntity.ok(idproposta);
       }
        throw new RuntimeException(resposta.getMessage());

    }

    @PutMapping("/envia/{idproposta}")
    public ResponseEntity<UUID> enviaProposta(@PathVariable(name = "idproposta") final UUID idproposta) {

        var resposta = propostaProfessorService.enviaProposta(idproposta);
        if (resposta.getHttpStatus() == HttpStatus.OK.value() ){
            return ResponseEntity.ok(idproposta);
        }
        throw new RuntimeException(resposta.getMessage());

    }

    @PutMapping("/recusa/{idproposta}")
    public ResponseEntity<UUID> recusaProposta(@PathVariable(name = "idproposta") final UUID idproposta,
                                               @RequestBody @Valid final PropostaContratoProfessorDTO propostaContratoProfessorDTO ) {

        var resposta = propostaProfessorService.recusaProposta(idproposta, propostaContratoProfessorDTO);
        if (resposta.getHttpStatus() == HttpStatus.OK.value() ){
            return ResponseEntity.ok(idproposta);
        }
        throw new RuntimeException(resposta.getMessage());

    }

    @PutMapping("/aceitar/{idproposta}")
    public ResponseEntity<UUID> recusaProposta(@PathVariable(name = "idproposta") final UUID idproposta,
                                               @RequestBody @Valid final CursoDTO cursoDTO ) {

        var resposta = propostaProfessorService.aceitaProposta(idproposta, cursoDTO);
        if (resposta.getHttpStatus() == HttpStatus.OK.value() ){
            return ResponseEntity.ok(idproposta);
        }
        throw new RuntimeException(resposta.getMessage());

    }


}
