import InterfaceModel from "./InterfaceModel";

class LoginAvaModel implements InterfaceModel 
{
    getCampoDisplay(): string {
        return "login";
    }
    public  getCampoId(): string {
        return "login";
    }
    public getDescricaoTabela(): string {
        return "Login no ava";
    }

    url?: string;
	login?: string;
	senha?: string;

}   


export default LoginAvaModel;