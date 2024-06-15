package br.com.minerva.minerva.model;

import br.com.minerva.minerva.domain.Pagamento;
import br.com.minerva.minerva.domain.Pagamentocurso;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagamentoCursoInscricaoDTO {

    private List<CursoDTO> cursos;
    private Pagamento pagamento;
    private List<Pagamentocurso> pagamentocurso;
}
