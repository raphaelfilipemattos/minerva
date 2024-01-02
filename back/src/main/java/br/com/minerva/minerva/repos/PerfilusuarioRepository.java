package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Perfilusuario;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerfilusuarioRepository extends JpaRepository<Perfilusuario, UUID> {
}
