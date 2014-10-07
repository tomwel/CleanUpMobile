package br.com.clean_up_mobile.model;

import java.io.Serializable;
import java.util.List;

public class Diarista extends Pessoa implements Serializable{
	private double valor;
	private List<Especialidade> especialidade;

	public List<Especialidade> getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(List<Especialidade> especialidade) {
		this.especialidade = especialidade;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}
