package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.AvaliacaoQuestao;
import br.com.edipo.ada.entity.AvaliacaoQuestaoPK;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o domínio da entidade que associa questões às avaliações.
 * 
 * @author Denys
 */
public class AvaliacaoQuestaoSB {

	private static final Logger log = Logger.getLogger(AvaliacaoQuestaoSB.class.getName());

	public static AvaliacaoQuestao getPorId(AvaliacaoQuestaoPK id) {
		return PersistenciaUtil.getEntityManager().find(AvaliacaoQuestao.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<AvaliacaoQuestao> getPorIdAvaliacao(Integer idAvaliacao) {
		String jpql = "select a from AvaliacaoQuestao a where a.avaliacao.id = :idAvaliacao";
		List<AvaliacaoQuestao> avaliacaoQuestoes = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, AvaliacaoQuestao.class);
		query.setParameter("idAvaliacao", idAvaliacao);

		try {
			avaliacaoQuestoes = (List<AvaliacaoQuestao>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return avaliacaoQuestoes;
	}

	public static String salvar(AvaliacaoQuestao avaliacaoQuestao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(em.merge(avaliacaoQuestao));
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

	public static String excluir(AvaliacaoQuestao avaliacaoQuestao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.getReference(AvaliacaoQuestao.class, avaliacaoQuestao.getId()));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			if (PersistenciaUtil.possuiNaExcecao(e, "ConstraintViolationException")) {
				resposta = "Não é possível excluir esta questão para esta avaliação porque já se encontra em uso.";
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
