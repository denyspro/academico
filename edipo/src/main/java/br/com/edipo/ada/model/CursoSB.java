package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.Curso;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o domínio de cursos.
 * 
 * @author Denys
 */
@Stateless
public class CursoSB {

	private static final Logger log = Logger.getLogger(CursoSB.class.getName());

	public static Curso getById(Integer id) {
		return PersistenciaUtil.getEntityManager().find(Curso.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<Curso> getByUser(Integer idUsuario) {
		String jpql = "select c from Curso c where c.usuario = :usuario";
		List<Curso> cursos = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Curso.class);
		query.setParameter("usuario", UsuarioSB.getById(idUsuario));

		try {
			cursos = (List<Curso>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return cursos;
	}

	public static String salvar(Curso curso) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(em.merge(curso));
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

	public static String excluir(Curso curso) {

		String resposta = "";

		EntityManager em = PersistenciaUtil.getEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.remove(em.getReference(Curso.class, curso.getId()));
			tx.commit();
		} catch (Exception e) {
			log.severe(e.toString());

			resposta = e.getMessage();

			Throwable t = e.getCause();

			/***
			 * O contêiner encapsula a causa raiz em outras exceções, sem dar acesso direto à sua classe.
			 * Portanto, a única forma de identificar é buscando pelo nome da classe entre as mensagens.
			 */
			while (t != null) {
				if (t.getMessage().contains("ConstraintViolationException")) {
					resposta = "Este item não pode ser excluído porque encontra-se em uso.";
					break;
				}

				t = t.getCause();
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
