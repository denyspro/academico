package br.com.edipo.ada.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Etiqueta;
import br.com.edipo.ada.entity.Questao;
import br.com.edipo.ada.model.QuestaoSB;
import br.com.edipo.ada.security.AutorizacaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por vis‹o que faz o papel de controlador para o dom’nio de quest›es.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class QuestaoMB {

	private static final Logger log = Logger.getLogger(QuestaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Questao questao;
	private List<Questao> questoes;
	private String dsEtiquetas;

	private static final String EXP_ETIQUETA = "#\\w+";

	@PostConstruct
	public void init() {

		String id = VisaoUtil.getViewParam("id");

		if (id != null) {
			try {
				questao = QuestaoSB.getPorId(Integer.parseInt(id));
			} catch (Exception e) {
				log.severe(e.toString());
			}
		} else {

			id = VisaoUtil.getViewParam("idQuestao");

			if (id != null) {
				try {
					questao = QuestaoSB.getPorId(Integer.parseInt(id));
				} catch (Exception e) {
					log.severe(e.toString());
				}
			}
		}

		if (questao == null) {
			Integer idUsuario = null;

			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("init: " + e.toString());
			}

			questao = new Questao();
			questao.setIdUsuario(idUsuario);
		}
	}

	@PreDestroy
	public void release() {
		log.info("Liberando recursos...");
	}

	public String getVisaoOrigem() {
		return visaoOrigem;
	}

	public void setVisaoOrigem(String visaoOrigem) {
		this.visaoOrigem = visaoOrigem;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public List<Questao> getQuestoes() {
		Integer idUsuario = null;

		if (questoes == null) {
			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("getQuestoes: " + e.toString());
			}

			questoes = QuestaoSB.getPorIdUsuario(idUsuario);
		}
		return questoes;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}

	public String getDsEtiquetas() {

		if (dsEtiquetas == null && questao.getEtiquetas() != null) {
			Iterator <Etiqueta> i = questao.getEtiquetas().iterator();

			dsEtiquetas = "";

			while(i.hasNext()) {
				dsEtiquetas += " " + i.next().getDsEtiqueta();
				log.info("Etiqueta encontrada: " + dsEtiquetas);
			}
		}

		return dsEtiquetas;
	}

	public void setDsEtiquetas(String etiquetasEmLinha) {
		this.dsEtiquetas = etiquetasEmLinha;
	}

	public int getNrAlternativas(Questao questao) {
		int nrAlternativas = 0;

		nrAlternativas = QuestaoSB.getNrAlternativas(questao.getId());

		return nrAlternativas;
	}

	public String salvar(Questao questao) {

		if (dsEtiquetas != null) {
			Pattern padrao = Pattern.compile(EXP_ETIQUETA, Pattern.CASE_INSENSITIVE);
			Matcher combinador = padrao.matcher(dsEtiquetas);

			Etiqueta etiqueta = null;
			ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();

			while (combinador.find()) {
				log.info("Etiqueta identificada: " + combinador.group());

				etiqueta = new Etiqueta();
				etiqueta.setDsEtiqueta(combinador.group());

				etiquetas.add(etiqueta);
			}

			if(!etiquetas.isEmpty()) {
				questao.setEtiquetas(etiquetas);
			}
		}

		String excecao = QuestaoSB.salvar(questao);

		if (excecao=="") {
			String mensagem = String.format("Quest‹o %d salva." , questao.getId());

			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}

	public String excluir(Questao questao) {

		String excecao = QuestaoSB.excluir(questao);

		if (excecao=="") {
			String mensagem = String.format("Quest‹o %d exclu’da.", questao.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}
