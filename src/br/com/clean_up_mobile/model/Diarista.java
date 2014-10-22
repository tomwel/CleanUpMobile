package br.com.clean_up_mobile.model;

import java.io.Serializable;
import java.util.List;

public class Diarista extends Pessoa implements Serializable{
	private double valor;
	private Cidade cidade;
	private List<Especialidade> especialidades;

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

}
