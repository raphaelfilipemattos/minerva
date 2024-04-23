import InterfaceModel from "@/models/InterfaceModel";
import style from "./form.module.css";
import ImageUploader from "../imageuploader/ImageUploader";
import Editor from  "@/bibliotecas/ckeditor/ckeditor";
import { CKEditor } from "@ckeditor/ckeditor5-react";

export  enum TipoCampo{
    string,
    number,
    boolean,
    map,
    TextArea,
    date,
    image
}

export default class CamposForm{
    nomeCampo?: string;
    displayCampo?: string;
    obrigatorio?: boolean;
    tipoCampo?: TipoCampo;
    maxLength?: number;
    descricao?: string;
    itens?: Array<InterfaceModel>;
    _editor: any;
   


    constructor( nomeCampo: string,
        displayCampo: string,
        obrigatorio: boolean,
        tipoCampo: TipoCampo,
        maxLength?: number,
        descricao?: string,
        itens?: Array<InterfaceModel>
        ){

       this.nomeCampo = nomeCampo;     
       this.displayCampo = displayCampo;     
       this.obrigatorio = obrigatorio;     
       this.tipoCampo = tipoCampo;     
       this.maxLength = maxLength;
       this.descricao = descricao;
       this.itens = itens;

       if (this.tipoCampo == TipoCampo.string && ! this.maxLength ){
         throw "Campo do tipo string tem que ter maxLength definido";
       }

       if (this.tipoCampo == TipoCampo.map && this.itens == null ){
        throw "Campo do tipo map tem que ter itens definido";
      }
    }

    private getTextArea(valorPadrao?: any){
           return (<CKEditor
                      editor={Editor}                       
                      data={valorPadrao} 
                      onReady={editor => {
                        this._editor = editor;
                      }}   
                      
                      onChange={() => {
                          const value =  this._editor.getData();
                          const event = {
                            target : {
                                name: "",
                                value: "",
                            }
                          }
                          event.target.name = this.nomeCampo || "";
                          event.target.value = value;
                          this.onChangeField(event);
                      }

                       
                    }

             />) 

        /*
        return  (<textarea 
                    className={style.input__field} 
                    name={this.nomeCampo} 
                    required={this.obrigatorio} 
                    defaultValue={valorPadrao}
                    onChange={event => {this.onChangeField(event)}}
                    ></textarea>)    ;
                    */
    }

    private getMap(valorPadrao?: any){
        return (this.itens &&
                 <select 
                       className={style.input__field} 
                       name={this.nomeCampo} 
                       required={this.obrigatorio}                    
                       onChange={event => {                        
                        this.onChangeField(event)}}
                       > 
                       <option>...</option>
                  {this.itens.map((item, key) =>{
                    const keyValue = item[item.getCampoId()];
                    const display = item[item.getCampoDisplay()];
                    const checked = keyValue == valorPadrao ? true : false;
                    return (<option key={key} value={keyValue} selected={checked}> {display} </option>);
                  })} 
                </select>  );                
    }

    private getImage(valorPadrao?: any){        
        return (<ImageUploader imagem64={valorPadrao} nameField={this.nomeCampo}  funOnSave={this.onChangeField} />);
    }

   

    private getInput( valorPadrao?: any){

        const input ={
            [TipoCampo.string]   : (<input 
                                            className={style.input__field} 
                                            name={this.nomeCampo} 
                                            required={this.obrigatorio} 
                                            type= "text"
                                            maxLength={this.maxLength}
                                            defaultValue={valorPadrao}                     
                                            onChange={event => {this.onChangeField(event)}} /> ),  
            [TipoCampo.boolean]  :  (<input 
                                            className={style.input__field} 
                                            name={this.nomeCampo} 
                                            required={this.obrigatorio} 
                                            type= "checkbox"
                                            maxLength={this.maxLength}
                                            defaultValue={1}
                                            defaultChecked = {valorPadrao == 1}                     
                                            onChange={event => {this.onChangeField(event)}} /> ),  
            [TipoCampo.number]   :  (<input 
                                            className={style.input__field} 
                                            name={this.nomeCampo} 
                                            required={this.obrigatorio} 
                                            type= "number"
                                            defaultValue={ valorPadrao}                     
                                            onChange={event => {this.onChangeField(event)}} /> ),  
            [TipoCampo.date]     : (            
                            <input 
                                        className={style.input__field} 
                                        name={this.nomeCampo} 
                                        required={this.obrigatorio} 
                                        type= "date"
                                        defaultValue={ valorPadrao}                     
                                        onChange={event => {this.onChangeField(event)}} /> ),
            [TipoCampo.map]      : this.getMap(valorPadrao),
            [TipoCampo.TextArea] : this.getTextArea(valorPadrao),
            [TipoCampo.image]    : this.getImage(valorPadrao)
        }         
        return input[this.tipoCampo];

     
    }

    render(key: number,  valorPadrao?: any){        
        return (
            <div className={"col-md-12   "+ style.input} key={key}>
                <label className={style.input__label}>{this.displayCampo}</label>
                {this.getInput(valorPadrao)}
                {this.descricao && (<p className={style.input__description}>{this.descricao}</p>)}
            </div>
        
            )
    }

    onChangeField(event){
        return event;
    }





}