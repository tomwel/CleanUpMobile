package br.com.clean_up_mobile.model;

import java.util.Date;

public class Notificacao {
	
	private int idNotificacao;
	private Diarista diarista;
	private Cliente cliente;
	private String descricaoNotificacao;
	private Date dataEnvioNotificacao;
	private StatusNotificacao status;
	
	public int getIdNotificacao() {
		return idNotificacao;
	}
	public void setIdNotificacao(int idNotificacao) {
		this.idNotificacao = idNotificacao;
	}
	public Diarista getDiarista() {
		return diarista;
	}
	public void setDiarista(Diarista diarista) {
		this.diarista = diarista;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public String getDescricaoNotificacao() {
		return descricaoNotificacao;
	}
	public void setDescricaoNotificacao(String descricaoNotificacao) {
		this.descricaoNotificacao = descricaoNotificacao;
	}
	public Date getDataEnvioNotificacao() {
		return dataEnvioNotificacao;
	}
	public void setDataEnvioNotificacao(Date dataEnvioNotificacao) {
		this.dataEnvioNotificacao = dataEnvioNotificacao;
	}
	public StatusNotificacao getStatus() {
		return status;
	}
	public void setStatus(StatusNotificacao status) {
		this.status = status;
	}	
}
