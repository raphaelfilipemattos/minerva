import EmpresaModel from "@/models/EmpresaModel";

export default function EmpresaServices(){
    let empresaModel= new EmpresaModel();
    empresaModel.idEmpresa = "1f812870-f816-46d6-921e-4621f9dfc1ab";
    empresaModel.nomeEmpresa = "ITE - Recreio";
    empresaModel.dominioAva = "iterecreio.ava.com.br";
    empresaModel.logo = "57715e61-706a-412b-8d76-32d6a7b128dc";
    empresaModel.personalizacao = null;
    empresaModel.idPacote = "e3416751-d5d8-4792-b803-26d1c0338e25";
    empresaModel.nomeAmbiente = "9dabcd4ad2d46e486a7";

    return empresaModel;
}