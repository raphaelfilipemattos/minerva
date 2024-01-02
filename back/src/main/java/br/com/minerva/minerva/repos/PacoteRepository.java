package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Pacote;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PacoteRepository extends JpaRepository<Pacote, UUID> {
}
