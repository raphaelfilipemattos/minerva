"use client"

import { useEffect, useState } from "react";
import ItemCarrocel from "./ItemCarrocel";
import style from "./curso.module.css";
import CursosServices from "@/services/CursosServices";

export default function Curso(){
    const[cursos,setCursos] = useState<Array<CursoModel>>();
    const cursosSerices = new CursosServices();
    useEffect(() => {
        cursosSerices.getCursos().then(data=> {
            let cursosArray = new Array<CursoModel>();
            cursosArray = data.map(item => item as CursoModel);            
            setCursos(cursosArray);
        })
        
    },[]);

    return (
        cursos &&
        <section id="cursos" className={style.curso} data-aos="zoom-in-up" data-aos-easing="linear"  data-aos-duration="1500">
            <div className="container" >
                <div className="section-title">
                    <p>Nossos Cursos</p>
                </div>
                <div className="row" >
                     {cursos.map((curso,index) =>(
                             <ItemCarrocel key={index} curso={curso} indexItem={index} />
                           ))
                    }
                   </div>
             </div>
        </section>
    );

}