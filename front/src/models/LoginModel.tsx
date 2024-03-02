import { UUID } from "crypto";
import PerfilModel from "./PerfilModel";

class LoginModel{
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