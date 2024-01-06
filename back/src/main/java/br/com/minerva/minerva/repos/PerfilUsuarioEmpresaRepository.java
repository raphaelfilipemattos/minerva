package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Perfil;
import br.com.minerva.minerva.domain.PerfilUsuarioEmpresa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PerfilUsuarioEmpresaRepository extends JpaRepository<PerfilUsuarioEmpresa, UUID> {
    @Query("""
              select perfil.idperfil, perfil.nome
                 from PerfilUsuarioEmpresa pue
                inner join pue.perfil perfil                                      
                where pue.usuario.idusuario = :idusuario
                  and pue.empresa.idempresa = :idempresa                
            """)
   List<Perfil> findByIdusuarioAndIdempresa(UUID idusuario, UUID idempresa);
   @Query("""
              select p
                 from PerfilUsuarioEmpresa p
                where usuario.idusuario = :idusuario
                  and empresa.idempresa = :idempresa
                  and perfil.idperfil = :idperfil
            """)
    PerfilUsuarioEmpresa findByIdusuarioAndIdempresaAndIdperfil(UUID idusuario,UUID idempresa, UUID idperfil);


}
