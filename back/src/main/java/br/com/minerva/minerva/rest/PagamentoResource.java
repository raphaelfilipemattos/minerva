package br.com.minerva.minerva.rest;

import br.com.minerva.minerva.model.FormaPagamentoDTO;

import br.com.minerva.minerva.model.PagamentoCursoDTO;
import br.com.minerva.minerva.model.UsuarioNovoDTO;
import br.com.minerva.minerva.service.PagamentoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/pagamento", produces = MediaType.APPLICATION_JSON_VALUE)

public class PagamentoResource {
    @Autowired
    private PagamentoService pagamentoService;


    @GetMapping("/{formaspagamento}")
    public ResponseEntity<List<FormaPagamentoDTO>> getFormasPagamento() {
        return ResponseEntity.ok( this.pagamentoService.getFormasPagamentos() );
    }

    @PostMapping
    @ApiResponse(responseCode = "200")
    public ResponseEntity<Boolean> fazPagamento(@RequestBody @Valid final PagamentoCursoDTO pagamentoCursoDTO) {
       var resposta =   this.pagamentoService.fazPagamentoCurso(pagamentoCursoDTO);
        return ResponseEntity.ok(resposta);
    }


}
