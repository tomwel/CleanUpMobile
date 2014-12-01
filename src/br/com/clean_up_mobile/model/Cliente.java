package br.com.clean_up_mobile.model;

import java.io.Serializable;

public class Cliente implements Serializable {

	private Integer codigo;
	private String nome;
	private String telefone;
	private String fotoUsuario;

	public Cliente() {
	}

	public Cliente(Integer codigo, String nome, String telefone) {
		setCodigo(codigo);
		setNome(nome);
		setTelefone(telefone);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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

	public String getFotoUsuario() {
		return fotoUsuario;
	}

	public void setFotoUsuario(String fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}
}