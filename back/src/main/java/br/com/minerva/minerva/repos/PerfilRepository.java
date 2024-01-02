package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Perfil;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerfilRepository extends JpaRepository<Perfil, UUID> {
}
