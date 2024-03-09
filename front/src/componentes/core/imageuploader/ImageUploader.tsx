import { useState } from "react";
import style from "./imageuploader.module.css";
import { ConexaoGET } from "@/infra/Conexao";
export default function ImageUploader(
    {imagem64,nameField, funOnSave}:{imagem64: string,nameField: string, funOnSave: Function
}){
    const [imagem, setImagem] = useState<string>(imagem64);
    function onSelectedfile(event){        
        const file = new FileReader();
        file.readAsDataURL(event.target.files[0]);
        file.onload = function(){            
            setImagem(file.result?.toString());
            const event = {
                target : {
                    value: file.result?.toString(),
                    name: nameField
                }
            }            
            funOnSave(event);
        }

    }    
    if (imagem && imagem.startsWith("geral")){
        ConexaoGET<string>(imagem,false).then(image64 => {
            setImagem(image64);
        })
    }
   
    return (
        <div className={style.imageuploader+ " row "}>
            <div className="col-md-12">
                <div className={style.preview+ " row "}>
                    {imagem && 
                    <img src={imagem}                   
                    alt="Imagem" /> 
                    }
                    {!!!imagem && <span>Sem imagem para exibir</span>}
                </div>
                <div className={style.fileUpload+ " row "}>
                    <input 
                         type="file" 
                         name={nameField}
                         accept=".png, .jpg, .jpeg" 
                         onChange={onSelectedfile}                          
                         />
                </div>

            </div>
        </div>
    )
}