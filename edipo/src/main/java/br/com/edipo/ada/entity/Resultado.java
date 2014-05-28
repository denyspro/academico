package br.com.edipo.ada.entity;

import java.math.BigDecimal;

/**
 * POJO usado para receber os resultados calculados.
 * 
 * @author Denys
 */
public class Resultado {

	private String dsEtiqueta;

	private BigDecimal vlCalculado;

	public Resultado (String dsEtiqueta, BigDecimal vlCalculado) {
		setDsEtiqueta(dsEtiqueta);
		setVlCalculado(vlCalculado);
	}

	public String getDsEtiqueta() {
		return dsEtiqueta;
	}

	public void setDsEtiqueta(String dsEtiqueta) {
		this.dsEtiqueta = dsEtiqueta;
	}

	public BigDecimal getVlCalculado() {
		return vlCalculado;
	}

	public void setVlCalculado(BigDecimal vlCalculado) {
		this.vlCalculado = vlCalculado;
	}

}
