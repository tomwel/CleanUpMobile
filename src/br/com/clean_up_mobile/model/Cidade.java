package br.com.clean_up_mobile.model;

import java.io.Serializable;

public class Cidade implements Serializable{
	
	@Override
	public String toString() {
		return nomeCidade;
	}
	private int codigoCidade;
	private String nomeCidade;
	
	public int getCodigoCidade() {
		return codigoCidade;
	}
	public void setCodigoCidade(int codigoCidade) {
		this.codigoCidade = codigoCidade;
	}
	public String getNomeCidade() {
		return nomeCidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}	

}
