"use client"
import TokenService from "@/services/TokenService";
import { redirect } from "next/navigation";
import { useEffect } from "react";


export default function LogoffPage(){
    useEffect(()=>{
        const tokenService = new TokenService()
        tokenService.deleteToken();
        setTimeout(function(){
            window.location.href = "/";
        },1000);
        
    },[])

    return (
        <section>
            <h1>Deslogando...</h1>
        </section>
    )
   
} 