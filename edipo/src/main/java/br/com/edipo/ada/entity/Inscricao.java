package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Classe que mapeia a entidade Inscricao.
 * 
 * @author Denys
 */
@Entity
public class Inscricao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idInscricao")
	private int id;

	private int idUsuario;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idCurso")
	private Curso curso;

	public Inscricao() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
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
		Inscricao other = (Inscricao) obj;
		if (id != other.id)
			return false;
		return true;
	}
}