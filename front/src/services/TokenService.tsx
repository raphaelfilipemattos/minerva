
import LoginModel from "@/models/LoginModel";
import nookies from 'nookies';

class TokenService{

    gravaToken(loginModel:LoginModel, ctx= null){
        if (! loginModel) return false;
        try{
            
            nookies.set(ctx,"token", loginModel.token || "",{
                maxAge: 3600*60*60, //2 horas,
                path: "/",
                sameSite: "lax",
                secure: location.protocol == "https:",
                httpOnly: false
            });
            nookies.set(ctx,"usuario", JSON.stringify(loginModel) || "",{
                maxAge: 3600*60*60, //2 horas,
                path: "/",
                sameSite: "lax",
                secure:  location.protocol == "https:",
                httpOnly: false
            });
            return true;
        }catch(err){
            console.log("erro ao gravar cookie: "+err);
            return false;
        }
    }

    getToken(ctx = null){
        return nookies.get(ctx,"token");
    }

    deleteToken(ctx = null){
        try {
            nookies.destroy(ctx, "token");
            nookies.destroy(ctx, "usuario");
            
        } catch (error) {
            console.log(error)
        }
    }

    isLogado(ctx =null){        
        return  Object.keys(this.getToken(ctx)).length > 0;
    }

    getUsuarioLogado(ctx = null): LoginModel | undefined{
        try {
            const usuario = nookies.get(ctx, "usuario");               
            const json = JSON.parse(usuario.usuario);                
            return json;
            
        } catch (error) {
            return undefined;
        }
        
    }
    isAdm(){
        const usuario = this.getUsuarioLogado();
        const perfilAdm = ["2c25bbf1-3595-4b58-870b-3a5c322a18db", "6a2cac68-1001-412b-8c32-8bc43bd44689"];

        if (! usuario) return false;
        if (usuario.perfil ){
            const perfis = usuario.perfil || [];
            const ids = perfis.map(perfil => perfil.idPerfil);            
            return ids.findIndex(idPerfil => perfilAdm.includes(idPerfil|| "")) >=0 ;
         }
    }


}


export default TokenService;