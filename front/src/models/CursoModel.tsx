import InterfaceModel from "./InterfaceModel";

class CursoModel implements InterfaceModel {
    getCampoDisplay(): string {
        return "nomeCurso";
    }
    public  getCampoId(): string {
        return "idcurso";
    }
    public getDescricaoTabela(): string {
        return "Cursos";
    }

    idcurso?: string;
    nomeCurso?: string;
    descricaoCompleta?: string;
    apelido?:string;
    valor?: Number;
    imagemCapa?: String;
    dataIni?: Date;
    dataFim?: Date;
    ativo?: boolean;

    
        
}


export default CursoModel;