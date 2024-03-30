package br.com.minerva.minerva.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;


import java.awt.*;
import java.util.UUID;

@Entity
@Getter
@Setter
public class EmpresaContratoProfessor {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id_empresa_contrato_professor;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprofessor", nullable = false)
    private Usuario professor;


    private String tipo_pagamento;
    private float valor_pagamento;
    private float valorpago;
    private String status_pagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcurso", nullable = false)
    private Curso curso;


    private TextArea obs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idproposta", nullable = false)
    private PropostaContratoProfessor Proposta;



}
