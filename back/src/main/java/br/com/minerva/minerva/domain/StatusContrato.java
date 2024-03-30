package br.com.minerva.minerva.domain;

public enum StatusContrato {
    NAO_ENVIADO("N"),
    ENVIADO("E"),
    ACEITO("A"),
    NAO_ACEITO("X"),
    AJUSTAR("R");

    private final String valor;
    StatusContrato(String valorOpcao){
        valor = valorOpcao;
    }
    public String getValor(){
        return valor;
    }

}
