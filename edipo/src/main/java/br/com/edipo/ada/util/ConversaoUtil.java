package br.com.edipo.ada.util;

import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("conversaoUtil")
public class ConversaoUtil implements Converter {

	private static final Logger log = Logger.getLogger(ConversaoUtil.class.getName());

	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		if (object == null)
			return null;

		try {
			Class<?> classe = object.getClass();
			Integer id = (Integer) classe.getMethod("getId").invoke(object);

			return classe.getName() + "-" + id;
		} catch (Exception e) {
			log.severe("getAsString: " + e.toString());
			return null;
		}
	}

	public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
		if (string == null || string.isEmpty())
			return null;

		try {
			String[] values = string.split("-");
			return PersistenciaUtil.getEntityManager().find(Class.forName(values[0]), Integer.valueOf(values[1]));
		} catch (Exception e) {
			log.severe("getAsObject: " + e.toString());
			return null;
		}
	}
}
