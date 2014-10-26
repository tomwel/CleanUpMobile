package br.com.clean_up_mobile.model;

import java.io.Serializable;
import java.util.Date;

public class Servico implements Serializable{

	private Integer codServico;
	private String tipoServico;
	private String descricao;
	private Cliente cliente;
	private Diarista diarista;
	private Endereco endereco;
	private Date dataServico;
	private double valor;
	private String status;
	
	public  Servico() {
		this.diarista = new Diarista();
		this.cliente = new Cliente();
		this.endereco = new Endereco();
	}
	
	public Servico(Integer codServico, String tipoServico, String descricao,
			Cliente cliente, Diarista diarista, Endereco endereco,
			Date dataServico, double valor, String status) {
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

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Date getDataServico() {
		return dataServico;
	}

	public void setDataServico(Date dataServico) {
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