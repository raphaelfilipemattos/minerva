package br.com.minerva.minerva.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class Perfil {

    public static final UUID ROOT = UUID.fromString( "2c25bbf1-3595-4b58-870b-3a5c322a18db");
    public static final UUID ADMINISTRADOR_EMPRESA = UUID.fromString( "6a2cac68-1001-412b-8c32-8bc43bd44689");
    public static final UUID SECRETARIA = UUID.fromString( "7781650b-0fe9-45a4-af0a-5f987fa47a29");
    public static final UUID PROFESSOR = UUID.fromString( "d5d8c107-3a0a-4575-8822-d518ce488a40");
    public static final UUID ALUNO = UUID.fromString( "d8310398-a81e-4013-9cbe-b9d3cbd7bdd1");

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idperfil;

    @Column(nullable = false, length = 50)
    private String nome;

    @OneToMany(mappedBy = "idperfil")
    private Set<PerfilEmpresa> idperfilPerfilEmpresas;

    @OneToMany(mappedBy = "idperfil")
    private Set<PerfilUsuarioEmpresa> idperfilPerfilUsuarioEmpresas;

}
