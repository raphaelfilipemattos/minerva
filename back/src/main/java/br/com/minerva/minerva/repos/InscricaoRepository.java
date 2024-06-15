package br.com.minerva.minerva.repos;

import br.com.minerva.minerva.domain.Inscricao;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InscricaoRepository extends JpaRepository<Inscricao, UUID> {


}
