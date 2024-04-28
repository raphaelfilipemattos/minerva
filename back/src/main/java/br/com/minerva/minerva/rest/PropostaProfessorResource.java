package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.config.Ambiente;
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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/propostaprofessor", produces = MediaType.APPLICATION_JSON_VALUE)
public class PropostaProfessorResource {

    private final PropostaProfessorService propostaProfessorService;

    public PropostaProfessorResource(final PropostaProfessorService propostaProfessorService) {
        this.propostaProfessorService = propostaProfessorService;
    }

    @GetMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<List<PropostaContratoProfessorDTO>> getPropostaUsuario() {
        var propostas = this.propostaProfessorService.getPropostaUsuario(Ambiente.getUsuarioLogado().getIdusuario());
        return  ResponseEntity.ok(propostas);
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<UUID> criaProposta(@RequestBody @Valid final NovaPropostaContratoProfessorDTO novaPropostaContratoProfessorDTO) {
        final UUID createdIdcurso = this.propostaProfessorService.criaProposta(novaPropostaContratoProfessorDTO);
        return new ResponseEntity<>(createdIdcurso, HttpStatus.CREATED);
    }


    @PutMapping("/{id_proposta_contrato_professor}")
    public ResponseEntity<UUID> alteraProposta(@PathVariable(name = "id_proposta_contrato_professor") final UUID id_proposta_contrato_professor,
                                               @RequestBody @Valid final PropostaContratoProfessorDTO propostaContratoProfessorDTO) {

       var resposta = propostaProfessorService.alteraProposta(id_proposta_contrato_professor, propostaContratoProfessorDTO);
       if (resposta.getHttpStatus() == HttpStatus.OK.value() ){
           return ResponseEntity.ok(id_proposta_contrato_professor);
       }
        throw new RuntimeException(resposta.getMessage());

    }

    @GetMapping("/envia/{idproposta}")
    public ResponseEntity<UUID> enviaProposta(@PathVariable(name = "idproposta") final UUID idproposta) {

        var resposta = propostaProfessorService.enviaProposta(idproposta);
        if (resposta.getHttpStatus() == HttpStatus.OK.value() ){
            return ResponseEntity.ok(idproposta);
        }
        throw new RuntimeException(resposta.getMessage());

    }


    @DeleteMapping("/{idproposta}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteproposta(
            @PathVariable(name = "idproposta") final UUID idproposta) {
        this.propostaProfessorService.delete(idproposta);
        return ResponseEntity.noContent().build();
    }


}
