import { useState } from "react";
import style from "./form.module.css";
import CamposForm from "./CamposForm";

export default function Formulario({objeto, descricao,  
                                    campos,   
                                    gravaFormulario,    
                                    tituloFormulario,                            
                                    onClose, onSubmit} : 
                             {objeto: Object|null,descricao: string, 
                              campos: Array<CamposForm>,  
                              gravaFormulario: boolean,
                              tituloFormulario: string,
                              onClose: (event: MouseEvent)=> void, 
                              onSubmit:(dados: Object)=> void}){

    const [dadoAlterado, setDadoAlterado] = useState<Object|null>(objeto);
    function onChangeField(event){   
        let value = event.target.value ;        
        if (event.target.type == "checkbox" ){
            value = event.target.value == 1;
        }
        setDadoAlterado({...dadoAlterado, [event.target.name] : value});
            }

    return (

            <div className={style.container}>
                <div className={style.modal}>
                    <div className={style.modal__header}>
                        <span className={style.modal__title}>{tituloFormulario} {descricao}</span>
                                <button className={style.button +" "+  style.button__icon} onClick={event => { onClose(event) }}>
                                    <svg width="24" viewBox="0 0 24 24" height="24" xmlns="http://www.w3.org/2000/svg">
                                <path fill="none" d="M0 0h24v24H0V0z"></path>
                                <path d="M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12 19 6.41z"></path></svg></button>
                    </div>
                    <div className={style.modal__body}>
                       {campos.map( (campo, key) =>{                               
                          campo.onChangeField = onChangeField;
                          return campo.render(key, objeto[campo.nomeCampo])
                       })}
                    </div>

                    {gravaFormulario &&
                     <div className={style.modal__footer+ " text-center"}>
                        <button className={style.button+' '+ style.button__primary} onClick={event => onSubmit(dadoAlterado)} >Gravar</button>
                    </div>}
                </div>
            </div>
    );

}