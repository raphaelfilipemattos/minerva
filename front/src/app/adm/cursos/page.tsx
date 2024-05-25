"use client"
import CursosServices from "@/services/CursosServices";
import { useEffect, useState } from "react";
import Table from "@/componentes/core/table/Table";
import CamposForm, { TipoCampo } from "@/componentes/core/form/CamposForm";
import CursoModel from "@/models/CursoModel";
import AdmPages from "../AdmPages";

export default function AdmCursosPage(){
    AdmPages();

    const [cursos, setCursos] =  useState<Array<CursoModel>>();    
    const cursosServices = new CursosServices();
    useEffect(()=>{
      cursosServices.getCursos().then(data=> {
            let cursosArray = new Array<CursoModel>();
            cursosArray = data.map(item => item as CursoModel);                        
            setCursos(cursosArray)
        });  
    },[]);

    const camposListagem = new Array<CamposForm>;
    const camposForm = new Array<CamposForm>;
    
    camposListagem.push(new CamposForm("nomeCurso","Nome do Curso",true,TipoCampo.string,30,"Nome principal do curso"));
    camposListagem.push(new CamposForm("descricaoCompleta","Descrição Completa",true,TipoCampo.TextArea,0,"Descrição detalhada do curso"));
    camposListagem.push(new CamposForm("valor","Valor",true,TipoCampo.number,0,"Valor em R$ do curso."));
    camposListagem.push(new CamposForm("dataIni","Data de início",true,TipoCampo.date,0,"Data que o curso irá iniciar"));
    camposListagem.push(new CamposForm("dataFim","Data de encerramento",true,TipoCampo.date,0,"Data que o curso irá encerrar"));
    camposListagem.push(new CamposForm("ativo","Esse curso ainda está ativo?",true,TipoCampo.boolean,0,""));
    


    camposForm.push(new CamposForm("nomeCurso","Nome do Curso",true,TipoCampo.string,30,"Nome principal do curso"));
    camposForm.push(new CamposForm("descricaoCompleta","Descrição Completa",true,TipoCampo.TextArea,0,"Descrição detalhada do curso"));
    camposForm.push(new CamposForm("apelido","Apelido",false,TipoCampo.string,30,"Apelido serve para identificar de forma rápida o curso. Isso ajuda em algumas operações no AVA."));
    camposForm.push(new CamposForm("valor","Valor",true,TipoCampo.number,0,"Valor em R$ do curso."));
    camposForm.push(new CamposForm("dataIni","Data de início",true,TipoCampo.date,0,"Data que o curso irá iniciar"));
    camposForm.push(new CamposForm("dataFim","Data de encerramento",true,TipoCampo.date,0,"Data que o curso irá encerrar"));
    camposForm.push(new CamposForm("ativo","Esse curso ainda está ativo?",true,TipoCampo.boolean,0,""));
    camposForm.push(new CamposForm("imagemCapa","Imagem para Capa",false,TipoCampo.image,0,"Imagem ilustrativa do curso."));
   
    
    return (
        cursos &&
        <section >
            <Table                 
                camposListagem={camposListagem}
                camposFormulario={camposForm}                
                campoId="idcurso"                
                descricao="Curso"
                endpoint="cursos"  
                classModel={CursoModel}              
                incluirNovoRegistro={true}                
            />
        </section>
    )
    
    

}