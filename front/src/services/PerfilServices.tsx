import { ConexaoGET } from "@/infra/Conexao";
import PerfilModel from "@/models/PerfilModel";
import { UUID } from "crypto";

export default class PerfilServices{

    async  getAll()  {        
        return  await ConexaoGET<PerfilModel>("geral/perfil");
    }

    getPerfil(idperfil:UUID): PerfilModel{ 
        const perfils = getAll().then(resposta => resposta as PerfilModel);
        return perfils[idperfil];

    }

}