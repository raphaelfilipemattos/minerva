import { UUID } from "crypto";
import PerfilModel from "./PerfilModel";
import InterfaceModel from "./InterfaceModel";

class LoginModel implements InterfaceModel {
   getCampoId(): string {
      throw new Error("Method not implemented.");
   }
   getCampoDisplay(): string {
      throw new Error("Method not implemented.");
   }
   getDescricaoTabela(): string {
      throw new Error("Method not implemented.");
   }
   token?: string;
   idusuario?: UUID;
   nome?: string;
   email?: string;
   cpf?: string;
   idempresa?: UUID;
   dataCadastro?: Date;
   perfil?:Array<PerfilModel>;
}

export default LoginModel;