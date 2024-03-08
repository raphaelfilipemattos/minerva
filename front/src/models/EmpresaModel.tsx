import InterfaceModel from "./InterfaceModel";

class EmpresaModel implements InterfaceModel{
    getCampoId(): string {
        return "idEmpresa";
    }
    getCampoDisplay(): string {
        return "nomeEmpresa";
    }
    getDescricaoTabela(): string {
        return "Empresa";
    }

    idEmpresa?: String;
    nomeEmpresa?:String;
    dominioAva?:String;
    logo?: String;
    personalizacao?: any;
    idPacote?: String;
    nomeAmbiente?: String;

}


export default EmpresaModel;