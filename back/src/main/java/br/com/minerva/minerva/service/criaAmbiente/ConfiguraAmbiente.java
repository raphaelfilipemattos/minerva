package br.com.minerva.minerva.service.criaAmbiente;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.config.DatabaseConfig;
import br.com.minerva.minerva.domain.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sound.midi.Patch;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ConfiguraAmbiente implements ICriaAmbiente{
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public ConfiguraAmbiente(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void alteraConfig(Empresa empresa) throws Exception {
        // altera o arquivo de configuracao;
        try {
            Path arquivo = Paths.get(Ambiente.localAvaEmpresa(empresa) + "/config.php");
            String config = Files.readString(arquivo);
            config =  config.replaceAll("padrao", empresa.getNomeAmbiente());
            Files.writeString(arquivo, config);

        }catch (Exception e){
            throw new Exception("Erro ao alterar o arquivo config.php ."+e.getMessage());
        }
    }

    @Override
    public ICriaAmbiente execute(Empresa empresa) throws Exception {
        //var dataSource = DatabaseConfig.getDataSource(Ambiente.nomeBancoMoodle(empresa),Ambiente.senhaPadraoMoodle(empresa));
        //this.jdbcTemplate.setDataSource(dataSource);
        this.alteraConfig(empresa);

        //define adm
        //configura entrar sem fazer login
        //Usar estilo definido
        //alterar logo
        return null;
    }


}
