import { UsuarioModel } from "./UsuarioModel";

export class ProfessorModel extends UsuarioModel {   
    foto?: string;
     
    public getDescricaoTabela(): string {
        return "Professores";
    }
}