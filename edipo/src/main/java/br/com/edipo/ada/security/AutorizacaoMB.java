package br.com.edipo.ada.security;

import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/***
 * <i>Backing bean</i> com escopo por sessão que tem o papel de controlador da
 * autorização. Usado pelas telas para exibir informações do usuário após a
 * autenticação e para intermediar seu acesso à opções específicas.
 * 
 * @author Denys
 */
@ManagedBean
@SessionScoped
public class AutorizacaoMB {
	private static final Logger log = Logger.getLogger(AutenticacaoMB.class.getName());

	@PreDestroy
	public void release() {
		log.info("Liberando recursos...");
	}

	public String getIdUsuario() {
		String idUsuario = null;

		try {
			idUsuario = AutorizacaoSB.getAtributo("idUsuario");
		} catch (Exception e) {
			log.severe(e.toString());
		}

		return idUsuario;
	}

	public String getDsNome() {
		String dsNome = null;

		try {
			dsNome = AutorizacaoSB.getAtributo("dsNome");
		} catch (Exception e) {
			log.severe(e.toString());
		}

		return dsNome;
	}

	public boolean getUsuarioAutenticado() {
		return AutorizacaoSB.getUsuarioAutenticado();
	}

	public String getUltimoAcesso() {
		String ultimoAcesso = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(AutorizacaoSB.getUltimoAcesso());

		return ultimoAcesso;
	}
}
