package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Resolucao;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o domínio de resoluções.
 * 
 * @author Denys
 */
public class ResolucaoSB {

	private static final Logger log = Logger.getLogger(ResolucaoSB.class.getName());

	@SuppressWarnings("unchecked")
	public static List<Resolucao> getResolvidas(Integer idUsuario) {
		String jpql = "select r from Resolucao r where r.idUsuario = :idUsuario and dtFimResolucao is not null order by dtIniResolucao desc";
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
