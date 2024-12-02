package br.ufg.inf.backend.notificacao;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tarefas")
public class TaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private TaskService taskService;

    @Override
    public void init() throws ServletException {
        // Configura o serviço padrão de notificação
        this.taskService = new TaskService(new DefaultNotificacaoService());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        try {
            resp.getWriter().println(taskService.listar());
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Erro ao listar tarefas: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        taskService = new TaskServiceWithSMSNotification(new SMSNotificacaoService());

        String taskName = getParameter(req, "task");
        String priority = getParameter(req, "priority");

        try {
            String result = taskService.adicionar(taskName, priority);
            resp.setContentType("text/plain");
            resp.getWriter().println(result);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Erro ao adicionar tarefa: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        taskService = new TaskServiceWithEmailNotification(new EmailNotificacaoService());

        String taskName = getParameter(req, "task");
        String priority = getParameter(req, "priority");
        int index;

        try {
            index = Integer.parseInt(getParameter(req, "index"));
            String result = taskService.atualizar(taskName, priority, index);
            resp.setContentType("text/plain");
            resp.getWriter().println(result);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().println("Índice inválido: deve ser um número inteiro.");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Erro ao atualizar tarefa: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String taskName = getParameter(req, "task");

        try {
            String result = taskService.remover(taskName);
            resp.setContentType("text/plain");
            resp.getWriter().println(result);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().println("Erro ao remover tarefa: " + e.getMessage());
        }
    }

    private String getParameter(HttpServletRequest req, String paramName) throws ServletException {
        String param = req.getParameter(paramName);
        if (param == null || param.trim().isEmpty()) {
            throw new ServletException("Parâmetro obrigatório ausente: " + paramName);
        }
        return param.trim();
    }
}
