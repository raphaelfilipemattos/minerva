"use client"

import Pessoa from "@/componentes/pessoas/Pessoa";
import { UsuarioModel } from "@/models/UsuarioModel";

export default function AdmUsuarioPage(){
    return Pessoa("usuarios", UsuarioModel);
    

}
