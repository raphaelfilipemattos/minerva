package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.model.CursoDTO;
import br.com.minerva.minerva.model.PropostaContratoProfessorDTO;
import br.com.minerva.minerva.service.PropostaProfessorService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/admpropostaprofessor", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdmPropostaProfessorResource {

    private final PropostaProfessorService propostaProfessorService;
    public AdmPropostaProfessorResource(final PropostaProfessorService propostaProfessorService) {
        this.propostaProfessorService = propostaProfessorService;
    }

    @GetMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<List<PropostaContratoProfessorDTO>> getPropostas() {
        var propostas = this.propostaProfessorService.getPropostasEmpresa(Ambiente.getUsuarioLogado().getIdempresa());
        return  ResponseEntity.ok(propostas);
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
