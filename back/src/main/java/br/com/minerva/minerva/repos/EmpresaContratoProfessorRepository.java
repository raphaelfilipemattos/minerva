package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Curso;
import br.com.minerva.minerva.domain.EmpresaContratoProfessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface EmpresaContratoProfessorRepository extends JpaRepository<EmpresaContratoProfessor, UUID> {

}
