package inge2.com.alquileres.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication @EnableScheduling
public class AlquileresBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlquileresBackendApplication.class, args);
	}

}
