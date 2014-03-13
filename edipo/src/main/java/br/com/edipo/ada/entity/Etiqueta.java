package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * Classe que mapeia a entidade Etiqueta.
 * 
 * @author Denys
 */
@Entity
public class Etiqueta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idEtiqueta")
	private int id;

	@ManyToMany(mappedBy="etiquetas", fetch=FetchType.EAGER)
	private List<Questao> questoes;

	public Etiqueta() {
	}

	public int getId() {
		return this.id;
	}

	public void setIdEtiqueta(int id) {
		this.id = id;
	}

	public List<Questao> getQuestoes() {
		return this.questoes;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
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
		Etiqueta other = (Etiqueta) obj;
		if (id != other.id)
			return false;
		return true;
	}
}