package br.com.clean_up_mobile.model;

import java.io.Serializable;

public class Servico implements Serializable  {

	private Integer codServico;
	private String tipoServico;
	private String descricao;
	private Cliente cliente;
	private DiaristaServico diarista;
	private Endereco endereco;
	private long dataServico;
	private double valor;
	private String status;
	public  Servico() {
		this.diarista = new DiaristaServico();
		this.cliente = new Cliente();
	}
	
	public Servico(Integer codServico, String tipoServico, String descricao,
			Cliente cliente, DiaristaServico diarista, Endereco endereco,
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

	public DiaristaServico getDiarista() {
		return diarista;
	}

	public void setDiarista(DiaristaServico diarista) {
		this.diarista = diarista;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public long getDataServico() {
		return dataServico;
	}

	public void setDataServico(long dataServico) {
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
}