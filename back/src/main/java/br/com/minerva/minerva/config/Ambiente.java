package br.com.minerva.minerva.config;

import br.com.minerva.minerva.domain.Empresa;
import org.springframework.stereotype.Component;

@Component
public class Ambiente {
    public static String senhaPadraoMoodle(Empresa empresa){
        return empresa.getNomeAmbiente()+"ava_M1n3rv@";
    }

    public static String nomeBancoMoodle(Empresa empresa){
        return "moodle_"+empresa.getNomeAmbiente();
    }

    public static String localLogoEmpresa(){
        return new String("/var/www/html/img/logo/");
    }

    private static String localAva(){
        return "/var/www/html/";
    }

    private static String localDadosAva(){
        return "/var/www/html/moodledata/";
    }


    public static String localAvaPadrao(){
        return Ambiente.localAva()+"padrao";
    }

    public static String localDadosAvaPadrao(){
        return Ambiente.localDadosAva()+"padrao";
    }

    public static String localAvaEmpresa(Empresa empresa){
        return Ambiente.localAva()+empresa.getNomeAmbiente();
    }

    public static String localDadosAvaEmpresa(Empresa empresa){
        return Ambiente.localDadosAva()+empresa.getNomeAmbiente();
    }

}
