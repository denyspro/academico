package br.com.edipo.ada.entity;

/**
 * POJO usado para receber os resultados calculados.
 * 
 * @author Denys
 */
public class Resultado {

	private String dsEtiqueta;

	private Double vlCalculado;

	public Resultado (String dsEtiqueta, Double vlCalculado) {
		setDsEtiqueta(dsEtiqueta);
		setVlCalculado(vlCalculado);
	}

	public String getDsEtiqueta() {
		return dsEtiqueta;
	}

	public void setDsEtiqueta(String dsEtiqueta) {
		this.dsEtiqueta = dsEtiqueta;
	}

	public Double getVlCalculado() {
		return vlCalculado;
	}

	public void setVlCalculado(Double vlCalculado) {
		this.vlCalculado = vlCalculado;
	}

}
