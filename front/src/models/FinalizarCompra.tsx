import CursoModel from "./CursoModel";
import InterfaceModel from "./InterfaceModel";

class FinalizarCopraModel implements InterfaceModel{
    getCampoId(): string {
        return "";
    }
    getCampoDisplay(): string {
        return "";
    }
    getDescricaoTabela(): string {
        return "";
    }

    idFormaPagamento?: string;
    valorTotal?:Number;
    cursos?: Array<CursoModel>;
    nomeTitularCartao?: string;
    numeroCartao?: string;
    vencimentoCartao?: string;
    codigoSegurancaCartao?: string;
    chaveCobrancaPix?: string;

}

export default FinalizarCopraModel;