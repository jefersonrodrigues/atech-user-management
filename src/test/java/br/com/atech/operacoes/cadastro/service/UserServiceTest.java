package br.com.atech.operacoes.cadastro.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import br.com.atech.operacoes.cadastro.converter.UserConverter;
import br.com.atech.operacoes.cadastro.dto.UserInfoDTO;
import br.com.atech.operacoes.cadastro.entity.User;
import br.com.atech.operacoes.cadastro.exception.ResourceNotFoundException;
import br.com.atech.operacoes.cadastro.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceTest {

	private static final String PASSWORD = "123456";
	private static final String EMAIL = "maria@gmail.com";
	private static final String USERNAME = "mariaBrow";
	private static final String NAME = "Maria Brown";
	private static final long ID = 1L;

	@InjectMocks
	UserService service;

	@Mock
	UserRepository repository;

	@Mock
	UserConverter converter;

	private User user;
	private UserInfoDTO info;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		startUser();
	}

	@Test
	void canRemoveUser() {
		when(repository.findById(anyLong())).thenReturn(Optional.of(user));
		doNothing().when(repository).deleteById(anyLong());
		service.remove(ID);
		verify(repository, times(1)).deleteById(anyLong());
	}

	@Test
	void cannotRemoveUser() {
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			throw new ResourceNotFoundException(ID);
		});

		when(repository.findById(anyLong())).thenThrow(exception);

		try {
			service.remove(ID);
		} catch (ResourceNotFoundException e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
			assertEquals(String.format("Resource not found. Id " + ID), e.getMessage());
		}
	}

	@Test
	void canUpdateUser() {
		when(repository.findById(anyLong())).thenReturn(Optional.of(user));
		when(repository.save(any())).thenReturn(user).thenReturn(converter.toUserDTO(user));
	}
	
	@Test
	void cannotUpdateUser() {
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			throw new ResourceNotFoundException(ID);
		});
		when(repository.findById(anyLong())).thenThrow(exception);
		try {
			Optional.of(user).get();
			service.update(info, -1L);
		} catch (Exception e) {
			assertEquals(ResourceNotFoundException.class, e.getClass());
		}
	}
	
	@Test
	void canInsertUser() {
		when(repository.save(user)).thenReturn(user);
		service.insert(info);
		
		assertNotNull(info);
		assertEquals(NAME, info.getName());
		assertEquals(EMAIL, info.getEmail());
		assertEquals(USERNAME, info.getUsername());
		assertEquals(PASSWORD, info.getPassword());
		verify(repository, times(1)).save(any());
	}
	
	private void startUser() {
		user = new User(ID, NAME, USERNAME, EMAIL, PASSWORD, false);
		info = new UserInfoDTO(NAME, USERNAME, EMAIL, PASSWORD);
	}
}
