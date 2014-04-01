package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Classe que mapeia a entidade Avaliacao.
 * 
 * @author Denys
 */
@Entity
public class Avaliacao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idAvaliacao")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtIniAvaliacao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtFimAvaliacao;

	private int idUsuario;

	private BigDecimal vlAvaliacao;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="AvaliacaoCurso"
		, joinColumns={
			@JoinColumn(name="idAvaliacao")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idCurso")
			}
		)
	private List<Curso> cursos;

	public Avaliacao() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDtIniAvaliacao() {
		return this.dtIniAvaliacao;
	}

	public void setDtIniAvaliacao(Date dtIniAvaliacao) {
		this.dtIniAvaliacao = dtIniAvaliacao;
	}

	public Date getDtFimAvaliacao() {
		return this.dtFimAvaliacao;
	}

	public void setDtFimAvaliacao(Date dtFimAvaliacao) {
		this.dtFimAvaliacao = dtFimAvaliacao;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public BigDecimal getVlAvaliacao() {
		return this.vlAvaliacao;
	}

	public void setVlAvaliacao(BigDecimal vlAvaliacao) {
		this.vlAvaliacao = vlAvaliacao;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
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
		Avaliacao other = (Avaliacao) obj;
		if (id != other.id)
			return false;
		return true;
	}
}