package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Resolucao;
import br.com.edipo.ada.model.ResolucaoSB;
import br.com.edipo.ada.security.AutorizacaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por vis‹o que faz o papel de controlador para o dom’nio de resultados.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class CorrecaoMB {
	private static final Logger log = Logger.getLogger(CorrecaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private List<Resolucao> resolucoes;

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
}