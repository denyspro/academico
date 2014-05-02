package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Resolucao;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o dom�nio de resolu��es.
 * 
 * @author Denys
 */
public class ResolucaoSB {

	private static final Logger log = Logger.getLogger(ResolucaoSB.class.getName());

	public static Resolucao getPorId(Integer id) {
		String jpql = "select distinct r from Resolucao r join fetch r.avaliacao a join fetch a.cursos c where r.id = :id";
		Resolucao resolucao = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Resolucao.class);
		query.setParameter("id", id);

		try {
			resolucao = (Resolucao) query.getResultList().get(0);
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resolucao;
//		return PersistenciaUtil.getEntityManager().find(Resolucao.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Resolucao> getResolvidas(Integer idUsuario) {
		String jpql = "select distinct r from Resolucao r join fetch r.avaliacao a join fetch a.cursos c where r.idUsuario = :idUsuario and dtFimResolucao is not null order by dtIniResolucao desc";
		List<Resolucao> resolucoes = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Resolucao.class);
		query.setParameter("idUsuario", idUsuario);

		try {
			resolucoes = (List<Resolucao>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resolucoes;
	}

	public static String salvar(Resolucao resolucao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(em.merge(resolucao));
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
