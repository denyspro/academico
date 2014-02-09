package br.com.edipo.ada.domain;

import br.com.edipo.ada.model.entity.Usuario;
import br.com.edipo.ada.model.persistence.UsuarioDAO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import java.util.List;

@ManagedBean
@ViewScoped
public class UsuarioBean {
	@Inject
	private UsuarioDAO dao;

	private List<Usuario> usuarios;

	public List<Usuario> listaUsuario() {
		if (usuarios == null) {
			usuarios = dao.lista();
		}
		return usuarios;
	}
}
