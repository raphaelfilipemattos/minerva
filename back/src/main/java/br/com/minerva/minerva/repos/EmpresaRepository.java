package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Empresa;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {


    List<Empresa> findAllByAmbientecriadoIsFalse();

    Empresa findByDominioava(String dominioava);
    Empresa findByDominio(String dominio);

    boolean existsByCnpjCpfIgnoreCase(String cnpjCpf);


}
