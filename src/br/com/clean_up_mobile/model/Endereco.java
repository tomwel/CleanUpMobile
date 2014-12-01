package br.com.clean_up_mobile.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Endereco implements Serializable{
	private Integer codigo;
	private String logradouro;
	private BigDecimal lat;
	private BigDecimal lng;
	
	public Endereco(){
		
	}
	
	public Endereco(String logradouro){
		this.logradouro = logradouro;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLng() {
		return lng;
	}

	public void setLng(BigDecimal lng) {
		this.lng = lng;
	}	
}
