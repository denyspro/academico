package br.com.edipo.ada.security;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
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
	private static final Logger log = Logger.getLogger(AutorizacaoMB.class.getName());

	String id;
	String dsNome;
	List<String> possuiPerfil;

	@PostConstruct
	public void init() {
		log.info("Iniciando sessão...");
		possuiPerfil = new ArrayList<String>();
	}

	@PreDestroy
	public void release() {
		log.info("Liberando recursos...");
	}

	public String getId() {
		if (id == null) {
			try {
				id = AutorizacaoSB.getAtributo("id");
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		return id;
	}

	public String getDsNome() {
		if (dsNome == null) {
			try {
				dsNome = AutorizacaoSB.getAtributo("dsNome");
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		return dsNome;
	}

	public boolean getUsuarioAutenticado() {
		return AutorizacaoSB.getUsuarioAutenticado();
	}

	public boolean getPossuiPerfil(String dsPerfil) {
		if (possuiPerfil.contains(dsPerfil)) {
			return true;
		} else if (AutorizacaoSB.getPossuiPerfil(dsPerfil)) {
			possuiPerfil.add(dsPerfil);
			return true;
		} else {
			return false;
		}
	}

	public String getUltimoAcesso() {
		String ultimoAcesso = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(AutorizacaoSB.getUltimoAcesso());

		return ultimoAcesso;
	}
}
