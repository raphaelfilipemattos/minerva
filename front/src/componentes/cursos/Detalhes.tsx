"use client"
import Image from "next/image";
import { redirect, useSearchParams } from "next/navigation";
import style from "./curso.module.css";
import CarrinhoService from "@/services/CarrinhoService";
import CursoModel from "@/models/CursoModel";
import { useState } from "react";
import { ConexaoGET } from "@/infra/Conexao";




export default function DetalhesCurso(){
    const [imagem, setImagem] = useState<string>("/img/curso.jpg");
    const carrinhoService = new CarrinhoService();
    
    const hash = useSearchParams().get("hash")
    
    if ( hash == undefined ){
        return redirect("/");
    }   
    const cursos =  JSON.parse(localStorage.getItem("cursos"));
    const curso = cursos.filter(item=> item.idcurso == hash)[0] as CursoModel;
    if (curso == null){
        return redirect("/");
    }

    if (curso.imagemCapa != null ){
        ConexaoGET<string>(curso.imagemCapa,false).then(image64 => {
                setImagem(image64);
            })
    }    
    function addCarrinho(){
      carrinhoService.addCurso(curso)
    }

   return (
            <section id="course-details" className={style.courseDetails}>
                <div className="container" data-aos="fade-up">
                    <div className="row">
                        <div className="col-lg-8">
                            <Image 
                                src={imagem} 
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