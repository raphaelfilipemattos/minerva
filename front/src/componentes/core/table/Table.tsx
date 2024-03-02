import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import {ClassType, ComponentClass, ReactNode, useState } from "react"
import {faPenToSquare, faTrashCan } from "@fortawesome/free-regular-svg-icons"
import style from "./table.module.css"
import Formulario from "../form/Formulario"
import { ConexaoDELETE, ConexaoPOST, ConexaoPUT } from "@/infra/Conexao"
import CamposForm from "../form/CamposForm"


export default function Table({htmlBeforeTable,camposListagem,camposFormulario, 
                               dados, endpoint, incluirNovoRegistro,
                               novoRecord, 
                               descricao, campoId }:
    {   htmlBeforeTable? : ReactNode
        camposListagem: Array<CamposForm>,  
        camposFormulario: Array<CamposForm>,  
        dados : Array<Object>,
        endpoint: string,
        incluirNovoRegistro: boolean,
        novoRecord?: Object ,
        descricao: string,
        campoId: string
    }
    ){

    const [modal,setModal] = useState<ReactNode | null>(null);    

    async function gravarEdicao(dado){
        const resposta = await ConexaoPUT(endpoint,dado[campoId], JSON.stringify(dado));
        console.log(resposta);
    }  
    
    async function apaga(dado){
        const resposta = await ConexaoDELETE(endpoint,dado[campoId]);
        console.log(resposta);
    }    
    
    async function gravarNovo(dado){
        const resposta = await ConexaoPOST(endpoint,dado);
        console.log(resposta);
    }  
    
    return (        
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
                    {dados.map( (dado, key) => {
                       return (
                             <tr key={key}>
                                <td className="text-center">{key+1}</td>
                                {camposListagem.map((campo, keyCampo)=> {
                                    return (<td key={keyCampo}> {dado[campo.nomeCampo] } </td> );
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
                                                            onClose={() => {setModal(null)}}
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

        </div>
    )
}