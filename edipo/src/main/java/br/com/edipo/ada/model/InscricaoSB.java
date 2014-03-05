package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Inscricao;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o domínio de inscrições.
 * 
 * @author Denys
 */
@Stateless
public class InscricaoSB {

	private static final Logger log = Logger.getLogger(InscricaoSB.class.getName());

	@SuppressWarnings("unchecked")
	public static List<Inscricao> getByUser(Integer id) {
		String jpql = "select i from Inscricao i where i.idUsuario = :idUsuario";
		List<Inscricao> inscricoes = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Inscricao.class);
		query.setParameter("idUsuario", id);

		try {
			inscricoes = (List<Inscricao>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return inscricoes;
	}

	public static String salvar(Inscricao inscricao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(inscricao);
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

	public static String excluir(Inscricao inscricao) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.getReference(Inscricao.class, inscricao.getId()));
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
