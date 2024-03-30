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

}
