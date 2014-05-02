package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Avaliacao;
import br.com.edipo.ada.entity.Resultado;
import br.com.edipo.ada.model.AvaliacaoSB;
import br.com.edipo.ada.model.ResultadoSB;
import br.com.edipo.ada.security.AutorizacaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por visão que faz o papel de controlador para o domínio de resultados.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class ResultadoMB {
	private static final Logger log = Logger.getLogger(ResultadoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Avaliacao avaliacao;
	private List<Avaliacao> avaliacoes;
	private List<Resultado> resultados;

	private Long nrResolucoes = 0L;
	private Long nrInscritos = 0L;

	@PostConstruct
	public void init() {
		Integer idAvaliacao = null;

		try {
			idAvaliacao = (VisaoUtil.getViewParam("idAvaliacao") != null) ? Integer.parseInt(VisaoUtil.getViewParam("idAvaliacao")) : null;
		} catch (Exception e) {
			log.severe(e.toString());
		}

		if (idAvaliacao != null) {
			log.info(String.format("Avaliação selecionada: %s", idAvaliacao));

			try {
				avaliacao = AvaliacaoSB.getPorId(idAvaliacao);
			} catch (Exception e) {
				log.severe(e.toString());
			}

			if (avaliacao != null) {
				resultados = ResultadoSB.getResultado(avaliacao.getId());

				nrResolucoes = ResultadoSB.getNrResolucoes(idAvaliacao);
				nrInscritos = ResultadoSB.getNrInscritos(idAvaliacao);
			}
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

			avaliacoes = AvaliacaoSB.getIniciadas(idUsuario);
		}
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}

	public Long getNrResolucoes() {
		return nrResolucoes;
	}

	public void setNrResolucoes(Long nrResolucoes) {
		this.nrResolucoes = nrResolucoes;
	}

	public Long getNrInscritos() {
		return nrInscritos;
	}

	public void setNrInscritos(Long nrInscritos) {
		this.nrInscritos = nrInscritos;
	}
}