import style from "./form.module.css";
export  enum TipoCampo{
    string,
    number,
    boolean,
    map,
    TextArea,
    date
}

export default class CamposForm{
    nomeCampo?: string;
    displayCampo?: string;
    obrigatorio?: boolean;
    tipoCampo?: TipoCampo;
    maxLength?: number;
    descricao?: string;
   


    constructor( nomeCampo: string,
        displayCampo: string,
        obrigatorio: boolean,
        tipoCampo: TipoCampo,
        maxLength?: number,
        descricao?: string){

       this.nomeCampo = nomeCampo;     
       this.displayCampo = displayCampo;     
       this.obrigatorio = obrigatorio;     
       this.tipoCampo = tipoCampo;     
       this.maxLength = maxLength;
       this.descricao = descricao;

       if (this.tipoCampo == TipoCampo.string && ! this.maxLength ){
         throw "Campo do tipo string tem que ter maxLength definido";
       }
    }

    private getTextArea(valorPadrao?: any){
        return  (<textarea 
                    className={style.input__field} 
                    name={this.nomeCampo} 
                    required={this.obrigatorio} 
                    defaultValue={valorPadrao}
                    onChange={event => {this.onChange(event)}}
                    ></textarea>)    ;
    }

    private getMap(valorPadrao?: any){
        //const valoresMap= new Map<string,string>(valorPadrao);
        return (<select className={style.input__field} name={this.nomeCampo} required={this.obrigatorio}> 
                   
                </select>  );                
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
                                            onChange={event => {this.onChange(event)}} /> ),  
            [TipoCampo.boolean]  :  (<input 
                                            className={style.input__field} 
                                            name={this.nomeCampo} 
                                            required={this.obrigatorio} 
                                            type= "checkbox"
                                            maxLength={this.maxLength}
                                            defaultValue={1}
                                            defaultChecked = {valorPadrao == 1}                     
                                            onChange={event => {this.onChange(event)}} /> ),  
            [TipoCampo.number]   :  (<input 
                                            className={style.input__field} 
                                            name={this.nomeCampo} 
                                            required={this.obrigatorio} 
                                            type= "number"
                                            defaultValue={valorPadrao}                     
                                            onChange={event => {this.onChange(event)}} /> ),  
            [TipoCampo.date]     : (<input 
                                        className={style.input__field} 
                                        name={this.nomeCampo} 
                                        required={this.obrigatorio} 
                                        type= "date"
                                        defaultValue={valorPadrao}                     
                                        onChange={event => {this.onChange(event)}} /> ),
            [TipoCampo.map]      : this.getMap(valorPadrao),
            [TipoCampo.TextArea] : this.getTextArea(valorPadrao)
        }         
        return input[this.tipoCampo];

     
    }

    render(key: number,  valorPadrao?: any){        
        return (
            <div className={style.input} key={key}>
                <label className={style.input__label}>{this.displayCampo}</label>
                {this.getInput(valorPadrao)}
                {this.descricao && (<p className={style.input__description}>{this.descricao}</p>)}
            </div>
        
            )
    }

    onChange(event){
        return event;
    }





}