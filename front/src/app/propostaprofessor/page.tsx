"use client"

import CamposForm, { TipoCampo } from "@/componentes/core/form/CamposForm";
import Table from "@/componentes/core/table/Table";
import PropostaProfessorModel from "@/models/PropostaProfessorModel";
import TipoPagamentoPropostaModel from "@/models/TipoPagamentoPropostaModel";
import TokenService from "@/services/TokenService";
import { useEffect, useState } from "react";

export default function PropostaProfessorPage(){
     const tokenService = new TokenService();
     const camposListagem = new Array<CamposForm>;
     const camposForm = new Array<CamposForm>;
     const formaRecebimento = new Array<TipoPagamentoPropostaModel>;
     const [isLogado,setIsLogado] = useState<Boolean>(false);
     useEffect( ()=>{
        setIsLogado(tokenService.isLogado());
        
        if (! tokenService.isLogado()){
            setTimeout(function(){                
                window.location.href = window.location.origin+"/login";
            },1000);
        }

    },[] );
    
    formaRecebimento.push( new TipoPagamentoPropostaModel("VALOR","$") );
    formaRecebimento.push( new TipoPagamentoPropostaModel("PORCENTAGEM","%") );

    camposListagem.push(new CamposForm("assunto","Assunto",true,TipoCampo.string,30,"Assunto da proposta") );
    camposListagem.push(new CamposForm("tipo_recebimento","Como gostaria de receber?",true,TipoCampo.map,0,"Valor fixo ou uma porcentagem da venda do curso",formaRecebimento   ));
    camposListagem.push(new CamposForm("valor","Valor",true,TipoCampo.number,0,"Valor/porcentagem que gostaria de receber"));
    camposListagem.push(new CamposForm("status","Status",true,TipoCampo.string,40,"Status"));
    
    camposForm.push(new CamposForm("assunto","Assunto",true,TipoCampo.string,30,"Assunto da proposta") );
    camposForm.push(new CamposForm("tipo_recebimento","Como gostaria de receber?",true,TipoCampo.map,0,"Valor fixo ou uma porcentagem da venda do curso",formaRecebimento   ));
    camposForm.push(new CamposForm("valor","Valor",true,TipoCampo.number,0,"Valor/porcentagem que gostaria de receber"));
    camposForm.push(new CamposForm("termos","Termos",true,TipoCampo.TextArea,0,"Seus termos"));


     return(
        <section>
           
            { isLogado &&
            <section>
                <h4>Envie sua proposta de curso para a empresa.  </h4>
                { 
                   <Table                 
                        camposListagem={camposListagem}
                        camposFormulario={camposForm}                
                        endpoint={"propostaprofessor"} 
                        classModel={PropostaProfessorModel}              
                        incluirNovoRegistro={true}                
                    />

                    
                }



            </section>

            
            }

        </section>
     )


}