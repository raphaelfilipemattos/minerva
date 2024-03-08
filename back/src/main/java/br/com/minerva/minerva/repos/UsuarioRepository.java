package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Usuario;

import java.util.List;
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

    @Query("""
            select u
              from Usuario u
             inner join PerfilUsuarioEmpresa pue 
                on (pue.usuario.idusuario = u.idusuario)
             where pue.empresa.idempresa = :idempresa
               and pue.perfil.idperfil not in (br.com.minerva.minerva.domain.Perfil.ROOT, br.com.minerva.minerva.domain.Perfil.ALUNO, br.com.minerva.minerva.domain.Perfil.PROFESSOR)
             order by u.nome  
            """)
    List<Usuario> getUsuariosEmpresa(UUID idempresa);

    @Query("""
            select u
              from Usuario u
             inner join PerfilUsuarioEmpresa pue 
                on (pue.usuario.idusuario = u.idusuario)
             where pue.empresa.idempresa = :idempresa
               and pue.perfil.idperfil = :idperfil
             order by u.nome  
            """)
    List<Usuario> getUsuariosEmpresaByPerfil(UUID idempresa,UUID idperfil);
}
