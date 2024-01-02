package br.com.minerva.minerva.service.criaAmbiente;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.domain.Empresa;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class CriaMoodleData implements ICriaAmbiente{

    @Autowired
    private final AdicionaDadosPadrao adicionaDadosPadrao;

    public CriaMoodleData(final AdicionaDadosPadrao adicionaDadosPadrao) {
        this.adicionaDadosPadrao = adicionaDadosPadrao;
    }

    @Override
    public ICriaAmbiente execute(Empresa empresa) throws Exception {
        File destino = new File(Ambiente.localDadosAvaEmpresa(empresa));
        if ( ! destino.exists()) {
            try {
                FileUtil.copyDir(new File(Ambiente.localDadosAvaPadrao()), destino);
            } catch (IOException e) {
                throw new Exception("Erro ao criar ambiente do moodle." + e.getMessage());
            }
        }


        return this.adicionaDadosPadrao.execute(empresa);
    }


}
