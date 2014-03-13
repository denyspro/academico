package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

	private static final Logger log = Logger.getLogger(InscricaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Questao questao;
	private List<Questao> questoes;
	
	@PostConstruct
	public void init() {

		String id = VisaoUtil.getViewParam("id");

		if (id != null) {
			try {
				questao = QuestaoSB.getById(Integer.parseInt(id));
			} catch (Exception e) {
				log.severe(e.toString());
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

			questoes = QuestaoSB.getByUser(idUsuario);
		}
		return questoes;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}

	public String salvar(Questao questao) {

		String excecao = QuestaoSB.salvar(questao);

		if (excecao=="") {
			String mensagem = String.format("Quest‹o %s salva." , questao.getId());

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
			String mensagem = String.format("Quest‹o %s exclu’da.", questao.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}
