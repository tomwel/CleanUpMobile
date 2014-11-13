package br.com.clean_up_mobile.util;

public class Constantes {
	// WebService
	public static final String IP_SERVIDOR = "10.1.2.7";
	public static final String POST_LOGIN = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/login/mobile";
	public static final String POST_CADASTRO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/public/cadastro/mobile/add";
	public static final String GET_DIARISTAS = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/cliente/listDiaristas";
	public static final String CONTRATAR_SERVICO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/servico/add";
	public static final String POST_CONFIRMASERVICO = "http://" + IP_SERVIDOR
            + ":8080/cleanUp/mobile/servico/confirmar";
    public static final String POST_CANCELASERVICO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/servico/cancelar";
    public static final String POST_CLASSIFICASERVICO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/servico/classifica";
	public static final String GET_SERVICO = "http://" + IP_SERVIDOR
			+ ":8080/cleanUp/mobile/servico";
    
    
  //Banco
  	public static final String TABELA_CIDADE = "cidade";
  	public static final String TABELA_ESPECIALIDADE = "especialidade";
}