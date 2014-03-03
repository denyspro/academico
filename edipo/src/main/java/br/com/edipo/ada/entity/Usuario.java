package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Classe que mapeia a entidade Usuario.
 * 
 * @author Denys
 */
@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idUsuario")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String dsIdentificador;

	private String dsNome;

	private String dsSenha;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtNascimento;

	//Associação bi-direcional N:N com a entidade Perfil
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="UsuarioPerfil"
		, joinColumns={
			@JoinColumn(name="idUsuario")
			}
		, inverseJoinColumns={
			@JoinColumn(name="idPerfil")
			}
		)
	private List<Perfil> perfis;

	public Usuario() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDsIdentificador() {
		return this.dsIdentificador;
	}

	public void setDsIdentificador(String dsIdentificador) {
		this.dsIdentificador = dsIdentificador;
	}

	public String getDsNome() {
		return this.dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDsSenha() {
		return this.dsSenha;
	}

	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}

	public Date getDtNascimento() {
		return this.dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public List<Perfil> getPerfis() {
		return this.perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dsIdentificador == null) ? 0 : dsIdentificador.hashCode());
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
		Usuario other = (Usuario) obj;
		if (dsIdentificador == null) {
			if (other.dsIdentificador != null)
				return false;
		} else if (!dsIdentificador.equals(other.dsIdentificador))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}