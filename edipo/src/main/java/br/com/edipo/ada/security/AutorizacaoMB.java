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
 * <i>Backing bean</i> com escopo por sess�o que tem o papel de controlador da
 * autoriza��o. Usado pelas telas para exibir informa��es do usu�rio ap�s a
 * autentica��o e para intermediar seu acesso � op��es espec�ficas.
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
		log.info("Iniciando sess�o...");
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

	/***
	 * M�todo que consulta se um usu�rio possui um certo perfil. Implementado de
	 * forma a evitar uma consulta em banco ao perfil de usu�rio a cada
	 * requisi��o de p�gina. Isto pode acontecer na montagem do menu porque o
	 * m�todo que verifica se um usu�rio do Shiro � est�tico. A lista
	 * pertencente � esta classe com escopo de sess�o serve como um <i>cache</i>
	 * de permiss�es. Sua �nica desvantagem � que o usu�rio precisa se
	 * autenticar novamente para atualizar a matriz de permiss�es.
	 * 
	 * @param dsPerfil
	 *            Perfil de usu�rio a ser consultado.
	 * @return verdadeiro ou falso
	 */
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
