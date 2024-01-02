package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.PerfilUsuarioEmpresa;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerfilUsuarioEmpresaRepository extends JpaRepository<PerfilUsuarioEmpresa, UUID> {
    PerfilUsuarioEmpresa findByIdusuarioAndIdempresa(UUID idusuario,UUID idempresa);
    PerfilUsuarioEmpresa findByIdusuarioAndIdempresaAndIdperfil(UUID idusuario,UUID idempresa, UUID idperfil);
}
