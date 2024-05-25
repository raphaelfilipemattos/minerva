import CursoModel from "@/models/CursoModel";

export default class CarrinhoService
{
   
    getCursosCarrinho(){
        let cursos = new Array();

        for ( let key of  Object.keys(localStorage)){
            const item = JSON.parse( localStorage.getItem(key) );
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

    
}