"use client"

import CamposForm, { TipoCampo } from "@/componentes/core/form/CamposForm";
import Formulario from "@/componentes/core/form/Formulario";
import ItensAcaoRegistro from "@/componentes/core/table/ItemAcaoRegistro";
import Table, { ConfiguracoesTabela, itensAcaoRegistro } from "@/componentes/core/table/Table";
import CursoModel from "@/models/CursoModel";
import PropostaProfessorModel from "@/models/PropostaProfessorModel";
import TipoPagamentoPropostaModel from "@/models/TipoPagamentoPropostaModel";
import TokenService from "@/services/TokenService";
import { faCircleCheck, faXmarkCircle } from "@fortawesome/free-regular-svg-icons";
import { ReactNode, useState } from "react";
import AdmPages from "../AdmPages";

export default function PropostaProfessorPage(){
    
    AdmPages();

    const tokenService = new TokenService();
    const camposListagem = new Array<CamposForm>;
    const camposForm = new Array<CamposForm>;
    const formaRecebimento = new Array<TipoPagamentoPropostaModel>;
    const configuracoes = new ConfiguracoesTabela();
    const [modal,setModal] = useState<ReactNode | null>(null);   

    
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

    const btnAprovar = new ItensAcaoRegistro();
    btnAprovar.endpoint = "admpropostaprofessor/aceitar";
    btnAprovar.verbo = "PUT";
    btnAprovar.titulo = "Aprovar";
    btnAprovar.usaIdUrl = true;
    btnAprovar.imagem = ['far','circle-check'];
    btnAprovar.msgConfirma = "Confirma aprovação?";
    btnAprovar.onShow =  (dado) => {
        return dado.status == 'ENVIADO';
    }
     
    btnAprovar.onClick = (event, dado,campoId) =>{
        const curso = new CursoModel();
        curso.nomeCurso = dado['assunto'];
        curso.descricaoCompleta = dado['termos'];
        
        const camposForm = new Array<CamposForm>;    
        camposForm.push(new CamposForm("nomeCurso","Nome do Curso",true,TipoCampo.string,30,"Nome principal do curso"));
        camposForm.push(new CamposForm("descricaoCompleta","Descrição Completa",true,TipoCampo.TextArea,0,"Descrição detalhada do curso"));
        camposForm.push(new CamposForm("apelido","Apelido",false,TipoCampo.string,30,"Apelido serve para identificar de forma rápida o curso. Isso ajuda em algumas operações no AVA."));
        camposForm.push(new CamposForm("valor","Valor",true,TipoCampo.number,0,"Valor em R$ do curso."));
        camposForm.push(new CamposForm("dataIni","Data de início",true,TipoCampo.date,0,"Data que o curso irá iniciar"));
        camposForm.push(new CamposForm("dataFim","Data de encerramento",true,TipoCampo.date,0,"Data que o curso irá encerrar"));
        camposForm.push(new CamposForm("ativo","Esse curso ainda está ativo?",true,TipoCampo.boolean,0,""));
        camposForm.push(new CamposForm("imagemCapa","Imagem para Capa",false,TipoCampo.image,0,"Imagem ilustrativa do curso."));
        setModal( <Formulario
           campos={camposForm}
           descricao="Curso"
           tituloFormulario="Conveter proposta em curso"
           objeto={curso}
           gravaFormulario={true}
           onClose={ ( event) =>{
                setModal(null);
           } }
           onSubmit ={ (dadoAlterado) => {           
                btnAprovar.doOnClick(dadoAlterado, dado[campoId])
           }}
        />)
    }
    

   const btnRecusar = new ItensAcaoRegistro();
   btnRecusar.endpoint = "admpropostaprofessor/recusa";
   btnRecusar.verbo = "PUT";
   btnRecusar.titulo = "Recusar";
   btnRecusar.usaIdUrl = true;
   btnRecusar.msgConfirma = "Deseja relamente recusar essa proposta?";
   btnRecusar.imagem =  ['far','circle-xmark'];
   btnRecusar.onShow =  (dado) => {
     return dado.status == 'ENVIADO';
   }

   configuracoes.itensAcaoRegistro = [btnAprovar, btnRecusar];

    
    return(
       <section>
            {modal}
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
    )
}