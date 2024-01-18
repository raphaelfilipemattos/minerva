package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByCpfIgnoreCase(String cpf);

    @Query("""
            select u
              from Usuario u
             where email = :login
                or cpf = :login
            """)
    Usuario getUsuarioByLogin(String login);

}
