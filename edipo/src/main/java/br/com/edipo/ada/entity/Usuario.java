package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Usuario database table.
 * 
 */
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

	//bi-directional many-to-many association to Perfil
	@ManyToMany
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

	public List<Perfil> getPerfis() {
		return this.perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

}