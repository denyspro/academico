package br.com.edipo.ada.model;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.Query;

import br.com.edipo.ada.entity.Resultado;
import br.com.edipo.ada.util.PersistenciaUtil;

public class ResultadoSB {

	private static final Logger log = Logger.getLogger(ResultadoSB.class.getName());

	@SuppressWarnings("unchecked")
	public static List<Resultado> getResultado(Integer idAvaliacao, Integer idUsuario) {
		String jpql = "select new br.com.edipo.ada.entity.Resultado (et.dsEtiqueta, avg(es.vlEscolha) as vlCalculado) "
				+	"from Escolha es "
				+	"join es.resolucao re "
				+	"join es.alternativa al "
				+	"join al.questao qu "
				+	"join qu.etiquetas et "
				+	"where es.blSelecionada = 1 "
				+	"and re.avaliacao.id = :idAvaliacao "
				+	"and re.idUsuario = :idUsuario "
				+	"group by et.dsEtiqueta";
		List <Resultado> resultados = null;

		Query query = PersistenciaUtil.getEntityManager().createQuery(jpql, Resultado.class);
		query.setParameter("idAvaliacao", idAvaliacao);
		query.setParameter("idUsuario", idUsuario);

		try {
			resultados = (List<Resultado>) query.getResultList();
		} catch (Exception e) {
			log.severe(e.toString());
		} finally {
			PersistenciaUtil.closeEntityManager();
		}

		return resultados;
	}
}
