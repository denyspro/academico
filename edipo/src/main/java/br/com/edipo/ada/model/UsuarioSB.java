package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Usuario;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom’nio de usu‡rios.
 * 
 * @author Denys
 */
@Stateless
public class UsuarioSB {

	private static final Logger log = Logger.getLogger(UsuarioSB.class.getName());

	public static List<Usuario> getTodos() {
		String jpql = "select u from Usuario u";

		return PersistenciaUtil.getEntityManager().createQuery(jpql, Usuario.class).getResultList();
	}

	public static Usuario getPorId(Integer id) {
		return PersistenciaUtil.getEntityManager().find(Usuario.class, id);
	}

	public static Usuario getPorDsIdentificador(String id) {

		String jpql = "select u from Usuario u where u.dsIdentificador = :dsIdentificador";
		Usuario u = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Usuario.class);
		query.setParameter("dsIdentificador", id);

		try {
			u = (Usuario) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return u;
	}

	public static String salvar(Usuario usuario) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(em.merge(usuario));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			resposta = e.getMessage();

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resposta;
	}

	public static String excluir(Usuario usuario) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(usuario);
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			if (PersistenciaUtil.possuiNaExcecao(e, "ConstraintViolationException")) {
				resposta = "N‹o Ž poss’vel excluir este usu‡rio porque j‡ se encontra em uso.";
			} else {
				resposta = e.getMessage();
			}

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resposta;
	}
}