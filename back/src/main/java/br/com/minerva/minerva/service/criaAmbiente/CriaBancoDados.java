package br.com.minerva.minerva.service.criaAmbiente;

import br.com.minerva.minerva.config.Ambiente;
import br.com.minerva.minerva.domain.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class CriaBancoDados implements ICriaAmbiente {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final CriaUsuarioBanco criaUsuarioBanco;

    public CriaBancoDados(final JdbcTemplate jdbcTemplate, final CriaUsuarioBanco criaUsuarioBanco) {

        this.jdbcTemplate = jdbcTemplate;

        this.criaUsuarioBanco = criaUsuarioBanco;
    }

    private boolean bancoJaExiste(Empresa empresa){
        String query = "select datname " +
                       "  from pg_database " +
                       " where datname = 'moodle_%%%%' ";
        query = query.replace("moodle_%%%%", Ambiente.nomeBancoMoodle(empresa));
        var resposta = this.jdbcTemplate.queryForRowSet(query);
        return ! (resposta.isFirst() && resposta.isLast());
    }

    @Override
    public ICriaAmbiente execute(Empresa empresa) throws Exception {
        if (! this.bancoJaExiste(empresa)) {
            try {
                String query = "CREATE DATABASE moodle_%%%% WITH OWNER = postgres TEMPLATE = moodle_padrao ENCODING = 'UTF8' LOCALE_PROVIDER = 'libc' CONNECTION LIMIT = -1 IS_TEMPLATE = False;";
                query = query.replace("moodle_%%%%", "moodle_" + empresa.getNomeAmbiente());
                this.jdbcTemplate.execute(query);
            } catch (Exception e) {
                throw new Exception("Erro ao criar banco de dados para a empresa " + empresa.getIdempresa() + " " + e.getMessage());
            }
        }
        return this.criaUsuarioBanco.execute(empresa);
    }


}
