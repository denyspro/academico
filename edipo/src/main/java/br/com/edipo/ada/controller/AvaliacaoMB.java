package br.com.edipo.ada.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Avaliacao;
import br.com.edipo.ada.model.AvaliacaoSB;
import br.com.edipo.ada.security.AutorizacaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por visão que faz o papel de controlador para o domínio de avaliações.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class AvaliacaoMB {

	private static final Logger log = Logger.getLogger(AvaliacaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Avaliacao avaliacao;
	private List<Avaliacao> avaliacoes;

	@PostConstruct
	public void init() {

		String id = VisaoUtil.getViewParam("id");

		if (id != null) {
			try {
				avaliacao = AvaliacaoSB.getPorId(Integer.parseInt(id));
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		if (avaliacao == null) {
			Integer idUsuario = null;

			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("init: " + e.toString());
			}

			avaliacao = new Avaliacao();
			avaliacao.setIdUsuario(idUsuario);
			avaliacao.setDtIniAvaliacao(new Date());
			avaliacao.setDtFimAvaliacao(new Date());
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

	public Avaliacao getAvaliacao() {
		return avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public List<Avaliacao> getAvaliacoes() {
		Integer idUsuario = null;

		if (avaliacoes == null) {
			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("getAvaliacoes: " + e.toString());
			}

			avaliacoes = AvaliacaoSB.getPorIdUsuario(idUsuario);
		}
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public int getNrQuestoes(Avaliacao avaliacao) {
		int nrQuestoes = 0;

		nrQuestoes = AvaliacaoSB.getNrQuestoes(avaliacao.getId());

		return nrQuestoes;
	}

	public String salvar(Avaliacao avaliacao) {

		String excecao = AvaliacaoSB.salvar(avaliacao);

		if (excecao=="") {
			String mensagem = (avaliacao.getId() == 0) ? String.format("Nova avaliação salva.") : String.format("Avaliação %d salva.", avaliacao.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}

	public String excluir(Avaliacao avaliacao) {

		String excecao = AvaliacaoSB.excluir(avaliacao);

		if (excecao=="") {
			String mensagem = String.format("Avaliacao %d excluída.", avaliacao.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}
