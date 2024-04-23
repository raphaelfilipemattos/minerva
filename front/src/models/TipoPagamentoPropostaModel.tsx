import InterfaceModel from "./InterfaceModel";

class TipoPagamentoPropostaModel implements InterfaceModel {
    getCampoDisplay(): string {
        return "descricao";
    }
    public  getCampoId(): string {
        return "tipo";
    }
    public getDescricaoTabela(): string {
        return "Forma de recebimento proposta professor";
    }

    constructor(tipo,descricao){
        this.tipo = tipo;
        this.descricao = descricao;

    }

    tipo?: string;
    descricao?: string;
        
}


export default TipoPagamentoPropostaModel;