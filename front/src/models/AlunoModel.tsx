import { UsuarioModel } from "./UsuarioModel";

export class AlunoModel extends UsuarioModel {    
    public getDescricaoTabela(): string {
        return "Alunos";
    }
}