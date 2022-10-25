package br.com.atech.operacoes.cadastro.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.atech.operacoes.cadastro.dto.UserDTO;
import br.com.atech.operacoes.cadastro.dto.UserInfoDTO;
import br.com.atech.operacoes.cadastro.dto.UserResponseDTO;
import br.com.atech.operacoes.cadastro.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@RestController
@RequestMapping(value = "/users")
@Api(value = "UserAPI", description = "API Rest para gerenciamento e visualização do cadastro de usuários")
public class UserController {
	
    Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService service;
	
	@ApiOperation(value = "Obtém um usuário informando seu id", notes = "Retorna um usuário dado seu Id", response = UserResponseDTO.class)
	@ApiResponses(value = {
	  @ApiResponse(code = 200, message = "Usuário recuperado com sucesso"),
	  @ApiResponse(code = 404, message = "Usuário não Encontrado")
	})
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserResponseDTO> getUsersList(
			@PathVariable(value = "id")  
			@ApiParam(name = "id", value = "id", example = "1") Long id) {
		try {
			return ResponseEntity.ok().body(service.findById(id));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Realiza o cadastro de um usuário", notes = "Realiza o cadastro de um usuário")
	@ApiResponses(value = {
	  @ApiResponse(code = 200, message = "Usuário cadastrado com sucesso"),
	  @ApiResponse(code = 400, message = "Não foi possivel realizar o cadastro do usuário")
	})
	@RequestMapping(path = "/cadastro", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> userInsert(@RequestBody UserInfoDTO user) {
		try {
			UserDTO userCreated = service.insert(user);
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(userCreated.getId()).toUri();
			return ResponseEntity.created(uri).body(userCreated);
		} catch (Exception e) {
			logger.info("Não foi possível salvar o usuário porque o {}", e.getMessage());
			return ResponseEntity.badRequest().body(null);
		}
	}
	
	@ApiOperation(value = "Realiza a alteração dos dados de um usuário", notes = "Realiza a edição de um usuário")
	@ApiResponses(value = {
	  @ApiResponse(code = 200, message = "Dados alterados com sucesso"),
	  @ApiResponse(code = 400, message = "Não foi possivel realizar a alteração")
	})
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserInfoDTO> userUpdate(@PathVariable Long id, @RequestBody UserInfoDTO user) {
		try {
			service.update(user, id);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
			
	}
	
	@ApiOperation(value = "Realiza a remoção dos dados de um usuário", notes = "Realiza a remoção de um usuário")
	@ApiResponses(value = {
	  @ApiResponse(code = 200, message = "Dados removidos com sucesso"),
	  @ApiResponse(code = 400, message = "Não foi possivel realizar a remoção")
	})
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<HttpStatus> userDelete(@PathVariable Long id){
		try {
			service.remove(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
		
	}


}
