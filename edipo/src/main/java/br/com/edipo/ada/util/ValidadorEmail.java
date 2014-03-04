package br.com.edipo.ada.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/***
 * Classe dedicada à validação de campos com formato de email nas visões.
 * 
 * @author Denys
 */
@FacesValidator(value = "validadorEmail")
public class ValidadorEmail implements Validator {

	/***
	 * Expressão regular referência para a validação.
	 */
	private static final String EXP_EMAIL = "^[_A-Za-z0-9-]+(\\." +
			"[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*" +
			"(\\.[A-Za-z]{2,})$";

	private Pattern padrao;
	private Matcher combinador;

	public ValidadorEmail() {
		/***
		 * Compila a expressão regular referência.
		 */
		padrao = Pattern.compile(EXP_EMAIL);
	}

	public void validate(FacesContext contexto, UIComponent componente, Object valor)
			throws ValidatorException {

		/***
		 * Aplica a expressão regular no valor do campo.
		 */
		combinador = padrao.matcher(valor.toString());

		/***
		 * Se a expressão regular não corresponder ao valor do campo, dispara uma exceção de volta para a visão.
		 */
		if(!combinador.matches()) {
			VisaoUtil.setMessage("Formato de email inválido.", componente);
		}
	}
}
