package br.com.minerva.minerva.service.criaAmbiente;

import br.com.minerva.minerva.domain.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AdicionaDadosPadrao implements ICriaAmbiente{

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final ConfiguraAmbiente configuraAmbiente;


    public AdicionaDadosPadrao(final JdbcTemplate jdbcTemplate,final ConfiguraAmbiente configuraAmbiente) {
        this.jdbcTemplate = jdbcTemplate;
        this.configuraAmbiente = configuraAmbiente;
    }

    private void copiaPerilUsuario(Empresa empresa) throws Exception {
        try{
            String query = "delete from perfil_empresa where idempresa = '"+empresa.getIdempresa()+"' ";
            this.jdbcTemplate.execute(query);
             query = "insert into perfil_empresa( idperfil_empresa, idempresa, idperfil, idperfil_moodle)   " +
                            "select gera_chave() idperfil_empresa, '%%%%' idempresa, idperfil, id_moodle_padrao " +
                            "  from perfil" ;
            query = query.replace("%%%%",empresa.getIdempresa().toString());
            this.jdbcTemplate.execute(query);
        }catch (Exception e){
            throw new Exception("Erro ao criar banco de dados para a empresa "+empresa.getIdempresa()+" "+e.getMessage());
        }
    }
    @Override
    public ICriaAmbiente execute(Empresa empresa) throws Exception {
          this.copiaPerilUsuario(empresa);

        return this.configuraAmbiente.execute(empresa);
    }

}