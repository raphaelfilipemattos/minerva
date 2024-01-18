package br.com.minerva.minerva.config;

import br.com.minerva.minerva.domain.Empresa;
import br.com.minerva.minerva.model.UsuarioDTO;
import br.com.minerva.minerva.repos.EmpresaRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.http.HttpRequest;

@Component
public class Ambiente {
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private HttpServletRequest httpServletRequest;
    
    public static String senhaPadraoMoodle(Empresa empresa){
        return empresa.getNomeAmbiente()+"ava_M1n3rv@";
    }

    public static String nomeBancoMoodle(Empresa empresa){
        return "moodle_"+empresa.getNomeAmbiente();
    }

    public static String localLogoEmpresa(){
        return new String("/var/www/html/img/logo/");
    }

    public static String localCapaCurso(){
        return new String("/var/www/html/img/capas/");
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

    public static UsuarioDTO getUsuarioLogado(){
        return (UsuarioDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public  Empresa getEmpresaAtual(){
        var urlEmpresa = this.httpServletRequest.getHeader("urlempresa");
        if (urlEmpresa == null){
            throw new RuntimeException("Header urlempresa não definido ");
        }
        var empresa  = this.empresaRepository.findByDominio(urlEmpresa);
        if (empresa == null){
            throw new RuntimeException("Empresa não localizada com a url informada ");
        }
        return empresa;
    }

}
