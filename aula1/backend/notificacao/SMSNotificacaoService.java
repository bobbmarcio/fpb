package br.ufg.inf.backend.notificacao;

public class SMSNotificacaoService implements NotificacaoService {

	@Override
	public String enviarNotificacao(String operacao) {
		try {
			return "Notificacao de " + Utils.tipoOperacao(operacao) + " de tarefa enviada por SMS";
		} catch (IllegalArgumentException e) {
			return "Operacao inavlida";
		}
	}
}
