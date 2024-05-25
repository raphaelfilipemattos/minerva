"use client"

import Pessoa from "@/componentes/pessoas/Pessoa";
import { UsuarioModel } from "@/models/UsuarioModel";
import AdmPages from "../AdmPages";

export default function AdmUsuarioPage(){
    AdmPages();
    return Pessoa("usuarios", UsuarioModel);
    

}
