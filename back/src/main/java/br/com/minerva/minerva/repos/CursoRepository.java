package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Curso;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CursoRepository extends JpaRepository<Curso, UUID> {
}
