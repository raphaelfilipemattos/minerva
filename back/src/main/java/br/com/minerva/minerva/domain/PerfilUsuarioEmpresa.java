package br.com.minerva.minerva.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class PerfilUsuarioEmpresa {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idperfilUsuarioEmpresa;

    @Column(nullable = false)
    private Long idusuarioMoodle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idperfil", nullable = false)
    private Perfil perfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

}
