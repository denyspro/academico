package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Curso;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom’nio de cursos.
 * 
 * @author Denys
 */
@Stateless
public class CursoSB {

	private static final Logger log = Logger.getLogger(UsuarioSB.class.getName());

	public static List<Curso> getByUser(Integer id) {
		String jpql = "select c from Curso c where c.idUsuario = :idUsuario";
		List<Curso> cursos = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Curso.class);
		query.setParameter("idUsuario", id);

		try {
			cursos = (List<Curso>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return cursos;
	}

	public static String salvar(Curso curso) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(curso);
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			resposta = e.getMessage();

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resposta;
	}

	public static String excluir(Curso curso) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(curso);
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			resposta = e.getMessage();

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resposta;
	}
}
