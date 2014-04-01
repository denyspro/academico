package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * Classe que mapeia a entidade Perfil.
 * 
 * @author Denys
 */
@Entity
public class Perfil implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idPerfil")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String dsPerfil;

	@ManyToMany(mappedBy="perfis")
	private List<Usuario> usuarios;

	public Perfil() {	
	}

	public int getId() {
		return this.id;
	}

	public void setIdPerfil(int id) {
		this.id = id;
	}

	public String getDsPerfil() {
		return this.dsPerfil;
	}

	public void setDsPerfil(String dsPerfil) {
		this.dsPerfil = dsPerfil;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
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
		Perfil other = (Perfil) obj;
		if (id != other.id)
			return false;
		return true;
	}
}