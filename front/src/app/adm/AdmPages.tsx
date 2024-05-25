import TokenService from "@/services/TokenService";

export default function AdmPages(){
    
    const tokenService = new TokenService();

    if (! tokenService.isAdm()){
        location.href = location.origin;        
        return ;
    }


}