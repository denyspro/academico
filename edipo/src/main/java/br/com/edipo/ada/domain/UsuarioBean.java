package br.com.edipo.ada.domain;

import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;

import br.com.edipo.ada.model.entity.Usuario;

@ViewScoped
@ManagedBean
public class UsuarioBean {

	private List<Usuario> usuarios;

	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = Usuario.findAll();
		}
		return usuarios;
	}
}
