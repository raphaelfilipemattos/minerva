package br.com.minerva.minerva.domain;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class Usuario {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idusuario;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false,insertable = false)
    private Long seq;

    @Column(nullable = false,insertable = false)
    private OffsetDateTime dataCadastro;

    @Column(nullable = false,insertable = false)
    private OffsetDateTime lastChange;

    @OneToMany(mappedBy = "idusuario")
    private Set<Inscricao> idusuarioInscricaos;

    @OneToMany(mappedBy = "usuario")
    private Set<PerfilUsuarioEmpresa> usuarioPerfilUsuarioEmpresas;

}
