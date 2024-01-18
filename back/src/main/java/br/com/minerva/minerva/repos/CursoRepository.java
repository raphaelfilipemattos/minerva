package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Curso;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CursoRepository extends JpaRepository<Curso, UUID> {
    @Query("""
             select curso
               from Curso curso
              where curso.empresa.idempresa = :idempresa 
            """)
    List<Curso> findAllByIdempresa(UUID idempresa);
}
