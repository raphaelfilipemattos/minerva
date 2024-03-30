package br.com.minerva.minerva.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.w3c.dom.Text;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@Setter
public class PropostaContratoProfessor {
    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id_proposta_contrato_professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idprofessor", nullable = false)
    private Usuario professor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;

    private LocalDateTime data_hora_criacao;

    private String status;

    @Column(columnDefinition = "TEXT")
    private String termos;
    private String assunto;

    private String tipo_recebimento;
    private float valor;

    @Column(columnDefinition = "TEXT", nullable = true)
    private String respostarecusa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idusuariorecusa", nullable = true)
    private Usuario usuariorecusa;




}
