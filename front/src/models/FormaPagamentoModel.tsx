import InterfaceModel from "./InterfaceModel";

class FormaPagamentoModel implements InterfaceModel{
    getCampoId(): string {
        return "idforma_pagamento";
    }
    getCampoDisplay(): string {
        return "descricao";
    }
    getDescricaoTabela(): string {
        return "Formas de pagamento";
    }

    idforma_pagamento?: String;
    descricao?:String;

}


export default FormaPagamentoModel;