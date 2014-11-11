package br.com.clean_up_mobile.vo;

import java.util.Date;
import java.util.List;
import br.com.clean_up_mobile.model.Diarista;
import br.com.clean_up_mobile.model.Endereco;
import br.com.clean_up_mobile.model.Usuario;

public class ServicoVO {
	
	private String descricao;
	private int codigo;
	private List<Endereco> enderecos;
	private String data;
	private Diarista diarista;
	private Usuario usuario;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Diarista getDiarista() {
		return diarista;
	}
	public void setDiarista(Diarista diarista) {
		this.diarista = diarista;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
