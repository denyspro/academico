package br.com.edipo.ada.model;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Avaliacao;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o domínio de avaliações.
 * 
 * @author Denys
 */
public class AvaliacaoSB {

	private static final Logger log = Logger.getLogger(AvaliacaoSB.class.getName());

	public static Avaliacao getPorId(Integer id) {
		String jpql = "select distinct a from Avaliacao a join fetch a.cursos c where a.id = :id";
		Avaliacao avaliacao = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Avaliacao.class);
		query.setParameter("id", id);

		try {
			avaliacao = (Avaliacao) query.getResultList().get(0);
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return avaliacao;
//		return PersistenciaUtil.getEntityManager().find(Avaliacao.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Avaliacao> getPorIdUsuario(Integer idUsuario) {
		String jpql = "select distinct a from Avaliacao a join fetch a.cursos c where a.idUsuario = :idUsuario order by a.dtIniAvaliacao desc";
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

	@SuppressWarnings("unchecked")
	public static List<Avaliacao> getIniciadas(Integer idUsuario) {
		String jpql = "select distinct a from Avaliacao a join fetch a.cursos c where a.idUsuario = :idUsuario and a.dtIniAvaliacao <= :dtIniAvaliacao order by a.dtIniAvaliacao desc";
		List<Avaliacao> avaliacao = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Avaliacao.class);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("dtIniAvaliacao", new Date());

		try {
			avaliacao = (List<Avaliacao>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return avaliacao;
	}

	@SuppressWarnings("unchecked")
	public static List<Avaliacao> getAbertasPorInscrito(Integer idUsuario) {
		String jpql = "select distinct a from Avaliacao a join fetch a.cursos c join c.inscritos i where i.idUsuario = :idUsuario and :dtIniResolucao between a.dtIniAvaliacao and dtFimAvaliacao and not exists (select r from Resolucao r where r.idUsuario = :idUsuario and r.avaliacao = a)";
		List<Avaliacao> avaliacoes = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Avaliacao.class);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("dtIniResolucao", new Date());

		try {
			avaliacoes = (List<Avaliacao>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return avaliacoes;
	}

	public static Integer getNrQuestoes(Integer id) {
		Integer nrQuestoes = 0;

		String jpql = "select count(*) from AvaliacaoQuestao a where a.id.idAvaliacao = :idAvaliacao";

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("idAvaliacao", id);

		try {
			nrQuestoes = ((Long) query.getSingleResult()).intValue();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return nrQuestoes;
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
				resposta = "Não é possível excluir esta avaliação porque já se encontra em uso.";
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
