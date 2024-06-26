package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Curso;
import br.com.minerva.minerva.domain.PropostaContratoProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface PropostaContratoProfessorRepository extends JpaRepository<PropostaContratoProfessor, UUID> {

  @Query("""
          select p
            from PropostaContratoProfessor p
           where p.professor.idusuario = :idprofessor 
          """)
    List<PropostaContratoProfessor> findByProfessor(UUID idprofessor);

  @Query("""
          select p
            from PropostaContratoProfessor p
           where p.empresa.idempresa = :idempresa 
             and p.status != 'N'
             order by status,  p.professor.idusuario,data_hora_criacao 
            
          """)
  List<PropostaContratoProfessor> findByEmpresa(UUID idempresa);
}
