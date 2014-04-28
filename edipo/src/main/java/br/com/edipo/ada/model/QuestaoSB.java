package br.com.edipo.ada.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Questao;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom’nio de quest›es.
 * 
 * @author Denys
 */
@Stateless
public class QuestaoSB {

	private static final Logger log = Logger.getLogger(QuestaoSB.class.getName());

	public static Questao getPorId(Integer id) {
		return PersistenciaUtil.getEntityManager().find(Questao.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Questao> getPorIdUsuario(Integer idUsuario) {
		String jpql = "select q from Questao q where q.idUsuario = :idUsuario";
		List<Questao> questoes = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Questao.class);
		query.setParameter("idUsuario", idUsuario);

		try {
			questoes = (List<Questao>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return questoes;
	}

	public static Integer getNrAlternativas(Integer id) {
		Integer nrAlternativas = 0;

		String jpql = "select size(q.alternativas) from Questao q where q.id = :idQuestao";

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("idQuestao", id);

		try {
			nrAlternativas = (Integer) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return nrAlternativas;
	}

	public static BigDecimal getVlSomaAlternativas(Questao questao) {
		BigDecimal vlSomaAlternativas = BigDecimal.ZERO;

		String jpql = "select sum(a.vlAlternativa) from Alternativa a where a.questao = :questao";

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("questao", questao);

		try {
			vlSomaAlternativas = (BigDecimal) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return vlSomaAlternativas;
	}

	public static String salvar(Questao questao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(em.merge(questao));
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

	public static String excluir(Questao questao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.getReference(Questao.class, questao.getId()));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			if (PersistenciaUtil.possuiNaExcecao(e, "ConstraintViolationException")) {
				resposta = "N‹o Ž poss’vel excluir esta quest‹o porque j‡ se encontra em uso.";
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
