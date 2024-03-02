import { ConexaoPOST } from "@/infra/Conexao";
import LoginModel from "@/models/LoginModel";
import TokenService from "./TokenService";
import { redirect } from "next/navigation";



export default class LoginService {
    tokenService = new TokenService();

    async login(usuario,senha){
        const parametros = {
                                login : usuario,
                                senha: senha,
                                urlempresa: window.location.hostname
                            }    
        const resposta = await ConexaoPOST<LoginModel>("login", JSON.stringify(parametros))
                            .then(respostaServer => {                                                                
                                    return respostaServer;
                                }
                            );
        if (resposta == null){
            alert("Usuário ou senha inválidos")
        }                       
        
       return this.tokenService.gravaToken(resposta);

    }
    
}
