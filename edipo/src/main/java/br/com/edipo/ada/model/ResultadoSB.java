package br.com.edipo.ada.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import br.com.edipo.ada.entity.Resultado;
import br.com.edipo.ada.util.PersistenciaUtil;

public class ResultadoSB {

	private static final Logger log = Logger.getLogger(ResultadoSB.class.getName());

	@SuppressWarnings("unchecked")
	public static List<Resultado> getResultado(Integer idAvaliacao, Integer idUsuario) {
		String jpql = "select et.dsEtiqueta, avg(ia.vlIndiceAcerto) as vlCalculado "
		+	"from Etiqueta et "
		+	"inner join QuestaoEtiqueta qe on qe.idEtiqueta = et.idEtiqueta "
		+	"inner join ( "
		+	"select qu.idQuestao, (sum(es.vlEscolha) * (sum(case when es.vlEscolha > 0.00 then 1 else 0 end) / count(es.idEscolha))) as vlIndiceAcerto "
		+	"from Escolha es "
		+	"inner join Alternativa al on es.idAlternativa = al.idAlternativa "
		+	"inner join Questao qu on al.idQuestao = qu.idQuestao "
		+	"inner join Resolucao re on re.idResolucao = es.idResolucao "
		+	"where es.blSelecionada = 1 "
		+	"and re.idAvaliacao = ? "
		+	"and re.idUsuario = ? "
		+	"group by qu.idQuestao) ia on ia.idQuestao = qe.idQuestao "
		+	"group by et.dsEtiqueta;";
		List <Resultado> resultados = new ArrayList<Resultado>();

		Object[] _resultado = null;
		List <Object[]> _resultados = null;

		Query query = PersistenciaUtil.getEntityManager().createNativeQuery(jpql);
		query.setParameter(1, idAvaliacao);
		query.setParameter(2, idUsuario);

		try {
			_resultados = (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		Iterator <Object[]> resultadosLista = _resultados.iterator();

		while (resultadosLista.hasNext()) {
			_resultado = resultadosLista.next();

			try {
				resultados.add(new Resultado((String) _resultado[0], (BigDecimal) _resultado[1]));
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		return resultados;
	}

	@SuppressWarnings("unchecked")
	public static List<Resultado> getResultado(Integer idAvaliacao) {
		String jpql = "select et.dsEtiqueta, avg(ia.vlIndiceAcerto) as vlCalculado "
		+	"from Etiqueta et "
		+	"inner join QuestaoEtiqueta qe on qe.idEtiqueta = et.idEtiqueta "
		+	"inner join ( "
		+	"select qu.idQuestao, (sum(es.vlEscolha) * (sum(case when es.vlEscolha > 0.00 then 1 else 0 end) / count(es.idEscolha))) as vlIndiceAcerto "
		+	"from Escolha es "
		+	"inner join Alternativa al on es.idAlternativa = al.idAlternativa "
		+	"inner join Questao qu on al.idQuestao = qu.idQuestao "
		+	"inner join Resolucao re on re.idResolucao = es.idResolucao "
		+	"where es.blSelecionada = 1 "
		+	"and re.idAvaliacao = ? "
		+	"group by qu.idQuestao, re.idUsuario) ia on ia.idQuestao = qe.idQuestao "
		+	"group by et.dsEtiqueta;";
		List <Resultado> resultados = new ArrayList<Resultado>();

		Object[] _resultado = null;
		List <Object[]> _resultados = null;

		Query query = PersistenciaUtil.getEntityManager().createNativeQuery(jpql);
		query.setParameter(1, idAvaliacao);

		try {
			_resultados = (List<Object[]>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		Iterator <Object[]> resultadosLista = _resultados.iterator();

		while (resultadosLista.hasNext()) {
			_resultado = resultadosLista.next();

			try {
				resultados.add(new Resultado((String) _resultado[0], (BigDecimal) _resultado[1]));
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		return resultados;
	}

	public static Long getNrResolucoes(Integer idAvaliacao) {
		String jpql = "select count(r) from Resolucao r where r.avaliacao.id = :idAvaliacao";
		Long nrResolucoes = 0L;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("idAvaliacao", idAvaliacao);

		try {
			nrResolucoes = (Long) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return nrResolucoes;
	}

	public static Long getNrInscritos(Integer idAvaliacao) {
		String jpql = "select count(distinct i.idUsuario) "
			+	"from Avaliacao a "
			+	"join a.cursos c "
			+	"join c.inscritos i "
			+	"where a.id = :idAvaliacao";
		Long nrInscritos = 0L;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("idAvaliacao", idAvaliacao);

		try {
			nrInscritos = (Long) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return nrInscritos;
	}

	public static BigDecimal getIndiceAcerto(Integer idResolucao, Integer idQuestao) {
		String jpql = "select sum(es.vlEscolha) * ( sum( case when es.vlEscolha > 0.00 then 1 else 0 end ) / count(es) ) "
			+	"from Escolha es "
			+	"join es.alternativa al "
			+	"join al.questao qu "
			+	"where es.blSelecionada = 1 "
			+	"and es.resolucao.id = :idResolucao "
			+	"and qu.id = :idQuestao "
			+	"group by qu";
		BigDecimal vlIndiceAcerto = BigDecimal.ZERO;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql);
		query.setParameter("idResolucao", idResolucao);
		query.setParameter("idQuestao", idQuestao);

		log.info(String.format("getIndiceAcerto: %d, %d", idResolucao, idQuestao));

		try {
			vlIndiceAcerto = (BigDecimal) query.getSingleResult();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return vlIndiceAcerto;
	}
}
