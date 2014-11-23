package br.com.clean_up_mobile.model;

import java.io.Serializable;

public class ServicoSimples implements Serializable {

	private Integer codServico;
	private String tipoServico;
	private String descricao;
	private String cliente;
	private String diarista;
	private String endereco;
	private long dataServico;
	private double valor;
	private String status;

	public ServicoSimples(Integer codServico, String tipoServico,
			String descricao, String cliente, String diarista, String endereco,
			long dataServico, double valor, String status) {
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

	public ServicoSimples() {
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

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getDiarista() {
		return diarista;
	}

	public void setDiarista(String diarista) {
		this.diarista = diarista;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
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