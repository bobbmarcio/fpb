package br.ufg.inf.backend.tasks.model;


public class Task {
	private String taskName;
	private TaskPriority taskPriority;
	
	
	public Task(String taskName, TaskPriority taskPriority) {
		this.taskName = taskName;
		this.taskPriority = taskPriority;
	}
	
	
	public String getTaskName() {
		return this.taskName;
	}
	
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	public TaskPriority getTaskPriority() {
		return this.taskPriority;
	}
	
	public void setTaskPriority(TaskPriority taskPriority) {
		this.taskPriority = taskPriority;
	}
	
}
