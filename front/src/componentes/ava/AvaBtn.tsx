"use client"

import { ConexaoGET } from "@/infra/Conexao";
import LoginAvaModel from "@/models/LoginAva";
import { useEffect, useRef, useState } from "react";;
import { v4 as uuidv4 } from 'uuid';
import md5 from "md5";


export default function AvaBtn(){
    const [dadosAva, setDadosAVA] = useState<LoginAvaModel>();
    const uuid =  uuidv4();
    const logintoken = md5(uuid.toString().replaceAll('-',''));
    const formRef = useRef();

    useEffect(()=>{
        ConexaoGET("geral/loginava", true).then(resposta => {                
            setDadosAVA(resposta as LoginAvaModel);
        })
        
    },[])

    function submit(){
        formRef.current.submit();
    }

    return (
        <>           
            {dadosAva &&

               <form ref={formRef} method="POST" action={"http://"+dadosAva.url+"/login/index.php"} name="login"  id="login">
                    <input type="hidden" name="logintoken" defaultValue={ logintoken.toString().replaceAll('-','')}></input>
                    <input type="hidden"   name="username" id="username"  defaultValue={dadosAva.login} />
                    <input type="hidden"   name="password" id="password"  defaultValue={dadosAva.senha} />
                    <input type="submit" className="btn btn-outline-dark" onClick={submit} value={"Sala de aula"} />
               </form> 
            
            }

        </>
    )

    


}