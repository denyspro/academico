package br.com.edipo.ada.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/***
 * Classe dedicada � valida��o de campos com formato de email nas vis�es.
 * 
 * @author Denys
 */
@FacesValidator(value = "validadorEmail")
public class ValidadorEmail implements Validator {

	/***
	 * Express�o regular refer�ncia para a valida��o.
	 */
	private static final String EXP_EMAIL = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";

	private Pattern padrao;
	private Matcher combinador;

	public ValidadorEmail() {
		/***
		 * Compila a express�o regular refer�ncia.
		 */
		padrao = Pattern.compile(EXP_EMAIL);
	}

	public void validate(FacesContext contexto, UIComponent componente, Object valor)
			throws ValidatorException {

		/***
		 * Aplica a express�o regular no valor do campo.
		 */
		combinador = padrao.matcher(valor.toString());

		/***
		 * Se a express�o regular n�o corresponder ao valor do campo, dispara uma exce��o de volta para a vis�o.
		 */
		if(!combinador.matches()) {
			VisaoUtil.setMessage("Formato de email inv�lido.", componente);
		}
	}
}
