package org.gestionstock.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Permet d'activer l'auditing JPA
public class StockGestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockGestionApplication.class, args);
	}

}
