import ValidadorCartaoCredito from "@/bibliotecas/helpers/ValidadorCartaoCredito";
import FinalizarCopraModel from "@/models/FinalizarCompra";
import CarrinhoService from "@/services/CarrinhoService";
import { useState } from "react";

export default function DadosCartao({idFormapagamento, valortotal}: {idFormapagamento:string, valortotal:Number}){

    const [numeroCartao,setNumeroCartao] = useState<string>('');
    const [validade,setValidade] = useState<string>("");
    const [cvc,setCvc] = useState<string>('');
    const [nome,setNome] = useState<string>('');

    function mascaraValidade(e){        
        let validadeFtm = e.target.value.replace(/\D/g, '').replace(/(\d{2})(\d{2})/, '$1/$2') ;        
        const mes = validadeFtm.split('/')[0];
        const ano = validadeFtm.split('/')[1];
        const anoAtual = (new Date()).getFullYear();
        const mesAtual = (new Date()).getMonth()+1;
        if (mes && mes.length == 2 && (parseInt(mes) < 1 || parseInt(mes) > 12) ){
            alert("O mês deve estar entre 1 e 12");
            return;
        }
        
        if (ano && ano.length == 2 && parseInt('20'+ano) < anoAtual){
            alert("Este cartão está vencido");
            return;
        }

        if (mes && ano && mes.length == 2 && ano.length == 2  && parseInt('20'+ano) == anoAtual && parseInt(mes)  < mesAtual){
            alert("Este cartão está vencido");
            return;
        }
        setValidade(validadeFtm);
    }

    function mascaraNumero(e){        
        if (e.target.value.length > 19) return;

        let numero = e.target.value.replace(/\D/g, '').replace(/(\d{4})(\d{4})(\d{4})(\d{4})/, '$1 $2 $3 $4') ;                
        setNumeroCartao(numero);
    }

    function prosseguir(e){
        e.preventDefault();
        for(let element of document.querySelectorAll("#form_cartao input") ){            
            if (element.value == "" || element.value == "0"){
                 alert("Existem campos não preenchidos.") ;
                 element.focus();
                 break;
            }
        }

        if (! ValidadorCartaoCredito.valida(numeroCartao) ){
            alert("O número do cartão de crédto está incorreto");
            document.querySelector("#form_cartao input[name='numero']")?.focus();
            return false;
        }

        const finalizaCompra = new FinalizarCopraModel();
        const carrinhoService = new CarrinhoService();

        finalizaCompra.idFormaPagamento = idFormapagamento;
        finalizaCompra.valorTotal = valortotal;
        finalizaCompra.cursos  = carrinhoService.getCursosCarrinho();
        finalizaCompra.nomeTitularCartao = nome;
        finalizaCompra.numeroCartao = numeroCartao;
        finalizaCompra.vencimentoCartao = validade;
        finalizaCompra.codigoSegurancaCartao = cvc;
        
        carrinhoService.finalizaCompra(finalizaCompra);


    }

    return (
        <div className="row" id="form_cartao">
            <div className="col-md-12">
                <div className="row">
                    <h5>Informe os dados do cartão</h5>
                </div>
                <div className="row" >
                    <div className="col-md-12">
                        <input type="text" 
                               name="nome" 
                               className="form-control"  
                               placeholder="Nome do titular no cartão"
                               onChange={e => {setNome(e.target.value)}}
                               />
                        <input type="text" 
                               name="numero"                                 
                               className="form-control"  
                               placeholder="Número do cartão" 
                               maxLength={19}
                               onChange={mascaraNumero}
                               value={numeroCartao}
                               />
                    </div>
                </div>
                <div className="row">
                    <div className="col-md-6">
                        <input type="text" 
                               name="validade" 
                               className="form-control" 
                               title="Validade do cartão" 
                               placeholder="MM/AA" 
                               maxLength={4}
                               onChange={mascaraValidade}
                               value={validade}

                               />
                    </div>
                    <div className="col-md-6">
                        <input type="number"
                               name="cvc"  
                               className="form-control"  
                               title="Código de segurança" 
                               placeholder="CVC" 
                               maxLength={3}                               
                               onChange={e => {setCvc(e.target.value)}}
                               />
                    </div>
                </div>
                <div className="row mt-2">
                    <div className="col-md-12 text-center">
                        <button className="btn btn-primary" onClick={prosseguir}>Prosseguir</button>
                    </div>
                </div>
            </div>
        </div>


    )
}