package br.ufg.inf.backend.notificacao;

import java.util.Map;

public final class Utils {

    private static final Map<String, String> OPERACOES = Map.of(
        "adicionar", "adicionamento",
        "atualizar", "atualizacao"
    );

    public static String getTipoOperacao(String operacao) {
        String tipo = OPERACOES.get(operacao);
        if (tipo == null) {
            throw new IllegalArgumentException("Operação inválida: " + operacao);
        }
        return tipo;
    }
}
