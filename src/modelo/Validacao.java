package modelo;

/**
 * Centraliza todas as regras de validação do sistema.
 *
 * Melhoria implementada:
 * - Nome com validação.
 * - Sobrenome com validação.
 * - Faixa etária no lugar de idade exata.
 * - Adequação à LGPD: coleta mínima de dados.
 */
public class Validacao {

    private static final int NOME_MIN = 2;
    private static final int NOME_MAX = 60;

    public static final String FAIXA_10_20 = "10 anos a 20 anos";
    public static final String FAIXA_20_30 = "20 anos a 30 anos";
    public static final String FAIXA_40_50 = "40 anos a 50 anos";

    private static final String[] FAIXAS_ETARIAS = {
            FAIXA_10_20,
            FAIXA_20_30,
            FAIXA_40_50
    };

    // ── Nome ───────────────────────────────────────────────────────────────

    public boolean validarNome(String nome) {
        if (nome == null || nome.isBlank()) return false;

        String t = nome.trim();

        if (t.length() < NOME_MIN || t.length() > NOME_MAX) return false;

        return t.matches("[\\p{L} ]+");
    }

    public String mensagemErroNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return "O nome não pode estar vazio.";
        }

        String t = nome.trim();

        if (t.length() < NOME_MIN) {
            return "O nome deve ter pelo menos " + NOME_MIN + " letras.";
        }

        if (t.length() > NOME_MAX) {
            return "O nome não pode ultrapassar " + NOME_MAX + " caracteres.";
        }

        if (!t.matches("[\\p{L} ]+")) {
            return "Use apenas letras e espaços.";
        }

        return "";
    }

    // ── Sobrenome ──────────────────────────────────────────────────────────

    public boolean validarSobrenome(String sobrenome) {
        if (sobrenome == null || sobrenome.isBlank()) return false;

        String t = sobrenome.trim();

        if (t.length() < NOME_MIN || t.length() > NOME_MAX) return false;

        return t.matches("[\\p{L} ]+");
    }

    public String mensagemErroSobrenome(String sobrenome) {
        if (sobrenome == null || sobrenome.isBlank()) {
            return "O sobrenome não pode estar vazio.";
        }

        String t = sobrenome.trim();

        if (t.length() < NOME_MIN) {
            return "O sobrenome deve ter pelo menos " + NOME_MIN + " letras.";
        }

        if (t.length() > NOME_MAX) {
            return "O sobrenome não pode ultrapassar " + NOME_MAX + " caracteres.";
        }

        if (!t.matches("[\\p{L} ]+")) {
            return "Use apenas letras e espaços.";
        }

        return "";
    }

    // ── Faixa etária ───────────────────────────────────────────────────────

    public boolean validarFaixaEtaria(String faixaEtaria) {
        if (faixaEtaria == null || faixaEtaria.isBlank()) return false;

        for (String faixa : FAIXAS_ETARIAS) {
            if (faixa.equals(faixaEtaria)) {
                return true;
            }
        }

        return false;
    }

    public String mensagemErroFaixaEtaria(String faixaEtaria) {
        if (faixaEtaria == null || faixaEtaria.isBlank()) {
            return "Selecione uma faixa etária.";
        }

        if (!validarFaixaEtaria(faixaEtaria)) {
            return "Selecione uma faixa etária válida.";
        }

        return "";
    }

    public String[] getFaixasEtarias() {
        return FAIXAS_ETARIAS.clone();
    }

    // ── Questionário ───────────────────────────────────────────────────────

    public boolean validarQuestionarioCompleto(int[] respostas) {
        if (respostas == null) return false;

        for (int r : respostas) {
            if (r < 0) return false;
        }

        return true;
    }

    public boolean validarResposta(int opcao, int totalOpcoes) {
        return opcao >= 0 && opcao < totalOpcoes;
    }

    // ── Satisfação ─────────────────────────────────────────────────────────

    public boolean validarSatisfacao(int estrelas) {
        return estrelas >= 0 && estrelas <= 5;
    }

    // ── Sanitização ────────────────────────────────────────────────────────

    public String sanitizarNome(String nome) {
        if (nome == null) return "";

        return nome.trim()
                .replaceAll("[^\\p{L} ]", "")
                .replaceAll("\\s{2,}", " ");
    }
}