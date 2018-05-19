package me.kadarh.mecaworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MecaworksApplication {

    public static void main(String[] args) {
        SpringApplication.run(MecaworksApplication.class, args);
    }
}
