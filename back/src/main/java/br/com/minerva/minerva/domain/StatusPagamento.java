package br.com.minerva.minerva.domain;

public enum StatusPagamento {
   NAO_PAGO( "N"),
   PAGO ("P"),
   PAGO_PARCIAL ("X");

    private final String valor;
    StatusPagamento(String valorOpcao){
        valor = valorOpcao;
    }
    public String getValor(){
        return valor;
    }

}
