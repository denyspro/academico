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

	public static Inscricao getPorId(Integer id) {
		return PersistenciaUtil.getEntityManager().find(Inscricao.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Inscricao> getPorIdUsuario(Integer idUsuario) {
		String jpql = "select distinct i from Inscricao i join fetch i.curso c join fetch c.usuario u where i.idUsuario = :idUsuario order by c.dsCurso";
		List<Inscricao> inscricoes = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Inscricao.class);
		query.setParameter("idUsuario", idUsuario);

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
			em.persist(em.merge(inscricao));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			if (PersistenciaUtil.possuiNaExcecao(e, "ConstraintViolationException")) {
				resposta = "Não é possível inscrever-se mais de uma vez no mesmo curso.";
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

			if (PersistenciaUtil.possuiNaExcecao(e, "ConstraintViolationException")) {
				resposta = "Não é possível excluir esta inscrição porque já se encontra em uso.";
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
