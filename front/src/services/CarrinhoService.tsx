import { ConexaoPOST } from "@/infra/Conexao";
import CursoModel from "@/models/CursoModel";
import FinalizarCopraModel from "@/models/FinalizarCompra";

export default class CarrinhoService
{
   
    getCursosCarrinho(){
        let cursos = new Array();

        for ( let key of  Object.keys(localStorage)){
            const valor = localStorage.getItem(key);
            
            if (! valor) continue;

            const item = JSON.parse( valor );
            if ( Object.getOwnPropertyNames(item).indexOf("nomeCurso") <0 ) continue;
            
            cursos.push(  item as CursoModel )
        }
        return cursos;
    }

    addCurso(curso: CursoModel){        
        const cursoJson = JSON.stringify(curso);
        localStorage.setItem(curso.idcurso, cursoJson);        
    }

    limpaCarrinho(){
        localStorage.clear();
    }

    removeCurso(idCurso:string){
        localStorage.removeItem(idCurso);
    }

    count(){
        return this.getCursosCarrinho().length;
    }
    
    finalizaCompra(finalizaCompra : FinalizarCopraModel){
        ConexaoPOST("pagamento", JSON.stringify(finalizaCompra)).then(resposta =>{
            if (resposta === true){
                alert("Compra finalizada com sucesso!");
                this.limpaCarrinho();
                document.getElementById("btn_sala_aula")?.click();
            }
        })
    }

    
}