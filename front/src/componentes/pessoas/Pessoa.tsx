"use client"

import { useEffect, useState } from "react";
import Table from "@/componentes/core/table/Table";
import CamposForm, { TipoCampo } from "@/componentes/core/form/CamposForm";
import PerfilServices from "@/services/PerfilServices";
import PerfilModel from "@/models/PerfilModel";
import { UsuarioModel } from "@/models/UsuarioModel";

export default function Pessoa(endpoint, model){
    const camposListagem = new Array<CamposForm>;
    const perfilService = new PerfilServices();
    const [perfis, setPerfis] = useState(new Array<PerfilModel>()) ;       
    useEffect(() => {
        perfilService.getAll().then(data => {
           data.map(perfil => {
             let perfilmodel = new PerfilModel();
             perfilmodel.idPerfil = perfil.idPerfil;
             perfilmodel.nomePerfil = perfil.nomePerfil;             
             const index =  perfis.findIndex(item => item.idPerfil == perfilmodel.idPerfil );
             if(index <  0){
                 perfis.push(perfilmodel);
                 setPerfis(perfis);
             }


           })
        });    

    },[]);
        
    camposListagem.push(new CamposForm("nome","Nome",true,TipoCampo.string,30,"Nome completo"));
    camposListagem.push(new CamposForm("email","E-mail",true,TipoCampo.string,25,"E-mail"));
    camposListagem.push(new CamposForm("cpf","CPF",true,TipoCampo.string,11,"CPF"));
    camposListagem.push(new CamposForm("idperfil","Perfil",true,TipoCampo.map,0,"Perfil", perfis));
    
       
    return (
        <section >
            <Table                 
                camposListagem={camposListagem}
                camposFormulario={camposListagem}                
                endpoint={endpoint} 
                classModel={model}              
                incluirNovoRegistro={false}                
            />
        </section>
    )
    
    

}
