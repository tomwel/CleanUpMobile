package br.com.clean_up_mobile.model;

import java.io.Serializable;
import java.util.Date;

public class Servico implements Serializable{

	private Integer codServico;
	private TipoServico tipoServico;
	private String descricao;
	private Cliente cliente;
	private Diarista diarista;
	private Endereco endereco;
	private Date dataServico;
	private double valor;
	private StatusServico status;
	private Notificacao notificacao;
	
	public  Servico() {
		this.diarista = new Diarista();
		this.cliente = new Cliente();
		this.endereco = new Endereco();
		this.notificacao = new Notificacao();
	}
	
	public Servico(Integer codServico, TipoServico tipoServico,
			String descricao, Cliente codCliente, Diarista codDiarista,
			Date dataServico, float valor) {
		super();
		this.codServico = codServico;
		this.tipoServico = tipoServico;
		this.descricao = descricao;
		this.cliente = codCliente;
		this.diarista = codDiarista;
		this.dataServico = dataServico;
		this.valor = valor;
	}

	public Integer getCodServico() {
		return codServico;
	}

	public void setCodServico(Integer codServico) {
		this.codServico = codServico;
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public StatusServico getStatus() {
		return status;
	}

	public void setStatus(StatusServico status) {
		this.status = status;
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}
	
}
