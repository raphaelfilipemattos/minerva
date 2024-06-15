package br.com.minerva.minerva.model;

import br.com.minerva.minerva.domain.FormaPagamento;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FormaPagamentoDTO {

    private UUID idforma_pagamento;
    private String descricao;

    public FormaPagamentoDTO(FormaPagamento formaPagamento){
        this.setIdforma_pagamento(formaPagamento.getIdformaPagamento());
        this.setDescricao(formaPagamento.getDescricao());
    }

}
