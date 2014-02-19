package br.com.edipo.ada.domain;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
			} catch (Exception e) {
				log.severe(e.toString());
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

	public String excluir(Usuario u) {

		String navRule = "";

		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(u);
			tx.commit();

			navRule = "listar?faces-redirect=true";
		} catch (Exception e) {
			log.severe(e.toString());

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			JpaUtil.closeEntityManager();
		}

		//devolver mensagem de exclusão
		return navRule;
	}

	public String salvar(Usuario u) {

		String navRule = "";

		EntityManager em = JpaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(u);
			tx.commit();

			navRule = "listar?faces-redirect=true";
		} catch (Exception e) {
			log.severe(e.toString());

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			JpaUtil.closeEntityManager();
		}

		//devolver mensagem de atualização
		return navRule;
	}
}