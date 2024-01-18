package br.com.minerva.minerva.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Getter
@Setter
public class CursoSala {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idcursoSala;

    @Column(nullable = false)
    private LocalDate dataIni;

    @Column(nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false)
    private Integer idcursoMoodle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcurso", nullable = false)
    private Curso curso;

}
