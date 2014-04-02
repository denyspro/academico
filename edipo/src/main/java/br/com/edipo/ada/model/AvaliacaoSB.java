package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Avaliacao;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom�nio de avalia��es.
 * 
 * @author Denys
 */
public class AvaliacaoSB {

	private static final Logger log = Logger.getLogger(AvaliacaoSB.class.getName());

	public static Avaliacao getPorId(Integer id) {
		return PersistenciaUtil.getEntityManager().find(Avaliacao.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Avaliacao> getPorIdUsuario(Integer idUsuario) {
		String jpql = "select a from Avaliacao a where a.idUsuario = :idUsuario";
		List<Avaliacao> avaliacao = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Avaliacao.class);
		query.setParameter("idUsuario", idUsuario);

		try {
			avaliacao = (List<Avaliacao>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return avaliacao;
	}

	public static String salvar(Avaliacao avaliacao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(em.merge(avaliacao));
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

	public static String excluir(Avaliacao avaliacao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.getReference(Avaliacao.class, avaliacao.getId()));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			if (PersistenciaUtil.possuiNaExcecao(e, "ConstraintViolationException")) {
				resposta = "N�o � poss�vel excluir esta avalia��o porque j� se encontra em uso.";
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