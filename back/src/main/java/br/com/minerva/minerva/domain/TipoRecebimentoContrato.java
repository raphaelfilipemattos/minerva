package br.com.minerva.minerva.domain;

public enum TipoRecebimentoContrato {
    PORCENTAGEM("P"),
    VALOR("V");

    private final String valor;
    TipoRecebimentoContrato(String valorOpcao){
        valor = valorOpcao;
    }
    public String getValor(){
        return valor;
    }

    public static TipoRecebimentoContrato findByValue(String label) {
        for (TipoRecebimentoContrato e : values()) {
            if (e.valor.equals(label)) {
                return e;
            }
        }
        return null;
    }

}
