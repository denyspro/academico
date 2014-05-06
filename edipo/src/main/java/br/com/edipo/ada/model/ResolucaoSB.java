package br.com.edipo.ada.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import br.com.edipo.ada.entity.AvaliacaoQuestao;
import br.com.edipo.ada.entity.Resolucao;
import br.com.edipo.ada.util.PersistenciaUtil;

/***
 * <i>Stateless bean</i> que faz o papel de modelo para o domínio de resoluções.
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
	}

	public static Resolucao getPorIdAvaliacao(Integer idUsuario, Integer idAvaliacao) {
		String jpql = "select distinct r from Resolucao r join fetch r.avaliacao a join fetch a.cursos c where r.idUsuario = :idUsuario and a.id = :idAvaliacao";
		Resolucao resolucao = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Resolucao.class);
		query.setParameter("idUsuario", idUsuario);
		query.setParameter("idAvaliacao", idAvaliacao);

		try {
			resolucao = (Resolucao) query.getResultList().get(0);
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resolucao;
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

	public static BigDecimal getVlResultadoCalculado(Resolucao resolucao) {
		BigDecimal vlResultado = BigDecimal.ZERO;
		BigDecimal vlIndiceAcerto = BigDecimal.ZERO;

		BigDecimal vlAvaliacao = resolucao.getAvaliacao().getVlAvaliacao();
		BigDecimal vlSomaQuestoes = AvaliacaoQuestaoSB.getVlSomaQuestoes(resolucao.getAvaliacao().getId());

		List<AvaliacaoQuestao> avaliacaoQuestoes = AvaliacaoQuestaoSB.getPorIdAvaliacao(resolucao.getAvaliacao().getId());
		Iterator <AvaliacaoQuestao> questoes = avaliacaoQuestoes.iterator();
		AvaliacaoQuestao avaliacaoQuestao = null;

		log.info(String.format("Valor da avaliação: %s", vlAvaliacao.toPlainString()));
		log.info(String.format("Valor da soma questões: %s", vlSomaQuestoes.toPlainString()));

		while (questoes.hasNext()) {
			avaliacaoQuestao = questoes.next();

			vlIndiceAcerto = ResultadoSB.getIndiceAcerto(resolucao.getId(), avaliacaoQuestao.getQuestao().getId()).divide(new BigDecimal(100));

			log.info(String.format("Índice acerto: %s", vlIndiceAcerto.toPlainString()));
			log.info(String.format("Peso questão: %s", avaliacaoQuestao.getVlQuestao().divide(vlSomaQuestoes, 2, RoundingMode.HALF_UP).toPlainString()));
			log.info(String.format("Valor questão: %s", vlAvaliacao.multiply(avaliacaoQuestao.getVlQuestao().divide(vlSomaQuestoes, 2, RoundingMode.HALF_UP).multiply(vlIndiceAcerto)).toPlainString()));

			vlResultado = vlResultado.add(vlAvaliacao.multiply(avaliacaoQuestao.getVlQuestao().divide(vlSomaQuestoes, 2, RoundingMode.HALF_UP).multiply(vlIndiceAcerto)));
		}

		log.info(String.format("Resultado: %s", vlResultado.toPlainString()));

		return vlResultado;
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
