"use client"

import Pessoa from "@/componentes/pessoas/Pessoa";
import { ProfessorModel } from "@/models/ProfessorModel";


export default function AdmProfessoresPage(){
    return Pessoa("usuarios/professores", ProfessorModel);
    

}
