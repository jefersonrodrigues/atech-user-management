package br.com.atech.operacoes.cadastro.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageExceptions {

	public static final String ERROR_TO_GET_USER_BY_ID = "Falha ao buscar usuário - Usuário = %s!!!";
	public static final String USUARIO_IS_OBLIGATED = "É obrigatório informar os dados do usuário - Usuário = %s!!!";
	public static final String ERROR_TO_SAVE_USER = "Falha ao salvar usuário - Usuário = %s!!!";


}
