package br.com.minerva.minerva.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class Curso {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idcurso;

    @Column(nullable = false)
    private String nomeCurso;

    @Column(nullable = false, length = 100)
    private String apelido;

    @Column(nullable = false)
    private Boolean ativo;

    @Column(nullable = false)
    private Long seq;

    @Column(nullable = false)
    private Long idcourseMoodle;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal valor;

    @Column
    private UUID imagemCapa;

    @Column(columnDefinition = "text")
    private String descricaoCompleta;

    @OneToMany(mappedBy = "idcurso")
    private Set<CursoTurma> idcursoCursoTurmas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa idempresa;

    @OneToMany(mappedBy = "idcurso")
    private Set<Inscricao> idcursoInscricaos;

}
