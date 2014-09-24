package br.com.clean_up_mobile.model;

public class Usuario {
	private int id;
	private String email;
	private String apelido;
	private boolean ativo;
	private String senha;
	private String perfil;
	
	public Usuario(int id, String email, String apelido, boolean ativo, String senha,
			String perfil) {
		super();
		this.id = id;
		this.email = email;
		this.apelido = apelido;
		this.ativo = ativo;
		this.senha = senha;
		this.perfil = perfil;
	}

	public Usuario(){
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public String getApelido() {
		return apelido;
	}
	
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
}
