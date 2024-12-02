package br.ufg.inf.backend.notificacao;

public class EmailNotificacaoService implements NotificacaoService {

	@Override
	public String enviarNotificacao(String operacao) {
		try {
			return "Notificacao de " + Utils.tipoOperacao(operacao) + " de tarefa enviada por email";	
		} catch (IllegalArgumentException e) {
			return "Operacao invalida";
		}
		
	}

}
