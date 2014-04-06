package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Classe que representa a chave prim‡ria da entidade AvaliacaoQuestao.
 * 
 * @author Denys
 */
@Embeddable
public class AvaliacaoQuestaoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idAvaliacao;

	private int idQuestao;

	public AvaliacaoQuestaoPK() {
	}
	public int getIdAvaliacao() {
		return this.idAvaliacao;
	}
	public void setIdAvaliacao(int idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}
	public int getIdQuestao() {
		return this.idQuestao;
	}
	public void setIdQuestao(int idQuestao) {
		this.idQuestao = idQuestao;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AvaliacaoQuestaoPK)) {
			return false;
		}
		AvaliacaoQuestaoPK castOther = (AvaliacaoQuestaoPK)other;
		return 
			(this.idAvaliacao == castOther.idAvaliacao)
			&& (this.idQuestao == castOther.idQuestao);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAvaliacao;
		hash = hash * prime + this.idQuestao;
		
		return hash;
	}
}