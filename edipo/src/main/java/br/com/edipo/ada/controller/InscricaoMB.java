package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Inscricao;
import br.com.edipo.ada.model.InscricaoSB;
import br.com.edipo.ada.security.AutorizacaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por visão que faz o papel de controlador para o domínio de inscrições.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class InscricaoMB {

	private static final Logger log = Logger.getLogger(InscricaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Inscricao inscricao;
	private List<Inscricao> inscricoes;

	@PostConstruct
	public void init() {

		String id = VisaoUtil.getViewParam("id");

		if (id != null) {
			try {
				inscricao = InscricaoSB.getPorId(Integer.parseInt(id));
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		if (inscricao == null) {
			Integer idUsuario = null;

			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("init: " + e.toString());
			}

			inscricao = new Inscricao();
			inscricao.setIdUsuario(idUsuario);
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

	public Inscricao getInscricao() {
		return inscricao;
	}

	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}

	public List<Inscricao> getInscricoes() {
		Integer idUsuario = null;

		if (inscricoes == null) {
			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("getInscricoes: " + e.toString());
			}

			inscricoes = InscricaoSB.getPorIdUsuario(idUsuario);
		}
		return inscricoes;
	}

	public void setInscricoes(List<Inscricao> inscricoes) {
		this.inscricoes = inscricoes;
	}

	public String salvar(Inscricao inscricao) {

		String excecao = InscricaoSB.salvar(inscricao);

		if (excecao=="") {
			String mensagem = String.format("Inscrição %s salva." , inscricao.getId());

			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}

	public String excluir(Inscricao inscricao) {

		String excecao = InscricaoSB.excluir(inscricao);

		if (excecao=="") {
			String mensagem = String.format("Inscrição %s excluída.", inscricao.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}