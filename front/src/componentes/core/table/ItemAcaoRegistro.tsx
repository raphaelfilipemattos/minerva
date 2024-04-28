import { ConexaoDELETE, ConexaoGET, ConexaoPOST, ConexaoPUT } from "@/infra/Conexao";
import { library } from '@fortawesome/fontawesome-svg-core';
import {IconPack, far } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default class ItensAcaoRegistro{
    titulo?: string;
    imagem?: any;
    verbo : string = 'PUT';
    endpoint?: string; 
    usaIdUrl?: boolean;
    body?: Object;
    msgConfirma? : string; 

    onShow(dado){
        return true;
    }
    
    async doOnClick(dado, valorId){
        this.msgConfirma = this.msgConfirma ?? this.titulo;
        if (! confirm(this.msgConfirma)) return;

        const id = this.usaIdUrl ? "/"+valorId: "";
        let resposta ;

        switch (this.verbo.toUpperCase()){
           case "GET":  resposta = await ConexaoGET(this.endpoint+id);
                       break; 
           case "PUT": resposta = await ConexaoPUT(this.endpoint,valorId, JSON.stringify(dado));
                       break; 
           case "POST": resposta = await ConexaoPOST(this.endpoint+id, JSON.stringify(dado));
                       break;
           case "DELETE": resposta = await ConexaoDELETE(this.endpoint, valorId);
                       break;            
        }
        

        if(resposta){
            alert("Dados salvos com sucesso!");
            window.location.reload(); 
         }else{
             alert("Ocorreu um erro ao gravar!");
             console.log(resposta);  
         }
        
    }

    onClick(event, dado,campoId){
        this.doOnClick(dado,dado[campoId])
    }

    render(dado, campoId,key){
        if (! (this.onShow(dado))) {
            return (<></>);
        }

        library.add(far)
        return (
            <FontAwesomeIcon 
            key={key}
            icon= {this.imagem } 
            title={this.titulo}                                                                               
            onClick={event => {
                    this.onClick(event, dado, campoId);
                }
            }
            />
        );
    }

}