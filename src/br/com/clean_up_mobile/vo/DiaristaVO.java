package br.com.clean_up_mobile.vo;

import java.io.Serializable;
import java.util.List;

import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.model.Especialidade;

public class DiaristaVO implements Serializable{

	private Integer codigo;
	private String nome;
	private String telefone;
	private Cidade cidade;
	private List<Especialidade> especialidades;
	
	public DiaristaVO(){
//		cidade = new Cidade();
//	    especialidades = new ArrayList
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
