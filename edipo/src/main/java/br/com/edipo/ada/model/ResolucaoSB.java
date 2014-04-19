package br.com.edipo.ada.model;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.edipo.ada.entity.Resolucao;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o domínio de resoluções.
 * 
 * @author Denys
 */
public class ResolucaoSB {

	private static final Logger log = Logger.getLogger(ResolucaoSB.class.getName());

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
