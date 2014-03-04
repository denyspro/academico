package br.com.edipo.ada.util;

import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import br.com.edipo.ada.controller.UsuarioMB;

/**
 * Agrupa os m�todos de manipula��o de view para efeito de reaproveitamento de
 * c�digo.
 * 
 * @author Denys
 */
public class VisaoUtil {

	private static final Logger log = Logger.getLogger(UsuarioMB.class.getName());

	public static final String VISAOORIGEM = "lista?faces-redirect=true";

	/**
	 * Retorna o valor de um par�metro passado via m�todo <i>GET</i>.
	 * 
	 * @param f
	 *            Nome do par�metro.
	 * 
	 * @return Valor do par�metro.
	 */
	public static String getViewParam(String f) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(f);
	}

	/**
	 * Retorna mensagem na <i>tag <u>h:message</u></i> de um campo espec�fico da
	 * <i>view</i>.
	 * 
	 * @param m
	 *            Mensagem a ser exibida.
	 * @param f
	 *            Nome do campo na view.
	 * 
	 * @see #setMessage(String)
	 */
	public static void setMessage(String mensagem, UIComponent componente) {
		log.info(String.format("Mensagem p/ %s: %s", componente.getId(), mensagem));
		FacesContext.getCurrentInstance().addMessage(componente.getClientId(), new FacesMessage(mensagem));
	}

	/**
	 * Retorna mensagem na <i>tag <u>h:messages</u></i> global da <i>view</i>.
	 * 
	 * @param m
	 *            Mensagem a ser exibida.
	 * 
	 * @see #setMessage(String, String)
	 */
	public static void setMessage(String mensagem) {
		log.info(String.format("Mensagem: %s", mensagem));
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensagem));
	}
}
