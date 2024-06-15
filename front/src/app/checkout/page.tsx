"use client"

import CursoModel from "@/models/CursoModel";
import CarrinhoService from "@/services/CarrinhoService";
import CursosServices from "@/services/CursosServices";
import TokenService from "@/services/TokenService";
import { useEffect, useState } from "react";
import style from "./checkout.module.css";
import FormasPagamento from "./formaspagamento";

export default function CheckoutPage(){
    const carrinhoService = new CarrinhoService();
    const cursoServices =  new CursosServices();
    const tokenService = new TokenService();
    const [cursos, setCursos] = useState<CursoModel[]>( carrinhoService.getCursosCarrinho() );
    const [total,setTotal] = useState(0);

    
    useEffect(()=>{        
        cursoServices.getCursos().then(cursosServices => {
            cursosServices?.map(item => {
                for(let index = 0; index < cursos.length; index++ ){
                    if (cursos[index].idcurso == item.idcurso ){
                        cursos[index].valor = item.valor;
                        carrinhoService.removeCurso(cursos[index].idcurso);
                        carrinhoService.addCurso(cursos[index]);
                        const soma = carrinhoService.getCursosCarrinho().reduce((acumulador, cursoAtual) => {                           
                            return acumulador + parseFloat(cursoAtual.valor);
                            },0);
                    
                        setTotal(soma);    
                    }
                }   
            })
            setCursos(carrinhoService.getCursosCarrinho());
        })
        
        
        
    },[]);
    
    if (! tokenService.isLogado()){
        location.href = location.origin+"/login";
        return;
    }
    
    
    return (
        <section>
            <div className="row text-center border-bottom mb-1">
                <h2>Finalizar compra</h2>
            </div>
            <div className="row">
                <div className="col-md-4">
                    <div className="row">
                        <h5>Carrinho</h5>
                        <div className="col-md-7">
                            <ul className={style.lista_cursos}>
                                {cursos.map((item,index) =>{
                                    return (
                                        <li key={index}>
                                            <span>{item.nomeCurso}</span>
                                            <span>{Intl.NumberFormat("pt-BR",{ style: 'currency', currency: 'BRL' }).format(item.valor)}</span>
                                        </li>
                                    )
                                })}
                            </ul>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-4">
                            <span>Total:</span>        
                        </div>
                        <div className="col-md-3 text-end">
                        <span>{Intl.NumberFormat("pt-BR",{ style: 'currency', currency: 'BRL' }).format(total)}</span>
                        </div>
                    </div>
                </div>   
                <div className="col-md-6">
                    <FormasPagamento
                       valorTotal={total}
                    />
                </div>                            
            </div>           
        </section>
    )
}