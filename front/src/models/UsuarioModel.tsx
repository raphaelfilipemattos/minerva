import { UUID } from "crypto";
import InterfaceModel from "./InterfaceModel";

export class UsuarioModel implements InterfaceModel{
    getCampoDisplay(): string {
        return "nome";
    }
    public getCampoId(): string {
        return "idusuario";
    }
    public getDescricaoTabela(): string {
        return "Usuários";
    }

    nome?: string;
	email?: string;
	cpf?: string;
	idempresa?: UUID;
	idusuario?: UUID;	
	dataCadastro?: Date;
	perfil?: UUID;
	
}