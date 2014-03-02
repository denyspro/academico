package br.com.edipo.ada.security;

import java.util.Date;

import javax.ejb.Stateless;

import org.apache.shiro.SecurityUtils;

/***
 * <i>Stateless bean</i> que intermedia o acesso ˆ sess‹o do usu‡rio.
 * 
 * @author Denys
 */
@Stateless
public class AutorizacaoSB {

	public static String getAtributo(String chave) {
		return SecurityUtils.getSubject().getSession().getAttribute(chave).toString();
	}

	public static void setAtributo(String chave, String valor) {
		SecurityUtils.getSubject().getSession(true).setAttribute(chave, valor);
	}

	public static boolean getUsuarioAutenticado() {
		return SecurityUtils.getSubject().isAuthenticated();
	}

	public static Date getUltimoAcesso() {
		Date ultimoAcesso = SecurityUtils.getSubject().getSession().getLastAccessTime();
		return ultimoAcesso;
	}
}