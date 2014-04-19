package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;

/**
 * Classe que mapeia a entidade Alternativa.
 * 
 * @author Denys
 */
@Entity
public class Alternativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idAlternativa")
	private int id;

	private String dsAlternativa;

	private BigDecimal vlAlternativa;

	@Column(columnDefinition="BIT")
	@Transient
	private boolean blEscolhida;

	@ManyToOne
	@JoinColumn(name="idQuestao")
	private Questao questao;

	public Alternativa() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDsAlternativa() {
		return this.dsAlternativa;
	}

	public void setDsAlternativa(String dsAlternativa) {
		this.dsAlternativa = dsAlternativa;
	}

	public BigDecimal getVlAlternativa() {
		return this.vlAlternativa;
	}

	public void setVlAlternativa(BigDecimal vlAlternativa) {
		this.vlAlternativa = vlAlternativa;
	}

	public boolean isBlEscolhida() {
		return blEscolhida;
	}

	public void setBlEscolhida(boolean blEscolhida) {
		this.blEscolhida = blEscolhida;
	}

	public Questao getQuestao() {
		return this.questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Alternativa other = (Alternativa) obj;
		if (id != other.id)
			return false;
		return true;
	}
}