import { ConexaoGET } from "@/infra/Conexao";
import FormaPagamentoModel from "@/models/FormaPagamentoModel";
import { useEffect, useState } from "react";
import DadosCartao from "./dadoscartao";

export default function FormasPagamento({valorTotal}:{valorTotal: Number}){
    const [formasPagamento,setFormasPagamento] = useState<FormaPagamentoModel[]>();
    const [idformapagamentoSelecionada, setIdFormapagamentoSelecionada]= useState<string>();
    useEffect(()=>{
        ConexaoGET<FormaPagamentoModel[]>('pagamento/formaspagamento').then(resposta => {            
            setFormasPagamento(resposta);
        })
    }, []);

    return(
        <div className="row">
            <h5>Forma de pagamento</h5>
            <div className="col-md-7">            
                <select name="formapagamento" 
                        className="form-control"
                        onChange={ e => {
                            setIdFormapagamentoSelecionada(e.target.value);                            
                        }}
                        
                >
                    <option value="-1">Selecione uma forma</option>
                {formasPagamento && formasPagamento.map((formapagamento,key) =>{
                    return (
                    <option key={key} value={formapagamento.idforma_pagamento?.toString()}>
                        {formapagamento.descricao} 
                    </option>)
                })}
                </select>
            </div>
            <div className="col-md-5">
                { idformapagamentoSelecionada && idformapagamentoSelecionada != "7b8e768b-93a8-42b6-8614-9ba0f8b38982" &&
                      <DadosCartao 
                        idFormapagamento={idformapagamentoSelecionada}
                        valortotal={valorTotal}
                      />                   
                 }
                 { idformapagamentoSelecionada && idformapagamentoSelecionada == "7b8e768b-93a8-42b6-8614-9ba0f8b38982" &&
                   <div className="row">
                      Pix
                   </div>
                 }
            </div>
        </div>
    );
}