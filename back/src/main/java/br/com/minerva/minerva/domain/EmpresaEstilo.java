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
public class EmpresaEstilo {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idempresaEstilo;

    @Column(length = 16)
    private String corFundo;

    @Column(length = 20)
    private String fontePadrao;

    @Column(columnDefinition = "text")
    private String css;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idempresa_id", nullable = false)
    private Empresa idempresa;

}
