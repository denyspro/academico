package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * Classe que mapeia a entidade Curso.
 * 
 * @author Denys
 */
@Entity
public class Curso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idCurso")
	private int id;

	private String dsCurso;

	private int idUsuario;

	@OneToMany(mappedBy="curso")
	private List<Inscricao> inscritos;

	public Curso() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDsCurso() {
		return this.dsCurso;
	}

	public void setDsCurso(String dsCurso) {
		this.dsCurso = dsCurso;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<Inscricao> getInscritos() {
		return this.inscritos;
	}

	public void setInscritos(List<Inscricao> inscritos) {
		this.inscritos = inscritos;
	}

	public Inscricao addInscrito(Inscricao inscrito) {
		getInscritos().add(inscrito);
		inscrito.setCurso(this);

		return inscrito;
	}

	public Inscricao removeInscrito(Inscricao inscrito) {
		getInscritos().remove(inscrito);
		inscrito.setCurso(null);

		return inscrito;
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
		Curso other = (Curso) obj;
		if (id != other.id)
			return false;
		return true;
	}
}