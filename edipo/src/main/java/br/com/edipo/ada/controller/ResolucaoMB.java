package br.com.edipo.ada.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.edipo.ada.entity.Alternativa;
import br.com.edipo.ada.entity.Avaliacao;
import br.com.edipo.ada.entity.AvaliacaoQuestao;
import br.com.edipo.ada.entity.Escolha;
import br.com.edipo.ada.entity.Questao;
import br.com.edipo.ada.entity.Resolucao;
import br.com.edipo.ada.model.AvaliacaoQuestaoSB;
import br.com.edipo.ada.model.AvaliacaoSB;
import br.com.edipo.ada.model.QuestaoSB;
import br.com.edipo.ada.model.ResolucaoSB;
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

	private Resolucao resolucao;

	private Avaliacao avaliacao;
	private List<Avaliacao> avaliacoes;

	private List<AvaliacaoQuestao> avaliacaoQuestoes;
	private List<Alternativa> escolhidas;

	@PostConstruct
	public void init() {
		Integer idAvaliacao = null;
		Integer idUsuario = null;

		try {
			idAvaliacao = (VisaoUtil.getViewParam("idAvaliacao") != null) ? Integer.parseInt(VisaoUtil.getViewParam("idAvaliacao")) : null;
			idUsuario = Integer.parseInt(AutorizacaoSB.getAtributo("id"));
		} catch (Exception e) {
			log.severe(e.toString());
		}

		// Caso uma avaliação tenha sido selecionada, inicializa uma resolução com base em seus dados. 
		if (idAvaliacao != null && idUsuario != null) {
			log.info(String.format("idAvaliacao: %s)", idAvaliacao));

			resolucao = new Resolucao();
			resolucao.setIdUsuario(idUsuario);
			resolucao.setDtIniResolucao(new Date());
			resolucao.setAvaliacao(AvaliacaoSB.getPorId(idAvaliacao));
			resolucao.setVlResultado(resolucao.getAvaliacao().getVlAvaliacao());

			Questao questao = null;
			Alternativa alternativa = null;
			Escolha escolha = null;
			BigDecimal vlSomaAlternativas = null;

			Iterator <AvaliacaoQuestao> questoes = getAvaliacaoQuestoes().iterator();
			Iterator <Alternativa> alternativas = null;

			while(questoes.hasNext()) {
				questao = questoes.next().getQuestao();
				alternativas = questao.getAlternativas().iterator();
				vlSomaAlternativas = QuestaoSB.getVlSomaAlternativas(questao);

				while(alternativas.hasNext()) {
					alternativa = alternativas.next();

					if (alternativa != null) {
						escolha = new Escolha();

						escolha.setVlEscolha((vlSomaAlternativas == BigDecimal.ZERO) ? BigDecimal.ZERO : alternativa.getVlAlternativa().divide(vlSomaAlternativas.divide(new BigDecimal("100")), 2, RoundingMode.HALF_UP));
						escolha.setBlSelecionada(false);
						escolha.setAlternativa(alternativa);

						log.info(String.format("Adicionando a alternativa '%s' com valor %s...", alternativa.getDsAlternativa(), escolha.getVlEscolha().toPlainString()));
						resolucao.addEscolha(escolha);
					}
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

	public Resolucao getResolucao() {
		return resolucao;
	}

	public void setResolucao(Resolucao resolucao) {
		this.resolucao = resolucao;
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
				avaliacoes = AvaliacaoSB.getAbertas(idUsuario);
			} catch (Exception e) {
				log.severe("getAvaliacoes: " + e.toString());
			}
		}
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
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

	public List<Alternativa> getEscolhidas() {
		return escolhidas;
	}

	public void setEscolhidas(List<Alternativa> escolhidas) {
		// Cada item de seleção na tela chama uma vez este método
		// O algoritmo abaixo garante que as seleções serão colocadas na mesma lista
		// Para que possam ser salvas em conjunto
		if (this.escolhidas == null) {
			this.escolhidas = escolhidas;
		} else {
			this.escolhidas.addAll(escolhidas);
		}
	}

	public String salvar(Resolucao resolucao) {
		Alternativa alternativa = null;
		Escolha escolha = null;

		Iterator <Alternativa> a = escolhidas.iterator();
		Iterator <Escolha> e = resolucao.getEscolhas().iterator(); // Como são preenchidas na mesma ordem, não preciso iterar desde o início

		while(a.hasNext()) {
			alternativa = a.next();
			log.info(String.format("Alternativa '%s'...", alternativa.getDsAlternativa()));

			while(e.hasNext()) {
				escolha = e.next();
				log.info(String.format(" Escolha '%s'...", escolha.getAlternativa().getDsAlternativa()));

				if (alternativa.equals(escolha.getAlternativa())) {
					escolha.setBlSelecionada(true); // Marca alternativa escolhida
					log.info(" Selecionada!");
					break;
				}
			}
		}

		resolucao.setDtFimResolucao(new Date());

		String excecao = ResolucaoSB.salvar(resolucao);

		if (excecao=="") {
			String mensagem = (resolucao.getId() == 0) ? String.format("Nova resolução salva.") : String.format("Resolução %d salva.", resolucao.getId());
			VisaoUtil.setMessage(mensagem);
			return visaoOrigem;
		} else {
			VisaoUtil.setMessage(excecao);
			return "";
		}
	}
}
