package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsuario;

	private String dsIdentificador;

	private String dsNome;

	private String dsSal;

	private String dsSenha;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtNascimento;

	public Usuario() {
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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

	public String getDsSal() {
		return this.dsSal;
	}

	public void setDsSal(String dsSal) {
		this.dsSal = dsSal;
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

	@Override
	public int hashCode() {
		return getIdUsuario();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuario) {
			Usuario usuario = (Usuario) obj;
			return usuario.getIdUsuario() == getIdUsuario();
		}
		return false;
	}
}
