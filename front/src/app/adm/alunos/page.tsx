"use client"
import Pessoa from "@/componentes/pessoas/Pessoa";
import { AlunoModel } from "@/models/AlunoModel";

export default function AdmAlunosPage(){
    return Pessoa("usuarios/alunos", AlunoModel);
    

}
