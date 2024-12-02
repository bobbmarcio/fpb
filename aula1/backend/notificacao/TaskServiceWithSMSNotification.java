package br.ufg.inf.backend.notificacao;

public class TaskServiceWithSMSNotification extends TaskService{

	public TaskServiceWithSMSNotification(NotificacaoService notificacaoService) {
		super(notificacaoService);
	}

}
