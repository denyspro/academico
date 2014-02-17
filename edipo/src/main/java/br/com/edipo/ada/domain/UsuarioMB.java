package br.com.edipo.ada.domain;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import br.com.edipo.ada.entity.Usuario;
import br.com.edipo.ada.persistence.JpaUtil;

@ViewScoped
@ManagedBean
public class UsuarioMB {

	private static final Logger log = Logger.getLogger(UsuarioMB.class
			.getName());

	private Usuario usuario;
	private List<Usuario> usuarios;

	@PostConstruct
	public void init() {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();

		if (!params.isEmpty()) {
			try {
				usuario = JpaUtil.getEntityManager().find(Usuario.class,
						Integer.parseInt(params.get("idUsuario")));
			} catch (NumberFormatException e) {
			}
		}

		if (usuario == null) {
			usuario = new Usuario();
		}
	}

	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = JpaUtil.getEntityManager()
					.createQuery("select u from Usuario u", Usuario.class)
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

	public String salvar(Usuario u) {

		EntityManager em = JpaUtil.getEntityManager();

		if (em.getTransaction().isActive()
				&& em.getTransaction().getRollbackOnly()) {
			em.getTransaction().rollback();
		}

		try {
			em.getTransaction().begin();
			em.persist(u);
			em.getTransaction().commit();
		} catch (Exception e) {
			log.severe(e.toString());
			return "";
		}

		return "listar?faces-redirect=true";
	}
}