package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Etiqueta;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom’nio de etiquetas.
 * 
 * @author Denys
 */
@Stateless
public class EtiquetaSB {

	private static final Logger log = Logger.getLogger(EtiquetaSB.class.getName());

	public static Etiqueta getById(Integer id) {
		return PersistenciaUtil.getEntityManager().find(Etiqueta.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Etiqueta> getPorIdUsuario(Integer idUsuario) {
		String jpql = "select e from Etiqueta e inner join QuestaoEtiqueta qe on qe.idEtiqueta = e.idEtiqueta"
				+ " inner join Questao q on q.idQuestao = qe.idQuestao where q.idUsuario = :idUsuario";
		List<Etiqueta> etiquetas = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Etiqueta.class);
		query.setParameter("idUsuario", idUsuario);

		try {
			etiquetas = (List<Etiqueta>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return etiquetas;
	}

	public static String salvar(Etiqueta etiqueta) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(em.merge(etiqueta));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			if (tx != null && tx.isActive()) {
				tx.rollback();
			}
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resposta;
	}

	public static String excluir(Etiqueta etiqueta) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.getReference(Etiqueta.class, etiqueta.getId()));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			if (PersistenciaUtil.possuiNaExcecao(e, "ConstraintViolationException")) {
				resposta = "N‹o Ž poss’vel excluir esta etiqueta porque j‡ se encontra em uso.";
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
