package br.com.minerva.minerva.service.criaAmbiente;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.domain.Empresa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CriaUsuarioBanco implements ICriaAmbiente{

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final CriaMoodle criaMoodle;

    public CriaUsuarioBanco(final JdbcTemplate jdbcTemplate, final CriaMoodle criaMoodle) {
        this.jdbcTemplate = jdbcTemplate;
        this.criaMoodle = criaMoodle;
    }

    private boolean usuarioJaExiste(Empresa empresa){
        String query = "select usename from pg_user where usename = 'moodle_%%%%%'";
        query = query.replace("moodle_%%%%%","moodle_"+empresa.getNomeAmbiente());
        var resposta = this.jdbcTemplate.queryForRowSet(query);
        return ! (resposta.isFirst() && resposta.isLast());
    }

    private boolean criaUauario(Empresa empresa) throws Exception {
        try {
            String query = "CREATE ROLE moodle_%%%% WITH LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT NOREPLICATION CONNECTION LIMIT -1 PASSWORD 'SENHA';";
            query = query.replace("moodle_%%%%", "moodle_" + empresa.getNomeAmbiente())
                    .replace("SENHA", Ambiente.senhaPadraoMoodle(empresa));

            this.jdbcTemplate.execute(query);
            return true;
        } catch (Exception e) {
            throw new Exception("Erro ao criar usuário do banco de dados para a empresa " + empresa.getIdempresa() + " " + e.getMessage());
        }

    }

    private void daAcesso(Empresa empresa) throws Exception {
        String query = "GRANT USAGE ON SCHEMA public TO moodle_padrao; " +
                " ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public " +
                " GRANT INSERT, SELECT, UPDATE, DELETE ON TABLES TO moodle_padrao; " +
                " ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public " +
                " GRANT ALL ON SEQUENCES TO moodle_padrao; " +
                " ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public "+
                " GRANT EXECUTE ON FUNCTIONS TO moodle_padrao; " +
                " ALTER DEFAULT PRIVILEGES FOR ROLE postgres IN SCHEMA public " +
                " GRANT USAGE ON TYPES TO moodle_padrao; ";
        query = query.replaceAll("moodle_padrao", Ambiente.nomeBancoMoodle(empresa));

        try {
            this.jdbcTemplate.execute(query);
        } catch (Exception e) {
            throw new Exception("Erro ao dar acesso ao usuário do banco de dados para a empresa " + empresa.getIdempresa() + " " + e.getMessage());

        }
    }

    @Override
    public ICriaAmbiente execute(Empresa empresa) throws Exception {
        if ( ! this.usuarioJaExiste(empresa) ) {
            if (this.criaUauario(empresa)){
                this.daAcesso(empresa);
            }
        }

        return this.criaMoodle.execute(empresa);
    }


}
