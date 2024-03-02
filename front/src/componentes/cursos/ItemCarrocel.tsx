import Image from 'next/image'
import style from "./curso.module.css";
import React from 'react';
import Link from 'next/link';

export default function ItemCarrocel({curso,indexItem} : {curso: CursoModel,indexItem: Number}){
    let isActive = indexItem == 0? "active":"";
    return(
        <div className="col-lg-4 col-md-6 d-flex align-items-stretch">
            <div className= {style.course_item}>
                <Image
                src="/img/curso.jpg" 
                className="img-fluid" 
                alt="..."
                width={500}
                height={500}
                />
                <div className={style.course_content}>
                    <div className="d-flex justify-content-between align-items-center mb-3">
                        <h4>{curso.nomeCurso}</h4>
                        <p className="price">R$ {curso.valor?.toString()}</p>
                    </div>

                    <h3>
                        <Link
                          href={{
                             pathname: "detalhescurso",
                             query: {"curso": JSON.stringify(curso)}
                          }}>
                            {curso.nomeCurso}
                        </Link>    
                        
                    </h3>
                    <p>{curso.descricaoCompleta}</p>                    
                </div>
            </div>
        </div>
    );

}