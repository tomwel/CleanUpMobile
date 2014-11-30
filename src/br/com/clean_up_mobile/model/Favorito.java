package br.com.clean_up_mobile.model;

public class Favorito {

	private Integer codigo;
	private Cliente cliente;
	private Diarista diarista;
	
	public Favorito(){
		this.cliente = new Cliente();
		this.diarista = new Diarista(); 
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
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
}

