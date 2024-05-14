package com.aluracursos.gutendex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.gutendex.principal.Principal;

@SpringBootApplication
public class GutendexApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GutendexApplication.class, args);
	}
	public void run(String... args) throws Exception{
		Principal principal = new Principal();
		principal.muestraElMenu();
	}

}
