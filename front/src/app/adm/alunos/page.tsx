"use client"
import Pessoa from "@/componentes/pessoas/Pessoa";
import { AlunoModel } from "@/models/AlunoModel";
import AdmPages from "../AdmPages";

export default function AdmAlunosPage(){
    AdmPages();
    return Pessoa("usuarios/alunos", AlunoModel);
    

}
