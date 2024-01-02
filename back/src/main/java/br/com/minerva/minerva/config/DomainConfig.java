package br.com.minerva.minerva.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("br.com.minerva.minerva.domain")
@EnableJpaRepositories("br.com.minerva.minerva.repos")
@EnableTransactionManagement
public class DomainConfig {
}
