package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;
import java.util.Set;

/**
 * Classe que mapeia a entidade Questao.
 * 
 * @author Denys
 */
@Entity
public class Questao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idQuestao")
	private int id;

	@Column(columnDefinition="BIT")
	private boolean blMultiplaEscolha;

	@Column(columnDefinition="BINARY(1)")
	private byte[] bnImagem;

	private String dsEnunciado;

	private int idUsuario;

	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	@JoinTable(
		name="QuestaoEtiqueta"
		, joinColumns={
			@JoinColumn(name="idQuestao")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idEtiqueta")
			}
		)
	private List<Etiqueta> etiquetas;

	// Necessário ser de um tipo diferente de List para evitar exceção "cannot simultaneously fetch multiple bags".
	// A desvantagem é que para iterar, precisa usar o método toArray().
	@OneToMany(mappedBy="questao",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Set<Alternativa> alternativas;

	public Questao() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getBlMultiplaEscolha() {
		return this.blMultiplaEscolha;
	}

	public void setBlMultiplaEscolha(boolean blMultiplaEscolha) {
		this.blMultiplaEscolha = blMultiplaEscolha;
	}

	public byte[] getBnImagem() {
		return this.bnImagem;
	}

	public void setBnImagem(byte[] bnImagem) {
		this.bnImagem = bnImagem;
	}

	public String getDsEnunciado() {
		return this.dsEnunciado;
	}

	public void setDsEnunciado(String dsEnunciado) {
		this.dsEnunciado = dsEnunciado;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<Etiqueta> getEtiquetas() {
		return this.etiquetas;
	}

	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public Set<Alternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(Set<Alternativa> alternativas) {
		this.alternativas = alternativas;
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
		Questao other = (Questao) obj;
		if (id != other.id)
			return false;
		return true;
	}
}