package br.com.atech.operacoes.cadastro.exception;


import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }
}