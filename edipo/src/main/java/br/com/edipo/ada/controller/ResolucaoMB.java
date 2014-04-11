package br.com.edipo.ada.controller;

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
public class ResolucaoMB {

	private static final Logger log = Logger.getLogger(ResolucaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Avaliacao avaliacao;
	private List<Avaliacao> avaliacoes;

	@PostConstruct
	public void init() {

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

			avaliacoes = AvaliacaoSB.getAbertas(idUsuario);
		}
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

}
