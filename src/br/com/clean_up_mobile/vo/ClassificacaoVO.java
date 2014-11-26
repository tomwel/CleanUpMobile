package br.com.clean_up_mobile.vo;

import br.com.clean_up_mobile.model.Servico;

public class ClassificacaoVO {
	private int pontuacao;
	private String comentario;
	private Servico servico;
		
	public Servico getServico() {
		return servico;
	}
	public void setServico(Servico servico) {
		this.servico = servico;
	}
	public int getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}	
}
