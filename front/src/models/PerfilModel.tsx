import { UUID } from "crypto";
import InterfaceModel from "./InterfaceModel";

class PerfilModel implements InterfaceModel{
    getCampoDisplay(): string {
        return "nomePerfil";
    }
    getCampoId(): string {
        return "idPerfil";
    }
    getDescricaoTabela(): string {
        return "Perfis do sistema";
    }
    
    idPerfil? :UUID;
    nomePerfil?: string;

}

export default PerfilModel;