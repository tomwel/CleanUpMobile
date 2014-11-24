package br.com.clean_up_mobile.model;

import java.io.Serializable;
import java.util.Date;

public class Servico implements Serializable  {

	private Integer codServico;
	private String tipoServico;
	private String descricao;
	private Cliente cliente;
	private Diarista diarista;
	private String endereco;
	private int dataServico;
	private double valor;
	private String status;
	private String comentario;
	private int nota;
	
	public  Servico() {
		this.diarista = new Diarista();
		this.cliente = new Cliente();
	}
	
	public Servico(Integer codServico, String tipoServico, String descricao,
			Cliente cliente, Diarista diarista, String endereco,
			int dataServico, double valor, String status) {
		super();
		this.codServico = codServico;
		this.tipoServico = tipoServico;
		this.descricao = descricao;
		this.cliente = cliente;
		this.diarista = diarista;
		this.endereco = endereco;
		this.dataServico = dataServico;
		this.valor = valor;
		this.status = status;
	}

	public Integer getCodServico() {
		return codServico;
	}

	public void setCodServico(Integer codServico) {
		this.codServico = codServico;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Diarista getDiarista() {
		return diarista;
	}

	public void setDiarista(Diarista diarista) {
		this.diarista = diarista;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public int getDataServico() {
		return dataServico;
	}

	public void setDataServico(int dataServico) {
		this.dataServico = dataServico;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) {
		this.nota = nota;
	}	
}