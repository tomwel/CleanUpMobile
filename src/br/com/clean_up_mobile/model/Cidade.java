package br.com.clean_up_mobile.model;

import java.io.Serializable;

public class Cidade implements Serializable{
	
	private int codigoCidade;
	private String cidade;
	
	public int getCodigoCidade() {
		return codigoCidade;
	}
	public void setCodigoCidade(int codigoCidade) {
		this.codigoCidade = codigoCidade;
	}
	public String getNomeCidade() {
		return cidade;
	}
	public void setNomeCidade(String nomeCidade) {
		this.cidade = nomeCidade;
	}	

}
