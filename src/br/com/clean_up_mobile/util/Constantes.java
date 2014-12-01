package br.com.clean_up_mobile.util;

public class Constantes {
	// WebService
	public static final String IP_SERVIDOR = "192.168.1.8";
//	public static final String IP_SERVIDOR = "10.1.2.19";
//	public static final String IP_SERVIDOR = "10.1.2.32";
//	public static final String IP_SERVIDOR = "192.168.43.92";
	public static final String POST_LOGIN = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/login/mobile";
	public static final String POST_CADASTRO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/public/cadastro/mobile/add";
	public static final String POST_ATUALIZARPERFILCLIENTE = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/atualizarPerfilCliente";
	public static final String POST_ATUALIZARPERFILDIARISTA = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/atualizarPerfilDiarista";
	public static final String GET_DIARISTAS = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/cliente/listDiaristas";
	public static final String CONTRATAR_SERVICO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/servico/add";
	    public static final String POST_ATUALIZAR = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/servico/atualizacao/";
    public static final String POST_CLASSIFICASERVICO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/servico/avaliarServico";
    public static final String GET_CLIENTELOGADO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/clienteLogado";
    public static final String GET_DIARISTALOGADA = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/diaristaLogada";
	public static final String GET_SERVICO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/servico";
	public static final String GET_CIDADES = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/listar/cidades";
	public static final String GET_ESPECIALIDADES = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/listar/especialidades";	
	public static final String GET_FAVORITOS = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/listar/favoritos";
	public static final String POST_ADDFAVORITO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/listar/addfavorito";
	public static final String POST_REMOVEFAVORITO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/listar/removefavorito";
	
    
    
  //Banco
  	public static final String TABELA_CIDADE = "cidade";
  	public static final String TABELA_ESPECIALIDADE = "especialidade";
}