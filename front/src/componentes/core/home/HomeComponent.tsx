import Curso from "@/componentes/cursos/Curso";
import style from "./home.module.css";
import Hero from "../../hero/Hero";
import Sobre from "@/componentes/sobre/Sobre";
import { useEffect } from "react";
import Professores from "@/componentes/professores/Professores";




export default function HomeComponent(){
  
    return (
        <div className={style.home} >
             <Hero/>
             <Sobre/>
             <Curso/>
             <Professores/>
        </div>
    );
}