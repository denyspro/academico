package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Etiqueta;
import br.com.edipo.ada.entity.Questao;
import br.com.edipo.ada.model.EtiquetaSB;
import br.com.edipo.ada.model.QuestaoSB;
import br.com.edipo.ada.security.AutorizacaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por vis‹o que faz o papel de controlador para o dom’nio de etiquetas.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class EtiquetaMB {

	private static final Logger log = Logger.getLogger(InscricaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private Etiqueta etiqueta;
	private List<Etiqueta> etiquetas;
	
	@PostConstruct
	public void init() {

		String id = VisaoUtil.getViewParam("id");

		if (id != null) {
			try {
				etiqueta = EtiquetaSB.getById(Integer.parseInt(id));
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		if (etiqueta == null) {
			Integer idQuestao = null;

			try {
				idQuestao = Integer.parseInt(VisaoUtil.getViewParam("idQuestao"));
			} catch (Exception e) {
				log.severe("init: " + e.toString());
			}

			etiqueta = new Etiqueta();
			List<Questao> questoes = etiqueta.getQuestoes();
			questoes.add(QuestaoSB.getPorId(idQuestao));
			etiqueta.setQuestoes(questoes);
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

	public Etiqueta getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(Etiqueta etiqueta) {
		this.etiqueta = etiqueta;
	}

	public List<Etiqueta> getEtiquetas() {
		Integer idUsuario = null;

		if (etiquetas == null) {
			try {
				idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
			} catch (Exception e) {
				log.severe("getEtiquetas: " + e.toString());
			}

			etiquetas = EtiquetaSB.getPorIdUsuario(idUsuario);
		}
		return etiquetas;
	}

	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public String salvar(Etiqueta etiqueta) {

		String excecao = EtiquetaSB.salvar(etiqueta);

		if (excecao=="") {
			String mensagem = (etiqueta.getId() == 0) ? String.format("Nova etiqueta salva.") : String.format("Etiqueta %d salva.", etiqueta.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}

	public String excluir(Etiqueta etiqueta) {

		String excecao = EtiquetaSB.excluir(etiqueta);

		if (excecao=="") {
			String mensagem = String.format("Etiqueta %d exclu’da.", etiqueta.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}
