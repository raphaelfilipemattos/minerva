"use client"
import Image from "next/image";
import { redirect, useSearchParams } from "next/navigation";
import style from "./curso.module.css";
import CarrinhoService from "@/services/CarrinhoService";
import CursoModel from "@/models/CursoModel";




export default function DetalhesCurso(){
    const carrinhoService = new CarrinhoService();
    
    const query = useSearchParams().get("curso")
    if ( query == undefined ){
        return redirect("/");
    }   
    
    const curso = JSON.parse(query) as CursoModel;
    
    function addCarrinho(){
      carrinhoService.addCurso(curso)
    }

   return (
            <section id="course-details" className={style.courseDetails}>
                <div className="container" data-aos="fade-up">
                    <div className="row">
                        <div className="col-lg-8">
                            <Image 
                                src="/img/curso.jpg" 
                                className="img-fluid" 
                                alt=""
                                width={500}
                                height={500}                                
                                />
                            <h3>{curso.nomeCurso}</h3>
                            <p>
                                {curso.descricaoCompleta}
                            </p>
                        </div>
                        <div className="col-lg-4">
                            <div className={style.course_info+ " d-flex justify-content-between align-items-center"}>
                                <h5>Valor</h5>
                                <p> R$ {Number.parseFloat(curso.valor).toLocaleString("pt-BR") }</p>
                            </div>
                            <div className={style.course_info+ " d-flex justify-content-center align-items-center"}>
                                <button type="button" className="btn btn-success" onClick={addCarrinho}>Adionar no carrinho</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
   );
  

}