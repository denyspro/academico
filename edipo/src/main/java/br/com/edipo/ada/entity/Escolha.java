package br.com.edipo.ada.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * Classe que mapeia a entidade Escolha.
 * 
 * @author Denys
 */
@Entity
public class Escolha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idEscolha")
	private int id;

	private BigDecimal vlEscolha;

	@Column(columnDefinition="BIT")
	private boolean blSelecionada;

	@ManyToOne
	@JoinColumn(name="idResolucao")
	private Resolucao resolucao;

	@ManyToOne
	@JoinColumn(name="idAlternativa")
	private Alternativa alternativa;

	public Escolha() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getVlEscolha() {
		return vlEscolha;
	}

	public void setVlEscolha(BigDecimal vlEscolha) {
		this.vlEscolha = vlEscolha;
	}

	public boolean getBlSelecionada() {
		return this.blSelecionada;
	}

	public void setBlSelecionada(boolean blSelecionada) {
		this.blSelecionada = blSelecionada;
	}

	public Resolucao getResolucao() {
		return this.resolucao;
	}

	public void setResolucao(Resolucao resolucao) {
		this.resolucao = resolucao;
	}

	public Alternativa getAlternativa() {
		return this.alternativa;
	}

	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
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
		Escolha other = (Escolha) obj;
		if (id != other.id)
			return false;
		return true;
	}
}