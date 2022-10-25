package br.com.atech.operacoes.cadastro.service;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.atech.operacoes.cadastro.converter.UserConverter;
import br.com.atech.operacoes.cadastro.dto.UserDTO;
import br.com.atech.operacoes.cadastro.dto.UserInfoDTO;
import br.com.atech.operacoes.cadastro.dto.UserResponseDTO;
import br.com.atech.operacoes.cadastro.entity.User;
import br.com.atech.operacoes.cadastro.exception.DatabaseException;
import br.com.atech.operacoes.cadastro.exception.ResourceNotFoundException;
import br.com.atech.operacoes.cadastro.repository.UserRepository;

@Service
public class UserService {

	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private UserConverter converter;

	public UserResponseDTO findById(Long id) {
		return repository.findById(id).map(converter::toUserResponseDTO).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public UserDTO insert(final UserInfoDTO user) {
		if (validateUser(user)) {
			User saved = repository.save(converter.toUserEntity(user));
			return converter.toUserDTO(saved);
		} else {
			throw new DatabaseException("ERRO AO SALVAR USUÁRIO");
		}
	}

	public UserDTO update(final UserInfoDTO user, Long id) {
		try {
			User entity = repository.findById(id).orElseThrow();
			updateData(entity, user);
			return converter.toUserDTO(repository.save(entity));
		} catch (NoSuchElementException e) {
			logger.info("Não foi possível encontrar o usuário de id = {}", id);
			throw new ResourceNotFoundException(id);
		}
	}

	public void remove(Long id) {
		try {
			repository.findById(id);
			repository.deleteById(id);
		} catch (NoSuchElementException e) {
			logger.info("Não foi possível encontrar o usuário de id = {}", id);
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(User entity, UserInfoDTO user) {
		entity.setName(user.getName());
		entity.setUsername(user.getUsername());
		entity.setEmail(user.getEmail());
		entity.setPassword(user.getPassword());
	}

	private Boolean validateUser(UserInfoDTO user) {
		if (repository.findByEmail(user.getEmail()) != null) {
			throw new DatabaseException("email informado já existe na base de dados!");
		} else if (repository.findByUsername(user.getUsername()) != null) {
			throw new DatabaseException("nome de usuário informado já existe na base de dados!");
		} else {
			return true;
		}
	}
}
