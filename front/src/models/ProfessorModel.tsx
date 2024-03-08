import { UsuarioModel } from "./UsuarioModel";

export class ProfessorModel extends UsuarioModel {    
    public getDescricaoTabela(): string {
        return "Professores";
    }
}