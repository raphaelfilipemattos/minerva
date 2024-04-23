"use client"

import CamposForm, { TipoCampo } from "@/componentes/core/form/CamposForm";
import Table, { ConfiguracoesTabela } from "@/componentes/core/table/Table";
import PropostaProfessorModel from "@/models/PropostaProfessorModel";
import TipoPagamentoPropostaModel from "@/models/TipoPagamentoPropostaModel";

export default function PropostaProfessorPage(){
    
    const camposListagem = new Array<CamposForm>;
    const camposForm = new Array<CamposForm>;
    const formaRecebimento = new Array<TipoPagamentoPropostaModel>;
    const configuracoes = new ConfiguracoesTabela();

    
    formaRecebimento.push( new TipoPagamentoPropostaModel("VALOR","$") );
    formaRecebimento.push( new TipoPagamentoPropostaModel("PORCENTAGEM","%") );

    camposListagem.push(new CamposForm("professor.nome","Solicitante",true,TipoCampo.string,100,"Pessoa que fez a proposta" ));
    camposListagem.push(new CamposForm("assunto","Assunto",true,TipoCampo.string,30,"Assunto da proposta") );
    camposListagem.push(new CamposForm("tipo_recebimento","Como gostaria de receber?",true,TipoCampo.map,0,"Valor fixo ou uma porcentagem da venda do curso",formaRecebimento   ));
    camposListagem.push(new CamposForm("valor","Valor",true,TipoCampo.number,0,"Valor/porcentagem que gostaria de receber"));
    camposListagem.push(new CamposForm("status","Status",true,TipoCampo.string,40,"Status"));
   
    camposForm.push(new CamposForm("assunto","Assunto",true,TipoCampo.string,30,"Assunto da proposta") );
    camposForm.push(new CamposForm("tipo_recebimento","Como gostaria de receber?",true,TipoCampo.map,0,"Valor fixo ou uma porcentagem da venda do curso",formaRecebimento   ));
    camposForm.push(new CamposForm("valor","Valor",true,TipoCampo.number,0,"Valor/porcentagem que gostaria de receber"));
    camposForm.push(new CamposForm("termos","Termos",true,TipoCampo.TextArea,0,"Seus termos"));

    configuracoes.gravaFormulario = false;
    configuracoes.tituloFormulario = "Visualizando ";
    configuracoes.usaExcluirRegistro = false;
    
    return(
       <section>
          
           {
           <section>
               <h4>Propostas de cursos enviadas para empresa  </h4>
               { 
                  <Table                 
                       camposListagem={camposListagem}
                       camposFormulario={camposForm}                
                       endpoint={"admpropostaprofessor"} 
                       classModel={PropostaProfessorModel}              
                       incluirNovoRegistro={false}  
                       opcoes={ configuracoes }              
                   />

                   
               }



           </section>

           
           }

       </section>
    )
}