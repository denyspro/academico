package br.com.edipo.ada.security;

import javax.servlet.ServletRequest;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * Sobrescreve o m�todo do filtro para retornar a mensagem ao inv�s do nome da
 * classe da exce��o.
 */
public class FiltroAutenticacao extends FormAuthenticationFilter {

	@Override
	protected void setFailureAttribute(ServletRequest request,
			AuthenticationException ae) {
		String message = ae.getMessage();
		request.setAttribute(getFailureKeyAttribute(), message);
	}
}