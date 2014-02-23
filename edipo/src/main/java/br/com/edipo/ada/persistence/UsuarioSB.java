package br.com.edipo.ada.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.domain.UsuarioMB;
import br.com.edipo.ada.entity.Usuario;
import br.com.edipo.ada.util.ModelUtil;

@Stateless
public class UsuarioSB {

	private static final Logger log = Logger.getLogger(UsuarioMB.class
			.getName());

	public static List<Usuario> getAll() {
		String jpql = "select u from Usuario u";

		return ModelUtil.getEntityManager().createQuery(jpql, Usuario.class)
				.getResultList();
	}

	public static Usuario getById(Integer id) {
		return ModelUtil.getEntityManager().find(Usuario.class, id);
	}

	public static Integer getBySurrogate(String id) {

		String jpql = "select u from Usuario u where u.dsIdentificador = :dsIdentificador";
		Integer idUsuario = null;

		Query query = ModelUtil.getEntityManager().createQuery(jpql,
				Usuario.class);
		query.setParameter("dsIdentificador", id);

		try {
			Usuario u = (Usuario) query.getSingleResult();
			idUsuario = u.getIdUsuario();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			ModelUtil.closeEntityManager();
		}

		return idUsuario;
	}

	public static boolean save(Usuario u) {

		boolean r = false;

		EntityManager em = ModelUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(u);
			tx.commit();

			r = true;
		} catch (Exception e) {
			log.severe(e.toString());

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			ModelUtil.closeEntityManager();
		}

		return r;
	}

	public static boolean delete(Usuario u) {

		boolean r = false;

		EntityManager em = ModelUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(u);
			tx.commit();

			r = true;
		} catch (Exception e) {
			log.severe(e.toString());

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			ModelUtil.closeEntityManager();
		}

		return r;
	}
}