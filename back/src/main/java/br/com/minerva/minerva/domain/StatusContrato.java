package br.com.minerva.minerva.domain;

public enum StatusContrato {
    NAO_ENVIADO("N"),
    ENVIADO("E"),
    ACEITO("A"),
    NAO_ACEITO("X"),
    AJUSTAR("R");

    private final String valor;
    StatusContrato(String valorOpcao){
        this.valor = valorOpcao;
    }
    public String getValor(){
        return this.valor;
    }

    public static StatusContrato findByValue(String label) {
        for (StatusContrato e : values()) {
            if (e.valor.equals(label)) {
                return e;
            }
        }
        return null;
    }

}
