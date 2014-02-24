package br.com.edipo.ada.security;

import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import br.com.edipo.ada.model.UsuarioSB;
import br.com.edipo.ada.util.ViewUtil;

/***
 * <i>Backing bean</i> que faz o papel de controlador para o domínio de autotização.
 * 
 * @author Denys
 */
@ManagedBean
@RequestScoped
public class AutenticacaoMB {

	private static final Logger log = Logger.getLogger(AutenticacaoMB.class
			.getName());

	String dsIdentificador;
	String dsSenha;
	Integer idUsuario;
	boolean blManterAutenticado = false;

	public String entrar() {
		UsernamePasswordToken ficha = new UsernamePasswordToken(
				dsIdentificador, dsSenha);

		ficha.setRememberMe(blManterAutenticado);

		Subject usuarioAtual = SecurityUtils.getSubject();

		log.info(String
				.format("Tentativa de acesso com o identificador [%s] e a senha [%s] ...",
						dsIdentificador, dsSenha));

		try {
			usuarioAtual.login(ficha);
		} catch (AuthenticationException e) {
			ViewUtil.setMessage("Seu email e senha não correspondem, ou você ainda não possui uma conta.");

			log.info("Acesso negado!");
			log.warning(e.getMessage());
			return "login";
		}
		log.info("Acesso concedido!");

		Session session = usuarioAtual.getSession(true);
		session.setAttribute("idUsuario",
				UsuarioSB.getBySurrogate(dsIdentificador));

		return "index?faces-redirect=true";
	}

	public String sair() {
		Subject usuarioAtual = SecurityUtils.getSubject();

		if (usuarioAtual.isAuthenticated()) {
			try {
				usuarioAtual.logout();
				log.info("Saiu do sistema.");
			} catch (Exception e) {
				log.warning(e.toString());
			}
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

	public Integer getIdUsuario() {
		try {
			idUsuario = (Integer) SecurityUtils.getSubject().getSession()
					.getAttribute("idUsuario");
		} catch (Exception e) {
			log.severe(e.toString());
		}

		return idUsuario;
	}
}