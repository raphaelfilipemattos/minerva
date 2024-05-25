"use client"

import { useEffect, useState } from "react";
import style from "./professor.module.css";
import { ProfessorModel } from "@/models/ProfessorModel";
import { ConexaoGET } from "@/infra/Conexao";
import Image from "next/image";
export default function Professores(){
  
  const [professores, setProfessores] = useState<Array<ProfessorModel>>(); 

  useEffect(()=>{
     ConexaoGET<Array<ProfessorModel>>("usuarios/professores").then(data => {        
        let professoresArray = new Array<ProfessorModel>();
        professoresArray = data.map(item => item as ProfessorModel);            
        setProfessores(professoresArray);

     })
  },[])  

  return(
      professores &&
      <section id="professores" className={style.professores+" container"} data-aos="zoom-in-up" data-aos-easing="linear"  data-aos-duration="1500">
          <div className="section-title">
            <p>Nosso time de Professores</p>                    
          </div>
          <div className="row">         
            {professores.map((professor) =>(            
                <div key={professor.idusuario} className="col-lg-4 col-md-6 d-flex align-items-stretch">    
                    <div className={style.member}>
                        <Image 
                            src={professor.foto || "/img/professor.jpg"} 
                            className="img-fluid"
                            width={200} 
                            height={300}
                            alt=""/>
                        <div className={style.member_content}>
                            <h4>{professor.nome}</h4>                                                
                        </div>
                        </div>   
                </div>
            ) )}
         </div> 
      </section>)
}