package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Alternativa;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom’nio de alternativas.
 * 
 * @author Denys
 */
public class AlternativaSB {

	private static final Logger log = Logger.getLogger(AlternativaSB.class.getName());

	public static Alternativa getPorId(Integer id) {
		return PersistenciaUtil.getEntityManager().find(Alternativa.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Alternativa> getPorIdQuestao(Integer idQuestao) {
		String jpql = "select a from Alternativa a where a.questao.id = :idQuestao";
		List<Alternativa> alternativas = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Alternativa.class);
		query.setParameter("idQuestao", idQuestao);

		try {
			alternativas = (List<Alternativa>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return alternativas;
	}

	public static String salvar(Alternativa alternativa) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(em.merge(alternativa));
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

	public static String excluir(Alternativa alternativa) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.getReference(Alternativa.class, alternativa.getId()));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			if (PersistenciaUtil.possuiNaExcecao(e, "ConstraintViolationException")) {
				resposta = "N‹o Ž poss’vel excluir esta alternativa porque j‡ se encontra em uso.";
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
