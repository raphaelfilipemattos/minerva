package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.CursoSala;
import br.com.minerva.minerva.domain.CursoTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface CursoSalaRepository extends JpaRepository<CursoSala, UUID> {

    @Query("""
             select sala
                from CursoSala sala
               where curso.idcurso = :idcurso 
            """)
    CursoSala findByIdCurso(UUID idcurso);
}
