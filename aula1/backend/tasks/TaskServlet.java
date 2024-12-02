package br.ufg.inf.backend.tasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.backend.tasks.model.Task;
import br.ufg.inf.backend.tasks.model.TaskPriority;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/task")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final List<Task> tasksList;

    public TaskServlet() {
        this.tasksList = new ArrayList<>();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (tasksList.isEmpty()) {
            respond(resp, "Sua lista de tarefas está vazia!");
            return;
        }
        StringBuilder tasksResponse = new StringBuilder();
        for (int i = 0; i < tasksList.size(); i++) {
            Task task = tasksList.get(i);
            tasksResponse.append(i + 1)
                         .append(": ")
                         .append(task.getTaskName())
                         .append(" - Prioridade: ")
                         .append(task.getTaskPriority())
                         .append("\n");
        }
        respond(resp, tasksResponse.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int index;
        try {
            index = Integer.parseInt(req.getParameter("index")) - 1;
        } catch (NumberFormatException e) {
            respond(resp, "Índice inválido! Deve ser um número.");
            return;
        }

        if (!isValidIndex(index, resp)) return;

        String taskName = req.getParameter("task");
        String priority = req.getParameter("priority");
        Task task = tasksList.get(index);
        task.setTaskName(taskName);
        task.setTaskPriority(parsePriority(priority, resp));
        respond(resp, "Tarefa atualizada com sucesso!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskName = req.getParameter("task");
        String priority = req.getParameter("priority");

        if (tasksList.stream().anyMatch(task -> task.getTaskName().equals(taskName))) {
            respond(resp, "Essa tarefa já existe!");
            return;
        }

        tasksList.add(new Task(taskName, parsePriority(priority, resp)));
        respond(resp, String.format("Tarefa '%s' adicionada com sucesso!", taskName));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskName = req.getParameter("task");

        Task taskToRemove = tasksList.stream()
                                     .filter(task -> task.getTaskName().equals(taskName))
                                     .findFirst()
                                     .orElse(null);

        if (taskToRemove == null) {
            respond(resp, "Tarefa não encontrada na lista!");
            return;
        }

        tasksList.remove(taskToRemove);
        respond(resp, String.format("Tarefa '%s' removida com sucesso!", taskName));
    }

    private boolean isValidIndex(int index, HttpServletResponse resp) throws IOException {
        if (index < 0 || index >= tasksList.size()) {
            respond(resp, String.format("Índice inválido! A lista possui %d tarefa(s).", tasksList.size()));
            return false;
        }
        return true;
    }

    private TaskPriority parsePriority(String priority, HttpServletResponse resp) throws IOException {
        try {
            return TaskPriority.getPriority(priority);
        } catch (IllegalArgumentException e) {
            respond(resp, "Prioridade inválida! Informe 'alta', 'média' ou 'baixa'.");
            return null;
        }
    }

    private void respond(HttpServletResponse resp, String message) throws IOException {
        resp.getWriter().println(message);
    }
}
