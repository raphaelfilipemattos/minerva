package br.com.minerva.minerva.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Pacote {

    @Id
    @Column(nullable = false, updatable = false)
    //@GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    //@GeneratedValue(generator = "uuid")
    private UUID idpacote;

    @Column(nullable = false, length = 50)
    private String descricao;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private Integer max_cursos;

    @Column
    private Integer uso_disco;


}
