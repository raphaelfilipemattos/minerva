package br.com.minerva.minerva.service;

import br.com.minerva.minerva.repos.EmpresaRepository;
import br.com.minerva.minerva.service.criaAmbiente.CriaBancoDados;
import br.com.minerva.minerva.service.criaAmbiente.ICriaAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CriaAmbienteService {

    @Autowired
    private final EmpresaRepository empresaRepository;

    @Autowired
    private ICriaAmbiente criaBancoDados;

    public CriaAmbienteService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    private void addLog(boolean erro, String msgErro){

    }

    //@Scheduled(fixedRate = 20 , timeUnit = TimeUnit.SECONDS)
    public void criaAmbiente(){
       try {
          var empresas = this.empresaRepository.findAllByAmbientecriadoIsFalse();
          for(var empresa: empresas){
              this.criaBancoDados.execute(empresa) ;
              empresa.setAmbientecriado(true);
              this.empresaRepository.save(empresa);
          }
       }catch (Exception e){
           this.addLog(true, e.getMessage());
       }

    }


}
