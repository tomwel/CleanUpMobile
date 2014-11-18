package br.com.clean_up_mobile.model;

import java.io.Serializable;

public class Especialidade implements Serializable {
	private Integer codigoEspecialidade;
	private String nomeEspecialidade;

	public Especialidade(Integer codigoEspecialidade, String nomeEspecialidade) {
		super();
		this.codigoEspecialidade = codigoEspecialidade;
		this.nomeEspecialidade = nomeEspecialidade;
	}

	public Especialidade() {
		// TODO Auto-generated constructor stub
	}

	public Integer getCodigoEspecialidade() {
		return codigoEspecialidade;
	}

	@Override
	public String toString() {
		return nomeEspecialidade;
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