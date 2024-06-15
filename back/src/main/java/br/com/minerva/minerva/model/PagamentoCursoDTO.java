package br.com.minerva.minerva.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PagamentoCursoDTO {

    private List<CursoDTO> cursos;
    private UUID idFormaPagamento;
    private Double valorTotal;
    private UUID idusuario;
}
