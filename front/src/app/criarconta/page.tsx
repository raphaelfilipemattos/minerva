"use client"

import { useState } from "react";
import style from "./criarconta.module.css";
import { UsuarioModel } from "@/models/UsuarioModel";
import ValidadorCPF from "@/bibliotecas/helpers/ValidadorCPF";
import {ValidadorSenha,ValidacaoSenha} from "@/bibliotecas/helpers/ValidaSenha";
import { ConexaoPOST } from "@/infra/Conexao";
import { redirect } from "next/navigation";


export default function CriarConta(){
    const [dados,setDados] = useState<UsuarioModel>( new UsuarioModel());
    const [senha2,setSenha2] = useState<string>( "");    
    const [validity, setValidity] = useState<ValidacaoSenha>(new ValidacaoSenha());


    function mascaraCPF(event){
        const value= event.target.value;
        const cpfMascara =  value
                    .replace(/\D/g, '') // substitui qualquer caracter que nao seja numero por nada
                    .replace(/(\d{3})(\d)/, '$1.$2') // captura 2 grupos de numero o primeiro de 3 e o segundo de 1, apos capturar o primeiro grupo ele adiciona um ponto antes do segundo grupo de numero
                    .replace(/(\d{3})(\d)/, '$1.$2')
                    .replace(/(\d{3})(\d{1,2})/, '$1-$2')
                    .replace(/(-\d{2})\d+?$/, '$1') // captura 2 numeros seguidos de um traço e não deixa ser digitado mais nada
      
        setDados(dados => ({...dados, cpf: cpfMascara}))
        
    }
    function onChange(event){
        const value= event.target.value;
        const propriedade = event.target.name;
        if (propriedade == 'senha2'){
            setSenha2(value);
            return;
        }
        setDados({...dados, [propriedade] : value})
      
    }
    function gravar(event){
        event.preventDefault();
        let erro = false;
        for (let item of document.querySelectorAll("input[required]") ){
            
            if(item.value == ""){
                const label = item.parentElement.querySelector("label[for='"+item.name+"']").textContent;
                alert(`O campo ${label} não pode ficar vazio`);
                item.focus();
                erro = true;                
                break;
            }
        }
        if (erro) return false;

        if (senha2 !== dados.senha){
            alert("As senhas não conferem");
            document.getElementById("senha")?.focus();
            return false;
        }

        if (! ValidadorCPF.valida(dados.cpf)){
            alert("CPF inválido");
            document.getElementById("cpf")?.focus();
            return false;
        }
        setValidity( ValidadorSenha.valida(dados.senha) );
        if (! validity.isOK){
            alert("A senha não está forte");
            document.getElementById("senha")?.focus();
            return false;
        }

        dados.cpf = dados.cpf?.replaceAll(".","").replace("-","").trim();

        ConexaoPOST("usuarios",JSON.stringify(dados) ).then(resposta => {
            alert("Cadastro realizado com sucesso!");
            location.href = location.origin+"/login";
            return;
        })
    }
    return(
         <section>
            <form className={style.form}>
                <span className={style.title}>Crie sua conta</span>
                <label htmlFor="nome" className={style.label}>Nome</label>
                <input type="text" 
                       id="nome" 
                       name="nome" 
                       required={true} 
                       className={style.input} 
                       onChange={onChange}
                       autoFocus
                       />
                <label htmlFor="cpf" className={style.label} >CPF</label>
                <input type="text" 
                       id="cpf" 
                       name="cpf" 
                       maxLength={15} 
                       required={true} 
                       className={style.input} 
                       onChange={ mascaraCPF }
                       value={dados.cpf} 
                       />
                <label htmlFor="email" className={style.label}>E-mail</label>
                <input type="email" id="email" name="email" required={true} className={style.input} onChange={onChange}/>
                <label htmlFor="senha" className={style.label}>Senha</label>
                <input type="password" 
                       id="senha" 
                       name="senha" 
                       required={true} 
                       className={style.input}  
                       onChange={onChange}
                       onKeyDownCapture={ event => {
                        const senha = event.target.value;
                        setValidity( ValidadorSenha.valida(senha) );
                       }}
                       />
                {!validity.isOK && (
                     <ul>
                        <li>Tem letra maiúscula: {validity.uppercase ? 'Sim' : 'Não'}</li>
                        <li>Tem letra minúscula: {validity.lowercase ? 'Sim' : 'Não'}</li>
                        <li>Tem número: {validity.number ? 'Sim' : 'Não'}</li>
                        <li>Tem caractere especial: {validity.specialChar ? 'Sim' : 'Não'}</li>
                        <li>Tamanho mínimo de 8 caracteres: {validity.length ? 'Sim' : 'Não'}</li>
                    </ul>
                )}
                <label htmlFor="senha2" className={style.label}>Repita a senha</label>
                <input type="password" 
                       id="senha2" 
                       name="senha2" 
                       required={true} 
                       className={style.input} 
                       onChange={onChange}/>
                <button type="submit" className={style.submit} onClick={gravar}>Cadastre-se</button>
            </form>   
         </section>
    );
}