package br.com.edipo.ada.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	@GeneratedValue
	private int idUsuario;
	private String dsIdentificador;
	private String dsSenha;
	private String dsSal;
	private String dsNome;
	private String dtNascimento;

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getDsIdentificador() {
		return dsIdentificador;
	}

	public void setDsIdentificador(String dsIdentificador) {
		this.dsIdentificador = dsIdentificador;
	}

	public String getDsSenha() {
		return dsSenha;
	}

	public void setDsSenha(String dsSenha) {
		this.dsSenha = dsSenha;
	}

	public String getDsSal() {
		return dsSal;
	}

	public void setDsSal(String dsSal) {
		this.dsSal = dsSal;
	}

	public String getDsNome() {
		return dsNome;
	}

	public void setDsNome(String dsNome) {
		this.dsNome = dsNome;
	}

	public String getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
}
