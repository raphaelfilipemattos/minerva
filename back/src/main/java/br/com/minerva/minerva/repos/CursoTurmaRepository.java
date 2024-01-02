package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.CursoTurma;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CursoTurmaRepository extends JpaRepository<CursoTurma, UUID> {
}
