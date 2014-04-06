package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Alternativa;
import br.com.edipo.ada.model.AlternativaSB;
import br.com.edipo.ada.model.QuestaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por vis‹o que faz o papel de controlador para o dom’nio de alternativas.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class AlternativaMB {

	private static final Logger log = Logger.getLogger(AlternativaMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Alternativa alternativa;
	private List<Alternativa> alternativas;

	@PostConstruct
	public void init() {

		String id = VisaoUtil.getViewParam("id");

		if (id != null) {
			try {
				alternativa = AlternativaSB.getPorId(Integer.parseInt(id));
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		if (alternativa == null) {
			Integer idQuestao = null;

			try {
				idQuestao = Integer.parseInt(VisaoUtil.getViewParam("idQuestao"));
			} catch (Exception e) {
				log.severe("init: " + e.toString());
			}

			alternativa = new Alternativa();
			alternativa.setQuestao(QuestaoSB.getPorId(idQuestao));
		}
	}

	@PreDestroy
	public void release() {
		log.info("Liberando recursos...");
	}

	public String getVisaoOrigem() {
		return visaoOrigem;
	}

	public void setVisaoOrigem(String origem) {
		this.visaoOrigem = origem;
	}

	public Alternativa getAlternativa() {
		return alternativa;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
	}

	public List<Alternativa> getAlternativas() {
		if (alternativas == null) {
			Integer idQuestao = null;

			try {
				idQuestao = Integer.parseInt(VisaoUtil.getViewParam("idQuestao"));
			} catch (Exception e) {
				log.severe(e.toString());
			}

			alternativas = AlternativaSB.getPorIdQuestao(idQuestao);
		}

		return alternativas;
	}

	public void setAlternativas(List<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}

	public String salvar(Alternativa alternativa) {

		String excecao = AlternativaSB.salvar(alternativa);

		if (excecao=="") {
			String mensagem = String.format("Alternativa %d salva.", alternativa.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem.concat("idQuestao=" + Integer.toString(alternativa.getQuestao().getId()));
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}

	public String excluir(Alternativa alternativa) {

		String excecao = AlternativaSB.excluir(alternativa);

		if (excecao=="") {
			String mensagem = String.format("Alternativa %d exclu’da.", alternativa.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem.concat("idQuestao=" + Integer.toString(alternativa.getQuestao().getId()));
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}
