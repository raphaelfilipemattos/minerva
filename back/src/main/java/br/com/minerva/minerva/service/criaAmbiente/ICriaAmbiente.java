package br.com.minerva.minerva.service.criaAmbiente;

import br.com.minerva.minerva.domain.Empresa;

public interface ICriaAmbiente {
    ICriaAmbiente execute(Empresa empresa) throws Exception;

}
