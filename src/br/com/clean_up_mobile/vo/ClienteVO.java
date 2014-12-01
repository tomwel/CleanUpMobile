package br.com.clean_up_mobile.vo;

import java.io.Serializable;
import br.com.clean_up_mobile.model.Cidade;

public class ClienteVO implements Serializable{
	private Integer codigo;
	private String fotoUsuario;
	private String nome;
	private String cpf;
	private String telefone;
	private Cidade cidade;
	private String senha;
	private String email;
	private Integer codUsuario;
	
	public ClienteVO(){
		
	}
	
	public ClienteVO(Integer codigo, String fotoUsuario, String nome,
			String cpf, String telefone, Cidade cidade, String senha,
			String email, Integer codUsuario) {
		super();
		this.codigo = codigo;
		this.fotoUsuario = fotoUsuario;
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.cidade = cidade;
		this.senha = senha;
		this.email = email;
		this.codUsuario = codUsuario;
	}
	
	
	public String getFotoUsuario() {
		return fotoUsuario;
	}
	public void setFotoUsuario(String fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
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
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getCodUsuario() {
		return codUsuario;
	}
	public void setCodUsuario(Integer codUsuario) {
		this.codUsuario = codUsuario;
	}	
}
