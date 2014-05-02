package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;

/**
 * Classe que mapeia a entidade que associa questões às avaliações.
 * 
 * @author Denys
 */
@Entity
public class AvaliacaoQuestao implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AvaliacaoQuestaoPK id;

	private BigDecimal vlQuestao;

	@Column(columnDefinition="BIT")
	@Transient
	private boolean blMultiplaEscolha;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idAvaliacao", insertable=false, updatable=false)
	private Avaliacao avaliacao;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idQuestao", insertable=false, updatable=false)
	private Questao questao;

	public AvaliacaoQuestao() {
	}

	public AvaliacaoQuestaoPK getId() {
		return this.id;
	}

	public void setId(AvaliacaoQuestaoPK id) {
		this.id = id;
	}

	public BigDecimal getVlQuestao() {
		return this.vlQuestao;
	}

	public void setVlQuestao(BigDecimal vlQuestao) {
		this.vlQuestao = vlQuestao;
	}

	public Avaliacao getAvaliacao() {
		return this.avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public Questao getQuestao() {
		return this.questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvaliacaoQuestao other = (AvaliacaoQuestao) obj;

		return id.equals(other.id);
	}
}