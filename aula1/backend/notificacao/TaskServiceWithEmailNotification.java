package br.ufg.inf.backend.notificacao;

public class TaskServiceWithEmailNotification extends TaskService{

	public TaskServiceWithEmailNotification(NotificacaoService notificacaoService) {
		super(notificacaoService);
	}

}
