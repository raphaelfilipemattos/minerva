package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Usuario;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByCpfIgnoreCase(String cpf);

    List<Usuario> findAllByIdempresa(UUID idempresa);
}
