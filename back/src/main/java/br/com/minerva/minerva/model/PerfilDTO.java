package br.com.minerva.minerva.model;

import br.com.minerva.minerva.domain.Perfil;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PerfilDTO {
    private UUID idPerfil;
    private String nomePerfil;

    public PerfilDTO(Perfil perfil){
        this.setNomePerfil(perfil.getNome());
        this.setIdPerfil(perfil.getIdperfil());
    }
}
