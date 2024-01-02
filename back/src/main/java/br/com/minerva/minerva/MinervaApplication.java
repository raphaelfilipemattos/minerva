package br.com.minerva.minerva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MinervaApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MinervaApplication.class, args);
    }

}
