package br.ufg.inf.backend.tasks.model;

public enum TaskPriority {
	ALTA, MEDIA, BAIXA;

	public static TaskPriority getPriority(String priority) {
		return TaskPriority.valueOf(priority.toUpperCase());
	}
}
