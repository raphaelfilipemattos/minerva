package br.com.minerva.minerva.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
public class UsuarioDTO extends @Valid UsuarioNovoDTO implements UserDetails {

    private UUID idusuario;

    @NotNull
    private Long seq;

    @NotNull
    private OffsetDateTime dataCadastro;

    @NotNull
    private OffsetDateTime lastChange;

    private List<PerfilDTO> perfil;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.perfil== null ) {
            return List.of(new SimpleGrantedAuthority("ROLE_ALUNO"));
        }

        var listaRoles = new ArrayList<SimpleGrantedAuthority>();
        for (PerfilDTO item_perfil : this.perfil){
            var nome = item_perfil.getNomePerfil().replace(" ","_").toLowerCase();
            listaRoles.add(new SimpleGrantedAuthority("ROLE_"+nome) );
        }
        return listaRoles;

    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        var temp = this.getCpf()+this.getEmail();
        return MD5Encoder.encode(temp.toString().getBytes());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
