package br.com.edipo.ada.domain;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.edipo.ada.model.entity.Usuario;

@ViewScoped
@ManagedBean
public class UsuarioBean {

	@PersistenceContext
	private EntityManager em;

	private Usuario usuario;
	private List<Usuario> usuarios;

	@PostConstruct
	public void init() {
		usuario = new Usuario();
	}

	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = em.createQuery("select u from Usuario u", Usuario.class)
					.getResultList();
		}
		return usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String editar(Usuario u) {
		this.setUsuario(usuario);
		return "editar";
	}

	public String salvar(Usuario u) {
		em.persist(usuario);
		return "listar";
	}
}