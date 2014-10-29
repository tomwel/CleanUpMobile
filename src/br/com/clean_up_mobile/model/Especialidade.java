package br.com.clean_up_mobile.model;

import java.io.Serializable;
import java.util.List;

public class Especialidade implements Serializable{
	private Integer codigoEspecialidade;
	private String nomeEspecialidade;
	
	public Integer getCodigoEspecialidade() {
		return codigoEspecialidade;
	}
	public void setCodigoEspecialidade(Integer codigoEspecialidade) {
		this.codigoEspecialidade = codigoEspecialidade;
	}
	public String getNomeEspecialidade() {
		return nomeEspecialidade;
	}
	public void setNomeEspecialidade(String nomeEspecialidade) {
		this.nomeEspecialidade = nomeEspecialidade;
	}

}
