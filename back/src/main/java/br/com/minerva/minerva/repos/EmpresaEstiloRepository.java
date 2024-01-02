package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.EmpresaEstilo;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmpresaEstiloRepository extends JpaRepository<EmpresaEstilo, UUID> {
}
