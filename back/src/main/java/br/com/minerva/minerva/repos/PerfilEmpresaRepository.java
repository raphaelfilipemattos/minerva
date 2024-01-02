package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.PerfilEmpresa;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerfilEmpresaRepository extends JpaRepository<PerfilEmpresa, UUID> {
}
