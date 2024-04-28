import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {ReactNode, useEffect, useState } from "react"
import {faPenToSquare, faTrashCan } from "@fortawesome/free-regular-svg-icons"
import style from "./table.module.css"
import Formulario from "../form/Formulario"
import { ConexaoDELETE, ConexaoGET, ConexaoPOST, ConexaoPUT } from "@/infra/Conexao"
import CamposForm, { TipoCampo } from "../form/CamposForm"
import InterfaceModel from "@/models/InterfaceModel"
import ItensAcaoRegistro from "./ItemAcaoRegistro"


export interface Opcoes{
    tituloFormulario: string;
    gravaFormulario: boolean ;
    itensAcaoRegistro? : ItensAcaoRegistro[];
    usaEditarRegistro: boolean;
    usaExcluirRegistro: Boolean;
}

export class ConfiguracoesTabela implements Opcoes{
    tituloFormulario: string = "";
    gravaFormulario: boolean = true;
    itensAcaoRegistro? : ItensAcaoRegistro[] ;
    usaEditarRegistro: boolean = true;
    usaExcluirRegistro: Boolean =true;
}

export default function Table({htmlBeforeTable,camposListagem,camposFormulario, 
                               endpoint, classModel, incluirNovoRegistro, opcoes }:
    {   htmlBeforeTable? : ReactNode
        camposListagem: Array<CamposForm>,  
        camposFormulario: Array<CamposForm>,         
        endpoint: string,
        classModel: InterfaceModel,
        incluirNovoRegistro: boolean, 
        opcoes: Opcoes,
    }
    ){
    const [modal,setModal] = useState<ReactNode | null>(null);   
    const [dados,setDados] = useState<Array<InterfaceModel | []>>();
    const novoRecord = new classModel() ;    
    const campoId = novoRecord.getCampoId();
    const descricao = novoRecord.getDescricaoTabela();
    const [configuracoes,setConfiguracoes] = useState<ConfiguracoesTabela>(new ConfiguracoesTabela());
   
    
    useEffect(()=> {
       ConexaoGET<Array<InterfaceModel>>(endpoint).then(data=> {
                let tempArray = new Array<InterfaceModel>();
                tempArray = data.map(item => item as InterfaceModel);                                  
                setDados(tempArray)
            });
             
        if (opcoes != null ){
            setConfiguracoes(opcoes);    
        }  
        
        
      },[]);
  
    
    function fechaModal(){
        setModal(null)
    }

    async function gravarEdicao(dado){
        const resposta = await ConexaoPUT(endpoint,dado[campoId], JSON.stringify(dado));
        if(resposta){
           alert("Dados salvos com sucesso!");
           window.location.reload(); 
        }else{
            alert("Ocorreu um erro ao gravar!");
            console.log(resposta);  
        }
    }  
    
    async function apaga(dado){
        if (confirm("Deseja realmenet apagar esse registro?")){
            const resposta = await ConexaoDELETE(endpoint,dado[campoId]);
            alert("Registro removido!");
            window.location.reload(); 
        }
    }    
    
    async function gravarNovo(dado){
        const json = JSON.stringify(dado);
        const resposta = await ConexaoPOST(endpoint,json);
        if(resposta){
            alert("Dados salvos com sucesso!");
            window.location.reload(); 
         }else{
            alert("Ocorreu um erro ao gravar!");
            console.log(resposta);
         }
    }  

    const dataFormat = new Intl.DateTimeFormat('pt-BR', {
        dateStyle: 'short',        
        timeZone: 'America/Bahia',
    });

    const numericFormat = new Intl.NumberFormat('pt-BR', {
        style: 'decimal',        
        roundingPriority: "lessPrecision",
        minimumFractionDigits: 2,
        minimumSignificantDigits: 2,

    });

    function getDado(dado, nomeCampo: string){
        const treeName = nomeCampo.split(".");
        let resposta = dado[treeName[0]];
        for(let index = 1; index < treeName.length; index ++){
            resposta = resposta[treeName[1]];
        }
        return resposta;

    }

    function getDadoFormatado(dado, campo: CamposForm){
       const informacao = getDado(dado,campo.nomeCampo);

       if (campo.tipoCampo == TipoCampo.date){
        return dataFormat.format( new Date(informacao + " 00:00:00") );
       }    
       if (campo.tipoCampo == TipoCampo.number){
        return numericFormat.format( informacao  );
       }    
       if (campo.tipoCampo == TipoCampo.boolean){
        return  informacao  ? "Sim" : "Não" ;
       }    
       if (campo.tipoCampo == TipoCampo.map){
         const valor =  campo.itens?.find(item => {
                return item[item.getCampoId()] == informacao ;
         });         
         return  valor == null ? "" : valor[valor?.getCampoDisplay()] ;
       }
       return  informacao ;
    }
    
    return (      
         <div className={style.tabela}>
            <h2>{descricao}</h2>
            {dados &&                   
            <div className={" table-responsive"}>
                {htmlBeforeTable}
                {incluirNovoRegistro &&
                    <button className="btn btn-success" 
                            onClick={()=> {
                                            setModal(
                                                        <Formulario
                                                                descricao={descricao}
                                                                tituloFormulario={configuracoes.tituloFormulario == '' ? ' Novo ': configuracoes.tituloFormulario}
                                                                gravaFormulario= {configuracoes.gravaFormulario}
                                                                objeto={novoRecord}
                                                                campos={camposFormulario}
                                                                onSubmit={(dadoNovo) => { gravarNovo(dadoNovo)}}
                                                                onClose={() => {setModal(null)}}
                                                            />
                                                    );
                                            }}>
                            Adicionar
                        </button>
                }
                {modal}
                <table className="table table-striped table-hover table-bordered mt-2 ">
                    <thead>
                    <tr>
                        <th className="text-center">#</th>
                        {camposListagem.map((coluna, key) => {
                            return <th key={key}>{coluna.displayCampo}</th> 
                        })}
                        <th className="text-center">Ações</th>
                        </tr> 
                    </thead>
                    <tbody className="table-group-divider">
                        {dados && dados.map( (dado, key) => {
                        return (
                                <tr key={key}>
                                    <td className="text-center" key={key+"_num_0"} >{key+1}</td>
                                    {camposListagem.map((campo, keyCampo)=> {
                                        return (<td key={key+'_'+campo.nomeCampo+"_"+keyCampo+1}>                                                
                                                    { getDadoFormatado(dado, campo)} 
                                            </td> );
                                    })}
                                    <td className={style.icons+ " d-flex justify-content-between"} key={key+"_acao_9999"}  >
                                        {configuracoes.usaEditarRegistro && 
                                            <FontAwesomeIcon 
                                                icon={faPenToSquare}
                                                key={key+"_acao_editar"}
                                                title="Editar"
                                                onClick={event => {                                            
                                                    setModal(                                              
                                                                <Formulario                                                                
                                                                    descricao={descricao}
                                                                    objeto={dado}
                                                                    campos={camposFormulario}
                                                                    gravaFormulario= {configuracoes.gravaFormulario}
                                                                    tituloFormulario={configuracoes.tituloFormulario == '' ? ' Alterando ': configuracoes.tituloFormulario}
                                                                    onSubmit={(dadoAlterado) => { gravarEdicao(dadoAlterado)}}
                                                                    onClose={() => {fechaModal()}}
                                                                />)
                                                        }
                                                    }/>}
                                       {configuracoes.usaExcluirRegistro &&
                                          <FontAwesomeIcon 
                                            icon={faTrashCan }  
                                            key={key+"_acao_apagar"}
                                            title="Apagar"                                                                              
                                            onClick={event => {
                                                    apaga(dado);
                                                }
                                            }
                                            />}
                                        {
                                            configuracoes.itensAcaoRegistro && 
                                            configuracoes.itensAcaoRegistro.map((acao, keyItens) =>{                                                
                                                return acao.render(dado, campoId, key+"_acao_extra"+keyItens);
                                            })
                                        }    
                                    </td>
                                </tr>)
                        } )}
                    </tbody>
                </table>

            </div>}
        </div>
    )
}