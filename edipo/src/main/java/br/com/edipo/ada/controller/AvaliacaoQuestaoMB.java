package br.com.edipo.ada.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Avaliacao;
import br.com.edipo.ada.entity.AvaliacaoQuestao;
import br.com.edipo.ada.entity.AvaliacaoQuestaoPK;
import br.com.edipo.ada.entity.Questao;
import br.com.edipo.ada.model.AvaliacaoQuestaoSB;
import br.com.edipo.ada.model.AvaliacaoSB;
import br.com.edipo.ada.model.QuestaoSB;
import br.com.edipo.ada.util.VisaoUtil;

/***
 * <i>Backing bean</i> de escopo por visão que faz o papel de controlador para o domínio entidade que associa questões às avaliações.
 * 
 * @author Denys
 */
@ViewScoped
@ManagedBean
public class AvaliacaoQuestaoMB {

	private static final Logger log = Logger.getLogger(AvaliacaoQuestaoMB.class.getName());

	private String visaoOrigem = VisaoUtil.VISAOORIGEM;

	private AvaliacaoQuestao avaliacaoQuestao;
	private List<AvaliacaoQuestao> avaliacaoQuestoes;

	@PostConstruct
	public void init() {

		String idAvaliacao = VisaoUtil.getViewParam("idAvaliacao");
		String idQuestao = VisaoUtil.getViewParam("idQuestao");

		log.info(String.format("AvaliacaoQuestaoMB: %s, %s)", idAvaliacao, idQuestao));

		if (idAvaliacao != null && idQuestao != null) {

			try {
				AvaliacaoQuestaoPK id = new AvaliacaoQuestaoPK();
				id.setIdAvaliacao(Integer.parseInt(idAvaliacao));
				id.setIdQuestao(Integer.parseInt(idQuestao));

				avaliacaoQuestao = AvaliacaoQuestaoSB.getPorId(id);
			} catch (Exception e) {
				log.severe(e.toString());
			}
		}

		if (avaliacaoQuestao == null) {
			if (idAvaliacao != null) {
				try {
					AvaliacaoQuestaoPK id = new AvaliacaoQuestaoPK();
					avaliacaoQuestao = new AvaliacaoQuestao();
	
					id.setIdAvaliacao(Integer.parseInt(idAvaliacao));
					Avaliacao avaliacao = AvaliacaoSB.getPorId(Integer.parseInt(idAvaliacao));
					avaliacaoQuestao.setAvaliacao(avaliacao);

					if (idQuestao != null) {
						id.setIdQuestao(Integer.parseInt(idQuestao));
						Questao questao = QuestaoSB.getPorId(Integer.parseInt(idQuestao));
						avaliacaoQuestao.setQuestao(questao);
					}

					avaliacaoQuestao.setId(id);
				} catch (Exception e) {
					log.severe(e.toString());
				}
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

	public AvaliacaoQuestao getAvaliacaoQuestao() {
		return avaliacaoQuestao;
	}

	public void setAvaliacaoQuestao(AvaliacaoQuestao avaliacaoQuestao) {
		this.avaliacaoQuestao = avaliacaoQuestao;
	}

	public List<AvaliacaoQuestao> getAvaliacaoQuestoes() {
		if (avaliacaoQuestoes == null) {
			Integer idAvaliacao = null;

			try {
				idAvaliacao = Integer.parseInt(VisaoUtil.getViewParam("idAvaliacao"));
			} catch (Exception e) {
				log.severe(e.toString());
			}

			avaliacaoQuestoes = AvaliacaoQuestaoSB.getPorIdAvaliacao(idAvaliacao);
		}

		return avaliacaoQuestoes;
	}

	public void setAvaliacaoQuestoes(List<AvaliacaoQuestao> avaliacaoQuestoes) {
		this.avaliacaoQuestoes = avaliacaoQuestoes;
	}

	public String salvar(AvaliacaoQuestao avaliacaoQuestao) {

		String excecao = AvaliacaoQuestaoSB.salvar(avaliacaoQuestao);

		if (excecao=="") {
			String mensagem = String.format("Questão %d associada.", avaliacaoQuestao.getQuestao().getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem.concat("idAvaliacao=" + Integer.toString(avaliacaoQuestao.getAvaliacao().getId()));
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}

	public String excluir(AvaliacaoQuestao avaliacaoQuestao) {

		String excecao = AvaliacaoQuestaoSB.excluir(avaliacaoQuestao);

		if (excecao=="") {
			String mensagem = String.format("Questão %d desassociada.", avaliacaoQuestao.getQuestao().getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem.concat("idAvaliacao=" + Integer.toString(avaliacaoQuestao.getAvaliacao().getId()));
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}
