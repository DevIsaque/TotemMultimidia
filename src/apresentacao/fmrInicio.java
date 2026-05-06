package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Tela inicial do totem com visual de abertura da exposicao.
 */
public class fmrInicio extends JDialog {

    private final Controle controle;
    private float alphaAnima = 0f;
    private Timer timerEntrada;

    public fmrInicio(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
        iniciarAnimacaoEntrada();
    }

    private void construirInterface() {
        JPanel painel = EstiloBase.criarPainelFundo(42L);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        float escala = EstiloBase.escalaTela(tela);

        int margem = Math.max(54, tela.width / 28);
        int colunaEsquerda = (int) (tela.width * 0.52);
        int cardDireitaX = colunaEsquerda + 18;
        int cardDireitaW = tela.width - cardDireitaX - margem;

        JLabel lblTag = EstiloBase.criarTag("Museu multimidia");
        lblTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblTag.setBounds(margem, EstiloBase.escalar(54, tela), EstiloBase.escalar(186, tela), EstiloBase.escalar(34, tela));
        painel.add(lblTag);

        JLabel lblTitulo = new JLabel("Robos Exploradores");
        lblTitulo.setFont(EstiloBase.fontePoppins((tela.width >= 1700 ? 64f : 54f) * escala));
        lblTitulo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setBounds(margem, EstiloBase.escalar(140, tela), colunaEsquerda - margem - 12, EstiloBase.escalar(82, tela));
        painel.add(lblTitulo);

        JLabel lblSub = new JLabel("<html><div style='width:" + (colunaEsquerda - margem - 20) + "px'>"
                + "Uma experiencia imersiva sobre as missoes que transformaram nossa visao de Marte. "
                + "Toque para iniciar a jornada, conhecer as obras e interagir com o acervo digital.</div></html>");
        lblSub.setFont(EstiloBase.fonteResponsiva(21f, tela));
        lblSub.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblSub.setBounds(margem, EstiloBase.escalar(260, tela), colunaEsquerda - margem - 20, EstiloBase.escalar(136, tela));
        painel.add(lblSub);

        JButton btnIniciar = EstiloBase.criarBotaoPrimario("Iniciar experiencia");
        btnIniciar.setFont(EstiloBase.fonteResponsiva(19f, tela));
        btnIniciar.setBounds(margem, EstiloBase.escalar(440, tela), EstiloBase.escalar(290, tela), EstiloBase.escalar(64, tela));
        btnIniciar.addActionListener(e -> {
            dispose();
            controle.exibirCadastro();
        });
        painel.add(btnIniciar);

        JLabel lblLinha = EstiloBase.criarLabel(
                "10 obras  •  linha do tempo completa  •  1 questionario final",
                EstiloBase.fonteResponsiva(15f, tela),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblLinha.setHorizontalAlignment(SwingConstants.LEFT);
        lblLinha.setBounds(margem, EstiloBase.escalar(526, tela), colunaEsquerda - margem, EstiloBase.escalar(24, tela));
        painel.add(lblLinha);

        JPanel cardResumo = EstiloBase.criarCard();
        cardResumo.setLayout(null);
        int cardResumoY = EstiloBase.escalar(86, tela);
        int cardResumoH = tela.height - (cardResumoY * 2);
        cardResumo.setBounds(cardDireitaX, cardResumoY, cardDireitaW, cardResumoH);
        painel.add(cardResumo);

        JPanel poster = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(12, 12, 18));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);

                GradientPaint gp = new GradientPaint(0, 0, new Color(18, 11, 13), getWidth(), getHeight(), new Color(12, 12, 18));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);

                RadialGradientPaint planeta = new RadialGradientPaint(
                        new Point((int) (getWidth() * 0.65), (int) (getHeight() * 0.34)),
                        Math.min(getWidth(), getHeight()) * 0.28f,
                        new float[]{0f, 0.55f, 1f},
                        new Color[]{
                                new Color(255, 115, 54, 240),
                                new Color(255, 191, 58, 190),
                                new Color(255, 191, 58, 0)
                        }
                );
                g2.setPaint(planeta);
                g2.fill(new Ellipse2D.Float((float) (getWidth() * 0.39), (float) (getHeight() * 0.08), getWidth() * 0.52f, getWidth() * 0.52f));

                g2.setColor(new Color(255, 255, 255, 16));
                for (int x = 24; x < getWidth(); x += 24) {
                    g2.drawLine(x, 0, x, getHeight());
                }
                for (int y = 24; y < getHeight(); y += 24) {
                    g2.drawLine(0, y, getWidth(), y);
                }

                g2.setColor(new Color(255, 255, 255, 24));
                g2.drawOval((int) (getWidth() * 0.16), (int) (getHeight() * 0.16), (int) (getWidth() * 0.72), (int) (getHeight() * 0.28));
                g2.drawOval((int) (getWidth() * 0.26), (int) (getHeight() * 0.42), (int) (getWidth() * 0.48), (int) (getHeight() * 0.18));
                g2.dispose();
            }
        };
        poster.setOpaque(false);
        int posterX = EstiloBase.escalar(24, tela);
        int posterH = Math.max(EstiloBase.escalar(250, tela), Math.min((int) (cardResumoH * 0.58), cardResumoH - EstiloBase.escalar(220, tela)));
        poster.setBounds(posterX, posterX, cardDireitaW - (posterX * 2), posterH);
        cardResumo.add(poster);

        JLabel lblResumoTag = EstiloBase.criarTag("Exposicao em destaque");
        lblResumoTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblResumoTag.setBounds(EstiloBase.escalar(34, tela), EstiloBase.escalar(34, tela), EstiloBase.escalar(170, tela), EstiloBase.escalar(32, tela));
        poster.add(lblResumoTag);

        JLabel lblResumoTitulo = new JLabel("<html><div style='width:" + (poster.getWidth() - 82) + "px'>Marte como palco da curiosidade humana</div></html>");
        lblResumoTitulo.setFont(EstiloBase.fonteResponsiva(28f, tela));
        lblResumoTitulo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblResumoTitulo.setBounds(EstiloBase.escalar(34, tela), EstiloBase.escalar(92, tela),
                poster.getWidth() - EstiloBase.escalar(68, tela), EstiloBase.escalar(128, tela));
        poster.add(lblResumoTitulo);

        JLabel lblResumoTexto = new JLabel("<html><div style='width:" + (poster.getWidth() - 70) + "px'>"
                + "Descubra as sondas, rovers e experimentos que redefiniram a exploracao planetaria.</div></html>");
        lblResumoTexto.setFont(EstiloBase.fonteResponsiva(16f, tela));
        lblResumoTexto.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblResumoTexto.setBounds(EstiloBase.escalar(34, tela), poster.getHeight() - EstiloBase.escalar(104, tela),
                poster.getWidth() - EstiloBase.escalar(70, tela), EstiloBase.escalar(72, tela));
        poster.add(lblResumoTexto);

        int infoH = Math.max(EstiloBase.escalar(70, tela),
                (cardResumoH - poster.getY() - poster.getHeight() - EstiloBase.escalar(58, tela)) / 2);
        int infoGap = EstiloBase.escalar(14, tela);
        int infoY1 = poster.getY() + poster.getHeight() + EstiloBase.escalar(18, tela);
        adicionarCartaoInfo(cardResumo, posterX, infoY1, cardDireitaW - (posterX * 2), infoH,
                "Fluxo pensado para toque",
                "Grandes areas clicaveis, leitura forte e navegacao simples para totens.");
        adicionarCartaoInfo(cardResumo, posterX, infoY1 + infoH + infoGap, cardDireitaW - (posterX * 2), infoH,
                "Narrativa continua",
                "Arte, descricao, interacoes e quiz final com o mesmo acabamento visual.");

        JLabel lblRodape = EstiloBase.criarLabel(
                "Use a tela inteira para navegar. Este modo foi redesenhado para parecer uma instalacao de museu, nao um formulario corporativo.",
                EstiloBase.fonteResponsiva(14f, tela),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblRodape.setHorizontalAlignment(SwingConstants.LEFT);
        lblRodape.setBounds(margem, tela.height - EstiloBase.escalar(56, tela), tela.width - (margem * 2), EstiloBase.escalar(20, tela));
        painel.add(lblRodape);

        setContentPane(painel);
    }

    private void adicionarCartaoInfo(JPanel pai, int x, int y, int w, int h, String titulo, String texto) {
        JPanel bloco = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(255, 255, 255, 9));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
            }
        };
        bloco.setOpaque(false);
        bloco.setBounds(x, y, w, h);
        pai.add(bloco);

        JLabel lblTitulo = EstiloBase.criarLabel(titulo, EstiloBase.FONTE_LABEL.deriveFont(16f), EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(20, 16, w - 40, 20);
        bloco.add(lblTitulo);

        JLabel lblTexto = new JLabel("<html><div style='width:" + (w - 40) + "px'>" + texto + "</div></html>");
        lblTexto.setFont(EstiloBase.FONTE_PEQUENA.deriveFont(15f));
        lblTexto.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblTexto.setBounds(20, 42, w - 40, 34);
        bloco.add(lblTexto);
    }

    private void iniciarAnimacaoEntrada() {
        setOpacity(0f);
        timerEntrada = new Timer(20, null);
        timerEntrada.addActionListener(e -> {
            alphaAnima = Math.min(1f, alphaAnima + 0.04f);
            setOpacity(alphaAnima);
            if (alphaAnima >= 1f) {
                timerEntrada.stop();
            }
        });
        timerEntrada.start();
    }

    @Override
    public void dispose() {
        if (timerEntrada != null) {
            timerEntrada.stop();
        }
        super.dispose();
    }
}
