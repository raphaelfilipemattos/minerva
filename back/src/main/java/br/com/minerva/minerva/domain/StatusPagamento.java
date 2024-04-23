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

    public static StatusPagamento findByValue(String label) {
        for (StatusPagamento e : values()) {
            if (e.valor.equals(label)) {
                return e;
            }
        }
        return null;
    }

}
