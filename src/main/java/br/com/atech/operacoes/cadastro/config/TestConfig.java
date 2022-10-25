package br.com.atech.operacoes.cadastro.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.atech.operacoes.cadastro.entity.User;
import br.com.atech.operacoes.cadastro.repository.UserRepository;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		
		User u1 = new User(null, "Maria Brown", "mariaBrow", "maria@gmail.com", "123456", false);
		User u2 = new User(null, "João Grey", "JoaoGr", "joao@gmail.com", "654321", false);
		userRepository.saveAll(Arrays.asList(u1, u2));

	}
	
}