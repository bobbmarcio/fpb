package br.ufg.inf.backend.calculator;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String PARAM_NUM1 = "num1";
    private static final String PARAM_NUM2 = "num2";
    private static final String PARAM_OPERATOR = "operator";

    private static final String ERROR_INVALID_NUMBER = "Um dos valores informados não é válido.";
    private static final String ERROR_INVALID_OPERATOR = "Operação inválida! Use: adicao, subtracao, multiplicacao ou divisao.";
    private static final String RESULT_TEMPLATE = "O total da operação %s é: %.2f";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String num1 = request.getParameter(PARAM_NUM1);
        String num2 = request.getParameter(PARAM_NUM2);
        String operator = request.getParameter(PARAM_OPERATOR);

        try {
            // Validação de números
            Double number1 = parseDouble(num1, "número 1", response);
            Double number2 = parseDouble(num2, "número 2", response);

            if (number1 == null || number2 == null) {
                return; // Resposta já enviada no caso de erro
            }

            // Executar operação
            Double total = calculate(number1, number2, operator, response);
            if (total == null) {
                return; // Resposta já enviada no caso de erro
            }

            // Resposta bem-sucedida
            response.getWriter().append(String.format(RESULT_TEMPLATE, operator, total));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().append("Erro inesperado: ").append(e.getMessage());
        }
    }

    private Double parseDouble(String value, String fieldName, HttpServletResponse response) throws IOException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().append("O valor para ").append(fieldName).append(" é inválido.");
            return null;
        }
    }

    private Double calculate(Double number1, Double number2, String operator, HttpServletResponse response)
            throws IOException {
        switch (operator) {
            case "adicao":
                return number1 + number2;
            case "subtracao":
                return number1 - number2;
            case "multiplicacao":
                return number1 * number2;
            case "divisao":
                if (number2 == 0) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().append("Divisão por zero não é permitida.");
                    return null;
                }
                return number1 / number2;
            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().append(ERROR_INVALID_OPERATOR);
                return null;
        }
    }
}
