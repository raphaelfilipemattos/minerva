import EmpresaModel from "@/models/EmpresaModel";
import TokenService from "@/services/TokenService";

async function Conexao(verbo: string, servico: string, parametros: any, usaJson: boolean = true) {
    const urlempresa = location.host.replace(":3000", '');
    const url = "http://localhost:8080/api/" + servico;
    const tokenService= new TokenService();    
    
    const headersList = {
        "urlempresa": urlempresa,             
        "Content-Type": "application/json", // Adicione o tipo de conteúdo, se aplicável
        
    };
    if (tokenService.isLogado()){
        headersList["Authorization"] = `Bearer ${tokenService.getToken().token}` ;
    }
    
    
    let respostaApi =  await fetch(url, {
                            method: verbo,
                            headers: headersList,
                            mode: "cors", 
                            cache: 'no-cache',
                            body: parametros
                        }).then(respostaApi =>{                                     
                            if (!respostaApi.ok) {
                                throw new Error(`Erro na solicitação: ${respostaApi.statusText}`);
                            }
                            return respostaApi;
                        }).catch(err => console.log(err))
    if (respostaApi== null) return null;
    
    if (usaJson){
        return await respostaApi.json();        
    }
    return respostaApi.text();
}


export async function ConexaoGET<T>(servico: string, usaJson: boolean = true): Promise<T>{
    return await Conexao("GET",servico,null, usaJson);
}

export async function ConexaoPOST<T>(servico: string, parametros: any): Promise<T>{
    return await Conexao("POST",servico,parametros);
}

export async function ConexaoPUT<T>(servico: string,id: String, parametros: any): Promise<T>{
    return  await Conexao("PUT",servico+"/"+id,parametros);
}

export async function ConexaoDELETE(servico: string,id: String){
    await Conexao("DELETE",servico+"/"+id,null, false);
}