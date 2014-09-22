package br.com.clean_up_mobile.model;

import java.util.List;

public class Especialidade {
	private Integer codigoEspecialidade;
	private String nomeEspecialidade;
	private List<Usuario> especialidade;
	
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
	public List<Usuario> getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(List<Usuario> especialidade) {
		this.especialidade = especialidade;
	}

}
