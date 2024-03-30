package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Perfil;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PerfilRepository extends JpaRepository<Perfil, UUID> {
    @Query( """
            select p
              from Perfil p
             where idperfil <>  br.com.minerva.minerva.domain.Perfil.ROOT
            """)
    List<Perfil> getPerfisFront();

    @Query("""
              select idperfilMoodle
                 from PerfilEmpresa 
                where empresa.idempresa = :idempresa
                  and idperfil.idperfil = :idperfil 
            """)

    Integer getPerfilMoodle(UUID idempresa, UUID idperfil);
}
