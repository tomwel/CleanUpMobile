package br.com.clean_up_mobile.vo;

import java.util.ArrayList;

public class PessoaVO {

	private String nome;
	private ArrayList<Integer> especialidades;
	private String cpf;
	private String telefone;
	private String endereco;
	private int cidade;
	private String email;
	private String senha;
	private int tipo;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<Integer> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(ArrayList<Integer> especialidades) {
		this.especialidades = especialidades;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public int getCidade() {
		return cidade;
	}
	public void setCidade(int cidade) {
		this.cidade = cidade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}	
	
}
