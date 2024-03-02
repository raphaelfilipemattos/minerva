import {ConexaoGET} from "@/infra/Conexao";

export default class CursosServices{

    async getCursos() {

        return await ConexaoGET<Array<CursoModel>>("cursos") ;

    }


}