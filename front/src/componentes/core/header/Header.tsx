"use client"

import { useContext, useEffect, useState } from "react";
import ItemMenu from "./ItemMenu";
import style from "./header.module.css";
import EmpresaServices from "@/services/EmpresaServices";
import EmpresaModel from "@/models/EmpresaModel";
import MenuUsuario from "./MenuUsuario";
import LoginModel from "@/models/LoginModel";
import TokenService from "@/services/TokenService";
import PerfilModel from "@/models/PerfilModel";
import MenuAdm from "./MenuAdm";


export default function Header(){
    const [empresa,setEmpresa] = useState<EmpresaModel>();
    const [usuarioLogado,setUsuarioLogado] = useState<LoginModel|undefined>(undefined);    
    const tokenService = new TokenService();        

    useEffect(()=>{
        setEmpresa(EmpresaServices());
        setUsuarioLogado(tokenService.getUsuarioLogado())       
    },[]); 
    
    
    
    return (
        empresa &&
        <header id="header" className={style.header+" fixed-top"}>
            <nav className="navbar navbar-expand-lg ">
                <div className="container-fluid">
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarTogglerDemo01">
                    <h1 className={style.logo+ " me-auto"}>
                       <a className="navbar-brand" href="/">{empresa.nomeEmpresa}</a>
                    </h1>                       
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <ItemMenu url="/" className="nav-link active" >Home</ItemMenu>                
                        <ItemMenu url="/cursos" className="nav-link" >Cursos</ItemMenu>
                        <ItemMenu url="/professores" className="nav-link" >Profesores</ItemMenu>
                        <ItemMenu url="/sobre" className="nav-link" >Sobre a empresa</ItemMenu>
                        {usuarioLogado &&
                            <MenuAdm/>
                        }
                        {usuarioLogado &&
                                <ItemMenu url="#"  className="nav-link" >Sala de aula</ItemMenu>                            
                        }                        
                    </ul>
                    {! usuarioLogado &&
                        <ul className="navbar-nav me-4 mb-2 mb-lg-0">
                            <ItemMenu url="/login" className="nav-link" >Área restrita</ItemMenu>
                        </ul>
                    }
                    {usuarioLogado &&
                      <MenuUsuario/>
                    }

                
                </div>
                </div>
            </nav>
        </header>
    );

}