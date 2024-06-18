package com.alura.literalura;


import com.alura.literalura.principal.Principal;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired// se indica una inyeccion de dependencia a spring
	private LibroRepository repository; //se pueden agregar los metodos a la clase principal
	public static void main(String[] args) {SpringApplication.run(LiteraluraApplication.class, args);}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository);
		principal.muestraElMenu();

	}

}
