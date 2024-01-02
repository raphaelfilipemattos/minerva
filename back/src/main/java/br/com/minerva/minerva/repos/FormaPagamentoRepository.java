package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.FormaPagamento;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, UUID> {
}
