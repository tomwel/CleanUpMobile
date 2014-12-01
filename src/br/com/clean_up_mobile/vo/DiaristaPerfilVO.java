package br.com.clean_up_mobile.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.clean_up_mobile.model.Cidade;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Especialidade;
import br.com.clean_up_mobile.model.Usuario;

public class DiaristaPerfilVO implements Serializable{
	private Integer codigo;
	private String nome;
	private String fotoUsuario;
	private String cpf;
	private String telefone;
	private Endereco endereco;
	private Cidade cidade;
	private Usuario usuario;
	private List<Especialidade> especialidades;
	private ArrayList<Integer> newEspecialidade;
	
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
	public String getFotoUsuario() {
		return fotoUsuario;
	}
	public void setFotoUsuario(String fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
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
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}
	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}
	public ArrayList<Integer> getNewEspecialidade() {
		return newEspecialidade;
	}
	public void setNewEspecialidade(ArrayList<Integer> newEspecialidade) {
		this.newEspecialidade = newEspecialidade;
	}	
}
