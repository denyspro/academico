package br.com.edipo.ada.security;

import javax.servlet.ServletRequest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * Sobrescreve o método do filtro para retornar a mensagem ao invés do nome da
 * classe da exceção.
 */
public class FiltroAutenticacao extends FormAuthenticationFilter {

	@Override
	protected void setFailureAttribute(ServletRequest request,
			AuthenticationException ae) {
		String message = ae.getMessage();
		request.setAttribute(getFailureKeyAttribute(), message);
	}
}