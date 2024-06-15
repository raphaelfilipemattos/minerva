import CarrinhoService from "@/services/CarrinhoService";
import style from "./carrinho.module.css";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSquareMinus } from "@fortawesome/free-regular-svg-icons";

export default function Carrinho(){
    const carrinhoService = new CarrinhoService();
    const [qtdCursos,setQtdCursos] = useState(0);    
    const [detalhes,setDetalhes] = useState(false);
    const [total, setTotal] = useState(0);

    useEffect( ()=>{        
       setInterval(()=>{
           setQtdCursos(carrinhoService.count() );      
           const soma = carrinhoService.getCursosCarrinho().reduce((acumulador, cursoAtual) => {                           
            return acumulador + parseFloat(cursoAtual.valor);
            },0);

            setTotal(soma);        
       },2000)
    },[])

    function removeCursoCarrinho(curso){
        if ( confirm("Deseja realmente remover esse curso do carrinho? ")){
            carrinhoService.removeCurso(curso.idcurso);            
            return true;
        }

        return false;

    }

    return (
        <div>

            <button 
                   type="button" 
                   className={style.img_carrinho + " btn btn-light position-relative"}  
                   onClick={()=>{
                        setDetalhes(! detalhes)
                   }}               
                   >

                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-cart4" viewBox="0 0 16 16">
                        <path d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5M3.14 5l.5 2H5V5zM6 5v2h2V5zm3 0v2h2V5zm3 0v2h1.36l.5-2zm1.11 3H12v2h.61zM11 8H9v2h2zM8 8H6v2h2zM5 8H3.89l.5 2H5zm0 5a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0m9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2m-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0"/>
                 </svg>
                 <span className="position-absolute top-0 start-50 translate-middle badge rounded-pill bg-danger">
                       {qtdCursos > 0 ? qtdCursos : "" }              
                 </span>
            </button>
            { detalhes && 
                <div className={style.lista_cursos} id="itensCarrinho" >
                <div className="modal-dialog modal-sm">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5 text-center " id="itensCarrinhoLabel">Cursos no seu carrinho</h1>
                        <button type="button" className="btn-close" onClick={
                            function (){
                                setDetalhes(false);
                            }
                        } ></button>
                    </div>
                    <div className="modal-body">
                        <table className={" table table-responsive"}>
                            <thead>
                                <tr>
                                    <th key={0}>Curso</th>
                                    <th key={1}>Valor</th>
                                    <th key={2}></th>
                                </tr>
                            </thead>
                            <tbody>
                                {carrinhoService.getCursosCarrinho().map(curso =>{                            
                                return (<tr key={curso.idcurso}>
                                            <td>{curso.nomeCurso}</td>
                                            <td>{curso.valor}</td>
                                            <td>
                                                <a href="#"
                                                   onClick={function(){
                                                      removeCursoCarrinho(curso)
                                                   }}
                                                >
                                                    <FontAwesomeIcon 
                                                      icon={faSquareMinus} 
                                                      title="Remover" 
                                                      className={style.remover_curso}
                                                      
                                                      />
                                                </a>
                                                </td>
                                </tr>)
                                })}
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th scope="row" className="label_total">Total:</th>
                                    <td className="label_valortotal">{Intl.NumberFormat("pt-BR",{ style: 'currency', currency: 'BRL' }).format(total)}</td>
                                </tr>                                
                            </tfoot>
                        </table>
                    </div>
                    <div className="modal-footer">
                         {qtdCursos > 0 && <a href="/checkout" className="btn btn-success">Finalizar compra</a> }
                    </div>
                </div>
                </div>
                </div>


             }
            
        </div>

    )

}