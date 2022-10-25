package br.com.atech.operacoes.cadastro.converter;

import org.springframework.stereotype.Component;

import br.com.atech.operacoes.cadastro.dto.UserDTO;
import br.com.atech.operacoes.cadastro.dto.UserInfoDTO;
import br.com.atech.operacoes.cadastro.dto.UserResponseDTO;
import br.com.atech.operacoes.cadastro.entity.User;

@Component
public class UserConverter {

	public User toUserEntity(final UserInfoDTO user) {
		return User.builder()
				.name(user.getName())
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.build();
	}
	
	public UserDTO toUserDTO(User user) {
		return UserDTO.builder()
				.id(user.getId())
				.name(user.getName())
				.username(user.getUsername())
				.email(user.getEmail())
				.password(user.getPassword())
				.build();
	}
	
	public UserResponseDTO toUserResponseDTO(User user) {
		return UserResponseDTO.builder()
				.name(user.getName())
				.username(user.getUsername())
				.email(user.getEmail())
				.build();
	}
}
