package br.com.minerva.minerva.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class Empresa {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idempresa;

    @Column(nullable = false, length = 100)
    private String razaoSocial;

    @Column(nullable = false, length = 100)
    private String nomeFantasia;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpjCpf;

    @Column(nullable = false, length = 200)
    private String emailFinanceiro;

    @Column(nullable = false, length = 200)
    private String emailEducacional;

    @Column
    private UUID logo;


    @Column(nullable = false, length = 20)
    private String nomeAmbiente;

    @Column(nullable = false, length = 255)
    private String dominio;

    @Column(nullable = false, length = 255)
    private String dominioava;


    @OneToMany(mappedBy = "idempresa")
    private Set<Curso> idempresaCursoes;

    @OneToMany(mappedBy = "idempresa")
    private Set<EmpresaEstilo> idempresaEmpresaEstiloes;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "idpacote_id", nullable = false)
    private UUID idpacote;

    private boolean ambientecriado;

    @OneToMany(mappedBy = "empresa")
    private Set<PerfilEmpresa> empresaPerfilEmpresas;

    @OneToMany(mappedBy = "empresa")
    private Set<PerfilUsuarioEmpresa> empresaPerfilUsuarioEmpresas;



}
