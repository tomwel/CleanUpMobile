package br.com.clean_up_mobile.model;

import java.util.List;

public class Diarista extends Pessoa{
	private List<Especialidade> especialidade;

	public List<Especialidade> getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(List<Especialidade> especialidade) {
		this.especialidade = especialidade;
	}
}
