package br.com.minerva.minerva.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class Inscricao {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idinscricao;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(nullable = false)
    private Long seq;

    @Column(nullable = false, unique = true, length = 10)
    private String identificador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcurso_id", nullable = false)
    private Curso idcurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuario_id", nullable = false)
    private Usuario idusuario;

    @OneToMany(mappedBy = "idinscricao")
    private Set<Pagamento> idinscricaoPagamentoes;

}
