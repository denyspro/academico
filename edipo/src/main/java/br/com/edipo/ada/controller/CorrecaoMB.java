package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Resolucao;
import br.com.edipo.ada.entity.Resultado;
import br.com.edipo.ada.model.ResolucaoSB;
import br.com.edipo.ada.model.ResultadoSB;
import br.com.edipo.ada.security.AutorizacaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por visão que faz o papel de controlador para o domínio de correções individuais.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class CorrecaoMB {
	private static final Logger log = Logger.getLogger(CorrecaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Resolucao resolucao;
	private List<Resolucao> resolucoes;
	private List<Resultado> resultados;

	@PostConstruct
	public void init() {
		Integer idResolucao = null;

		try {
			idResolucao = (VisaoUtil.getViewParam("idResolucao") != null) ? Integer.parseInt(VisaoUtil.getViewParam("idResolucao")) : null;
		} catch (Exception e) {
			log.severe(e.toString());
		}

		if (idResolucao != null) {
			log.info(String.format("Resolução selecionada: %s", idResolucao));

			try {
				resolucao = ResolucaoSB.getPorId(idResolucao);
			} catch (Exception e) {
				log.severe(e.toString());
			}

			if (resolucao != null) {
				resultados = ResultadoSB.getResultado(resolucao.getAvaliacao().getId(), resolucao.getIdUsuario());
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

	public Resolucao getResolucao() {
		return resolucao;
	}

	public void setResolucao(Resolucao resolucao) {
		this.resolucao = resolucao;
	}

	public List<Resolucao> getResolucoes() {
		Integer idUsuario = null;

		if (resolucoes == null) {
			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe(e.toString());
			}
	
			resolucoes = ResolucaoSB.getResolvidas(idUsuario);
		}
		return resolucoes;
	}

	public void setResolucoes(List<Resolucao> resolucoes) {
		this.resolucoes = resolucoes;
	}

	public List<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}
}