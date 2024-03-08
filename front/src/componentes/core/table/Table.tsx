import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {ClassType, ComponentClass, ReactNode, useEffect, useState } from "react"
import {faPenToSquare, faTrashCan } from "@fortawesome/free-regular-svg-icons"
import style from "./table.module.css"
import Formulario from "../form/Formulario"
import { ConexaoDELETE, ConexaoGET, ConexaoPOST, ConexaoPUT } from "@/infra/Conexao"
import CamposForm, { TipoCampo } from "../form/CamposForm"
import InterfaceModel from "@/models/InterfaceModel"
import { Interface } from "readline"


export default function Table({htmlBeforeTable,camposListagem,camposFormulario, 
                               endpoint, classModel, incluirNovoRegistro }:
    {   htmlBeforeTable? : ReactNode
        camposListagem: Array<CamposForm>,  
        camposFormulario: Array<CamposForm>,         
        endpoint: string,
        classModel: InterfaceModel,
        incluirNovoRegistro: boolean,        
        
        
    }
    ){
    const [modal,setModal] = useState<ReactNode | null>(null);   
    const [dados,setDados] = useState<Array<InterfaceModel | []>>();
    const novoRecord = new classModel() ;    
    const campoId = novoRecord.getCampoId();
    const descricao = novoRecord.getDescricaoTabela();

    
    useEffect(()=> {
       ConexaoGET<Array<InterfaceModel>>(endpoint).then(data=> {
                let tempArray = new Array<InterfaceModel>();
                tempArray = data.map(item => item as InterfaceModel);                  
                setDados(tempArray)
            }); 
        
      },[]);
  
    
    function fechaModal(){
        setModal(null)
    }

    async function gravarEdicao(dado){
        const resposta = await ConexaoPUT(endpoint,dado[campoId], JSON.stringify(dado));
        if(resposta){
           alert("Dados salvos com sucesso!");
           window.location.reload(); 
        }
    }  
    
    async function apaga(dado){
        if (confirm("Deseja realmenet apagar esse registro?")){
            const resposta = await ConexaoDELETE(endpoint,dado[campoId]);
            console.log(resposta);
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
        style: 'currency', 
        currency: 'BRL'
    });

    function getDadoFormatado(dado, campo: CamposForm){
       if (campo.tipoCampo == TipoCampo.date){
        return dataFormat.format( new Date(dado[campo.nomeCampo]+ " 00:00:00") );
       }    
       if (campo.tipoCampo == TipoCampo.number){
        return numericFormat.format( dado[campo.nomeCampo] );
       }    
       if (campo.tipoCampo == TipoCampo.boolean){
        return  dado[campo.nomeCampo] ? "Sim" : "Não" ;
       }    
       if (campo.tipoCampo == TipoCampo.map){
         const valor =  campo.itens?.find(item => {
                return item.getCampoId() == dado[campo.nomeCampo];
         });
         return valor?.getCampoDisplay();
       }
       return  dado[campo.nomeCampo];
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
                                    <td className="text-center">{key+1}</td>
                                    {camposListagem.map((campo, keyCampo)=> {
                                        return (<td key={keyCampo}>                                                
                                                    { getDadoFormatado(dado, campo)} 
                                            </td> );
                                    })}
                                    <td className={style.icons+ " d-flex justify-content-between"}>
                                        <FontAwesomeIcon 
                                            icon={faPenToSquare}
                                            onClick={event => {                                            
                                                setModal(                                              
                                                            <Formulario
                                                                descricao={descricao}
                                                                objeto={dado}
                                                                campos={camposFormulario}
                                                                onSubmit={(dadoAlterado) => { gravarEdicao(dadoAlterado)}}
                                                                onClose={() => {fechaModal()}}
                                                            />)
                                                    }
                                                }/>
                                        <FontAwesomeIcon 
                                            icon={faTrashCan }                                                                                
                                            onClick={event => {
                                                    apaga(dado);
                                                }
                                            }
                                            />
                                    </td>
                                </tr>)
                        } )}
                    </tbody>
                </table>

            </div>}
        </div>
    )
}