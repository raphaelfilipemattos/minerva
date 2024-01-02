package br.com.minerva.minerva.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Getter
@Setter
public class Pagamento {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID idpagamento;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal valorPago;

    @Column(nullable = false)
    private LocalDate data;

    @Column
    private OffsetDateTime hora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idforma_pagamento_id", nullable = false)
    private FormaPagamento idformaPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idinscricao_id", nullable = false)
    private Inscricao idinscricao;

}
