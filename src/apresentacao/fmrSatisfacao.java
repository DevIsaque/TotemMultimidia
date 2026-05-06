package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Tela final de satisfacao em sintonia com o novo visual.
 */
public class fmrSatisfacao extends JDialog {

    private final Controle controle;
    private int notaSelecionada = -1;
    private JLabel[] estrelas;
    private JLabel lblNota;
    private JButton btnEnviar;

    private static final String[] MENSAGENS = {
            "Sem avaliacao registrada.",
            "Que pena. Seu retorno ajuda a melhorar o percurso.",
            "Obrigado pelo retorno. Vamos evoluir a experiencia.",
            "Boa avaliacao. Estamos no caminho certo.",
            "Otimo. Ficamos felizes com a visita.",
            "Excelente. A experiencia marcou voce."
    };

    public fmrSatisfacao(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = EstiloBase.criarPainelFundo(33L);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;

        JLabel lblTag = EstiloBase.criarTag("Encerramento da visita");
        lblTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblTag.setBounds(cx - EstiloBase.escalar(108, tela), EstiloBase.escalar(52, tela),
                EstiloBase.escalar(216, tela), EstiloBase.escalar(34, tela));
        fundo.add(lblTag);

        int tituloW = Math.min(EstiloBase.escalar(900, tela), tela.width - EstiloBase.escalar(80, tela));
        JLabel lblTitulo = new JLabel("<html><div style='text-align:center;width:" + tituloW + "px'>Como foi a sua experiencia no totem?</div></html>");
        lblTitulo.setFont(EstiloBase.fonteResponsiva(54f, tela));
        lblTitulo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setBounds(cx - tituloW / 2, EstiloBase.escalar(110, tela), tituloW, EstiloBase.escalar(120, tela));
        fundo.add(lblTitulo);

        JLabel lblNome = EstiloBase.criarLabel(
                "Obrigado, " + controle.getNomeVisitante() + ". Sua opiniao fecha a jornada e ajuda a melhorar o acervo digital.",
                EstiloBase.fonteResponsiva(22f, tela),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNome.setBounds(EstiloBase.escalar(40, tela), EstiloBase.escalar(240, tela),
                tela.width - EstiloBase.escalar(80, tela), EstiloBase.escalar(34, tela));
        fundo.add(lblNome);

        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        int cardW = Math.min(EstiloBase.escalar(840, tela), tela.width - EstiloBase.escalar(80, tela));
        int cardH = Math.min(EstiloBase.escalar(320, tela), tela.height - EstiloBase.escalar(360, tela));
        cardH = Math.max(EstiloBase.escalar(250, tela), cardH);
        card.setBounds(cx - cardW / 2, EstiloBase.escalar(314, tela), cardW, cardH);
        fundo.add(card);

        JLabel lblCardTag = EstiloBase.criarTag("Avalie de 0 a 5");
        lblCardTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblCardTag.setBounds(EstiloBase.escalar(30, tela), EstiloBase.escalar(28, tela),
                EstiloBase.escalar(132, tela), EstiloBase.escalar(32, tela));
        card.add(lblCardTag);

        JPanel painelEstrelas = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        painelEstrelas.setOpaque(false);
        painelEstrelas.setBounds(EstiloBase.escalar(44, tela), EstiloBase.escalar(92, tela),
                cardW - EstiloBase.escalar(88, tela), EstiloBase.escalar(96, tela));
        card.add(painelEstrelas);

        estrelas = new JLabel[6];
        for (int i = 0; i <= 5; i++) {
            final int nota = i;
            JLabel estrela = new JLabel(i == 0 ? "0" : "★", SwingConstants.CENTER);
            estrela.setFont(i == 0 ? EstiloBase.fonteResponsiva(36f, tela) : EstiloBase.fonteResponsiva(58f, tela));
            estrela.setForeground(EstiloBase.COR_TEXTO_FRACO);
            estrela.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            estrela.setPreferredSize(new Dimension(EstiloBase.escalar(92, tela), EstiloBase.escalar(92, tela)));
            estrela.setToolTipText(i == 0 ? "Sem nota" : nota + " estrela(s)");

            estrela.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selecionarNota(nota);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    destacarAte(nota);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    atualizarEstrelas(notaSelecionada);
                }
            });

            estrelas[i] = estrela;
            painelEstrelas.add(estrela);
        }

        lblNota = EstiloBase.criarLabel(
                "Toque em um valor para avaliar a experiencia",
                EstiloBase.fonteResponsiva(19f, tela),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNota.setBounds(EstiloBase.escalar(30, tela), EstiloBase.escalar(200, tela),
                cardW - EstiloBase.escalar(60, tela), EstiloBase.escalar(26, tela));
        card.add(lblNota);

        int pontos = controle.calcularPontuacao();
        JLabel lblQuiz = EstiloBase.criarLabel(
                "Desempenho no questionario: " + pontos + " de " + controle.getTotalPerguntas() + " acertos",
                EstiloBase.fonteResponsiva(15f, tela),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblQuiz.setBounds(EstiloBase.escalar(30, tela), EstiloBase.escalar(234, tela),
                cardW - EstiloBase.escalar(60, tela), EstiloBase.escalar(20, tela));
        card.add(lblQuiz);

        btnEnviar = EstiloBase.criarBotaoPrimario("Encerrar visita");
        btnEnviar.setEnabled(false);
        btnEnviar.setFont(EstiloBase.fonteResponsiva(18f, tela));
        btnEnviar.setBounds((cardW - EstiloBase.escalar(232, tela)) / 2, cardH - EstiloBase.escalar(58, tela),
                EstiloBase.escalar(232, tela), EstiloBase.escalar(42, tela));
        btnEnviar.addActionListener(e -> enviarAvaliacao());
        card.add(btnEnviar);

        JLabel lblRodape = EstiloBase.criarLabel(
                "Sua resposta fica apenas nesta sessao e orienta melhorias futuras da instalacao.",
                EstiloBase.fonteResponsiva(14f, tela),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblRodape.setBounds(0, tela.height - EstiloBase.escalar(52, tela), tela.width, EstiloBase.escalar(20, tela));
        fundo.add(lblRodape);

        setContentPane(fundo);
    }

    private void selecionarNota(int nota) {
        notaSelecionada = nota;
        atualizarEstrelas(nota);
        lblNota.setText(MENSAGENS[nota]);
        lblNota.setForeground(nota >= 4 ? EstiloBase.COR_SUCESSO
                : nota >= 2 ? EstiloBase.COR_TEXTO_SECUNDARIO : EstiloBase.COR_ERRO);
        btnEnviar.setEnabled(true);
    }

    private void destacarAte(int nota) {
        for (int i = 0; i <= 5; i++) {
            estrelas[i].setForeground(i <= nota && nota > 0
                    ? EstiloBase.COR_DESTAQUE_2
                    : i == 0 && nota == 0
                    ? EstiloBase.COR_DESTAQUE
                    : EstiloBase.COR_TEXTO_FRACO);
        }
    }

    private void atualizarEstrelas(int nota) {
        for (int i = 0; i <= 5; i++) {
            boolean ativo = nota >= 0 && i <= nota;
            if (i == 0 && nota == 0) {
                estrelas[i].setForeground(EstiloBase.COR_DESTAQUE);
            } else {
                estrelas[i].setForeground(ativo && nota > 0 ? EstiloBase.COR_DESTAQUE_2 : EstiloBase.COR_TEXTO_FRACO);
            }
        }
    }

    private void enviarAvaliacao() {
        dispose();
        controle.finalizarVisita(notaSelecionada);
    }
}
