package br.com.edipo.ada.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Classe que mapeia a entidade Resolucao.
 * 
 * @author Denys
 */
@Entity
public class Resolucao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idResolucao")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtFimResolucao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dtIniResolucao;

	private BigDecimal vlResultado;

	@OneToMany(mappedBy="resolucao",cascade=CascadeType.ALL)
	private List<Escolha> escolhas;

	@ManyToOne
	@JoinColumn(name="idAvaliacao")
	private Avaliacao avaliacao;

	private int idUsuario;

	public Resolucao() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDtFimResolucao() {
		return this.dtFimResolucao;
	}

	public void setDtFimResolucao(Date dtFimResolucao) {
		this.dtFimResolucao = dtFimResolucao;
	}

	public Date getDtIniResolucao() {
		return this.dtIniResolucao;
	}

	public void setDtIniResolucao(Date dtIniResolucao) {
		this.dtIniResolucao = dtIniResolucao;
	}

	public BigDecimal getVlResultado() {
		return this.vlResultado;
	}

	public void setVlResultado(BigDecimal vlResultado) {
		this.vlResultado = vlResultado;
	}

	public List<Escolha> getEscolhas() {
		return this.escolhas;
	}

	public void setEscolhas(List<Escolha> escolhas) {
		this.escolhas = escolhas;
	}

	public void addEscolha(Escolha escolha) {
		if (this.escolhas == null) {
			this.escolhas = new ArrayList<Escolha>();
		}

		this.escolhas.add(escolha);
		escolha.setResolucao(this);
	}

	public void removeEscolha(Escolha escolha) {
		if (this.escolhas != null) {
			this.escolhas.remove(escolha);
			escolha.setResolucao(null);
		}
	}

	public Avaliacao getAvaliacao() {
		return this.avaliacao;
	}

	public void setAvaliacao(Avaliacao avaliacao) {
		this.avaliacao = avaliacao;
	}

	public int getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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
		Resolucao other = (Resolucao) obj;
		if (id != other.id)
			return false;
		return true;
	}
}