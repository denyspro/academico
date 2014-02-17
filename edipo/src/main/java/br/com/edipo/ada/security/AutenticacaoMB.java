package br.com.edipo.ada.security;

import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

@ManagedBean
@RequestScoped
public class AutenticacaoMB {

	private static final Logger log = Logger.getLogger(AutenticacaoMB.class
			.getName());

	String dsIdentificador;
	String dsSenha;
	boolean blManterAutenticado = false;

	public String entrar() {
		UsernamePasswordToken ficha = new UsernamePasswordToken(
				dsIdentificador, dsSenha);

		ficha.setRememberMe(blManterAutenticado);

		Subject usuarioAtual = SecurityUtils.getSubject();

		log.info("Tentativa de acesso com o identificador [" + dsIdentificador
				+ "] e a senha [" + dsSenha + "]...");

		try {
			usuarioAtual.login(ficha);
		} catch (AuthenticationException e) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(
									"Seu email e senha não correspondem, ou você ainda não possui uma conta."));

			log.info("Acesso negado!");
			log.warning(e.getMessage());
			return "login";
		}

		log.info("Acesso concedido!");
		return "index?faces-redirect=true";
	}

	public String sair() {
		Subject usuarioAtual = SecurityUtils.getSubject();

		try {
			usuarioAtual.logout();
		} catch (Exception e) {
			log.warning(e.toString());
		}

		return "login?faces-redirect=true";
	}

	public String getDsIdentificador() {
		return dsIdentificador;
	}

	public void setDsIdentificador(String dsIdentificador) {
		this.dsIdentificador = dsIdentificador;
	}

	public String getDsSenha() {
		return dsSenha;
	}

	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}

	public boolean isBlManterAutenticado() {
		return blManterAutenticado;
	}

	public void setBlManterAutenticado(boolean blManterAutenticado) {
		this.blManterAutenticado = blManterAutenticado;
	}
}