package com.example.produtosapi;

import com.example.produtosapi.model.Usuario;
import com.example.produtosapi.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProdutosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdutosApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner inicializarDados(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Criar um usuário administrador inicial se não existir
			if (!usuarioRepository.existsByUsername("admin")) {
				Usuario admin = new Usuario();
				admin.setUsername("admin");
				admin.setPassword(passwordEncoder.encode("admin123"));
				admin.setNome("Administrador");
				admin.setRole("ADMIN");
				usuarioRepository.save(admin);
				System.out.println("Usuário administrador criado com sucesso!");
			}
		};
	}
}