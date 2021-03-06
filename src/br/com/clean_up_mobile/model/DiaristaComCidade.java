package br.com.clean_up_mobile.model;

import java.io.Serializable;
import java.util.List;

public class DiaristaComCidade implements Serializable{

	private Integer codigo;
	private String nome;
	private String fotoUsuario;
	private String telefone;
	private Cidade cidade;
	private List<Especialidade> especialidades;
	public boolean favorito;
	public Double mediaDiarista;

	public DiaristaComCidade() {
	}
	
	public DiaristaComCidade(Integer codigo, String nome, String telefone,
			Cidade cidade, List<Especialidade> especialidades, String fotoUsuario) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.telefone = telefone;
		this.cidade = cidade;
		this.especialidades = especialidades;
		this.fotoUsuario = fotoUsuario;
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
	
	public Double getMediaDiarista() {
		return mediaDiarista;
	}

	public void setMediaDiarista(Double mediaDiarista) {
		this.mediaDiarista = mediaDiarista;
	}

	public String getFotoUsuario() {
		return fotoUsuario;
	}

	public void setFotoUsuario(String fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}
}