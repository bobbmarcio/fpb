package br.ufg.inf.backend.notificacao;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.backend.tasks.model.Task;
import br.ufg.inf.backend.tasks.model.TaskPriority;

public class TaskService {

    private final List<Task> tasksList = new ArrayList<>();
    private final NotificacaoService notificacaoService;

    public TaskService(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    public String addTask(String taskName, String priority) {
        Task newTask = new Task(taskName, parsePriority(priority));

        if (tasksList.stream().anyMatch(task -> task.getTaskName().equals(newTask.getTaskName()))) {
            return "Esta tarefa já existe!";
        }

        tasksList.add(newTask);
        notificacaoService.enviarNotificacao("adicionar");
        return "Tarefa adicionada com sucesso!";
    }

    public String updateTask(String taskName, String priority, int index) {
        if (index < 1 || index > tasksList.size()) {
            return String.format("Tarefa não encontrada. Sua lista possui apenas %d tarefa(s).", tasksList.size());
        }

        Task task = tasksList.get(index - 1);
        task.setTaskName(taskName);
        task.setTaskPriority(parsePriority(priority));
        notificacaoService.enviarNotificacao("atualizar");
        return "Tarefa atualizada com sucesso!";
    }

    public String removeTask(String taskName) {
        if (tasksList.isEmpty()) {
            return "Sua lista de tarefas está vazia!";
        }

        Task taskToRemove = tasksList.stream()
                .filter(task -> task.getTaskName().equals(taskName))
                .findFirst()
                .orElse(null);

        if (taskToRemove == null) {
            return "Você não possui essa tarefa cadastrada na sua lista!";
        }

        tasksList.remove(taskToRemove);
        return String.format("Tarefa %s removida com sucesso!", taskName);
    }

    public String listTasks() {
        if (tasksList.isEmpty()) {
            return "Você não possui tarefas!";
        }

        StringBuilder tasksString = new StringBuilder();
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);
            tasksString.append(i + 1)
                    .append(": ")
                    .append(task.getTaskName())
                    .append(" - Prioridade: ")
                    .append(task.getTaskPriority())
                    .append("\n");
        }
        return tasksString.toString();
    }

    private TaskPriority parsePriority(String priority) {
        try {
            return TaskPriority.getPriority(priority);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Prioridade inválida! Informe: alta, média ou baixa.");
        }
    }
}
