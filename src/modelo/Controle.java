package modelo;

import apresentacao.*;
import javax.swing.*;

/**
 * Controlador central — interliga frontend e backend,
 * gerencia o fluxo de telas e a passagem de dados.
 */
public class Controle extends absPropriedades {

    private final JFrame framePai;
    private final Validacao validacao;

    public Controle() {
        framePai = new JFrame();
        framePai.setUndecorated(true);
        framePai.setExtendedState(JFrame.MAXIMIZED_BOTH);
        framePai.setVisible(true);
        framePai.toFront();
        framePai.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        validacao = new Validacao();

        // absPropriedades chama Executar() automaticamente no construtor
    }

    // ── Ponto de entrada ───────────────────────────────────────────────────

    @Override
    public void Executar() {
        SwingUtilities.invokeLater(this::exibirTelaInicial);
    }

    // ── Navegação ──────────────────────────────────────────────────────────

    public void exibirTelaInicial() {
        new fmrInicio(framePai, this).setVisible(true);
    }

    public void exibirCadastro() {
        new fmrCadastroVisitante(framePai, this).setVisible(true);
    }

    public void exibirQuestionario() {
        new fmrQuestionario(framePai, this).setVisible(true);
    }

    public void exibirSatisfacao() {
        new fmrSatisfacao(framePai, this).setVisible(true);
    }

    public void exibirObra(int indice) {
        obraAtual = indice;
        new fmrObra(framePai, this, indice).setVisible(true);
    }

    public void exibirMiniGame(int posicao) {
        new fmrMiniGame(framePai, this, posicao).setVisible(true);
    }

    // ── Lógica de fluxo ───────────────────────────────────────────────────

    public void proximaEtapaAposObra(int obraIdx) {
        for (int pos : posicoesMinigame) {
            if (pos == obraIdx) {
                exibirMiniGame(obraIdx);
                return;
            }
        }

        int prox = obraIdx + 1;

        if (prox < titulosObras.length) {
            exibirObra(prox);
        } else {
            exibirQuestionario();
        }
    }

    public void aposMinigame(int obraAnterior) {
        miniGameConcluido = true;

        int prox = obraAnterior + 1;

        if (prox < titulosObras.length) {
            exibirObra(prox);
        } else {
            exibirQuestionario();
        }
    }

    public void aposQuestionario() {
        historicoPontuacoes.add(calcularPontuacao());
        exibirSatisfacao();
    }

    public void finalizarVisita(int notaFinal) {
        registrarSatisfacao(notaFinal);

        historicoNomes.add(nomeVisitante + " " + sobrenomeVisitante);
        historicoFaixasEtarias.add(faixaEtariaVisitante);
        historicoSatisfacoes.add(notaFinal);

        reiniciarSessao();
        exibirTelaInicial();
    }

    private void reiniciarSessao() {
        nomeVisitante        = "";
        sobrenomeVisitante   = "";
        faixaEtariaVisitante = "";

        dadosVisitante[0] = "";
        dadosVisitante[1] = "";
        dadosVisitante[2] = "";

        obraAtual         = 0;
        etapaAtual        = 0;
        notaSatisfacao    = -1;
        miniGameConcluido = false;

        for (int i = 0; i < respostasVisitante.length; i++) {
            respostasVisitante[i] = -1;
        }
    }

    // ── Validação do visitante ─────────────────────────────────────────────

    /*
     * Mantido por causa da interface intMetodos.
     * Neste fluxo novo, o segundo parâmetro representa a faixa etária,
     * não mais a idade exata.
     */
    @Override
    public boolean validarVisitante(String nome, String faixaEtaria) {
        return validacao.validarNome(nome)
                && validacao.validarFaixaEtaria(faixaEtaria);
    }

    public boolean validarVisitante(String nome, String sobrenome, String faixaEtaria) {
        return validacao.validarNome(nome)
                && validacao.validarSobrenome(sobrenome)
                && validacao.validarFaixaEtaria(faixaEtaria);
    }

    public String erroNome(String nome) {
        return validacao.mensagemErroNome(nome);
    }

    public String erroSobrenome(String sobrenome) {
        return validacao.mensagemErroSobrenome(sobrenome);
    }

    public String erroFaixaEtaria(String faixaEtaria) {
        return validacao.mensagemErroFaixaEtaria(faixaEtaria);
    }

    public boolean salvarDadosVisitante(String nome, String sobrenome, String faixaEtaria) {
        if (!validarVisitante(nome, sobrenome, faixaEtaria)) {
            return false;
        }

        this.nomeVisitante = validacao.sanitizarNome(nome);
        this.sobrenomeVisitante = validacao.sanitizarNome(sobrenome);
        this.faixaEtariaVisitante = faixaEtaria;

        // Armazenamento dos dados em vetor
        this.dadosVisitante[0] = this.nomeVisitante;
        this.dadosVisitante[1] = this.sobrenomeVisitante;
        this.dadosVisitante[2] = this.faixaEtariaVisitante;

        return true;
    }

    /*
     * Métodos antigos mantidos para evitar erro caso alguma tela antiga ainda chame.
     * O sistema novo não usa idade exata.
     */
    @Deprecated
    public String erroIdade(String idade) {
        return "A idade exata não é mais solicitada. Selecione uma faixa etária.";
    }

    @Deprecated
    public boolean salvarDadosVisitante(String nomeCompleto, String idadeTexto) {
        return false;
    }

    // ── Getters auxiliares gerais ──────────────────────────────────────────

    public JFrame getFramePai() {
        return framePai;
    }

    // ── Getters das obras ──────────────────────────────────────────────────

    public String getTituloObra(int i) {
        return titulosObras[i];
    }

    public String getDescricaoObra(int i) {
        return descricoesObras[i];
    }

    public String getImagemObra(int i) {
        return imagensObras[i];
    }

    public String getAnoObra(int i) {
        return anosObras[i];
    }

    public String getCodigoObra(int i) {
        return codigosObras[i];
    }

    public boolean deveExibirModelo3D(int i) {
        return exibirModelo3D[i];
    }

    public int getTotalObras() {
        return titulosObras.length;
    }

    // ── Getters do questionário ────────────────────────────────────────────

    public String getPergunta(int i) {
        return perguntas[i];
    }

    public String[] getOpcoesPergunta(int i) {
        return opcoes[i];
    }

    public int getTotalPerguntas() {
        return perguntas.length;
    }

    // ── Getters dos dados do visitante ─────────────────────────────────────

    public String getNomeVisitanteAtual() {
        return nomeVisitante;
    }

    public String getSobrenomeVisitanteAtual() {
        return sobrenomeVisitante;
    }

    public String getNomeCompletoVisitanteAtual() {
        return nomeVisitante + " " + sobrenomeVisitante;
    }

    public String getFaixaEtariaVisitanteAtual() {
        return faixaEtariaVisitante;
    }

    public String[] getDadosVisitanteAtual() {
        return dadosVisitante;
    }
}