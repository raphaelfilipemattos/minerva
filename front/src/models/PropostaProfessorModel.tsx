import InterfaceModel from "./InterfaceModel";

class PropostaProfessorModel implements InterfaceModel {
    getCampoDisplay(): string {
        return "assunto";
    }
    public  getCampoId(): string {
        return "id_proposta_contrato_professor";
    }
    public getDescricaoTabela(): string {
        return "Propostas de curso";
    }

    id_proposta_contrato_professor?: string;
    assunto?: string;
    termos?: string;
    tipo_recebimento?:string;
    valor?: Number;
    status?: string;
    
    
        
}


export default PropostaProfessorModel;