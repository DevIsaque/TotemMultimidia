package apresentacao;

import javax.swing.*;
import java.awt.*;

/**
 * Teclado virtual interno para uso em touchscreen.
 */
public class TecladoVirtual extends JDialog {

    private final JTextField campoAlvo;
    private boolean maiusculas = true;

    private static final String[][] LINHAS = {
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"},
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ç"},
            {"Z", "X", "C", "V", "B", "N", "M", ",", ".", "'"}
    };

    private JPanel painelTeclas;

    public TecladoVirtual(JDialog pai, JTextField campoAlvo) {
        super(pai, "Teclado Virtual", true);
        this.campoAlvo = campoAlvo;
        configurarJanela();
        construirInterface();
    }

    private void configurarJanela() {
        setUndecorated(true);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int largura = Math.min(940, tela.width - 80);
        int altura = Math.min(470, tela.height - 80);
        setSize(largura, altura);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
    }

    private void construirInterface() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int margem = EstiloBase.escalar(20, tela);

        JPanel fundo = new JPanel(new BorderLayout(0, EstiloBase.escalar(14, tela)));
        fundo.setOpaque(false);
        fundo.setBorder(BorderFactory.createEmptyBorder(margem, margem, margem, margem));

        JPanel card = EstiloBase.criarCardDestaque();
        card.setLayout(new BorderLayout(0, EstiloBase.escalar(14, tela)));
        card.setBorder(BorderFactory.createEmptyBorder(
                EstiloBase.escalar(18, tela),
                EstiloBase.escalar(18, tela),
                EstiloBase.escalar(18, tela),
                EstiloBase.escalar(18, tela)
        ));
        fundo.add(card, BorderLayout.CENTER);

        JPanel topo = new JPanel(new BorderLayout(0, 12));
        topo.setOpaque(false);

        JLabel lblTitulo = EstiloBase.criarLabel("Teclado virtual", EstiloBase.fonteResponsiva(24f, tela), EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        topo.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblPreview = new JLabel(" ", SwingConstants.LEFT);
        lblPreview.setFont(EstiloBase.fonteResponsiva(22f, tela));
        lblPreview.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblPreview.setOpaque(true);
        lblPreview.setBackground(new Color(10, 10, 16));
        lblPreview.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 22), 1, true),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        lblPreview.setText(campoAlvo.getText().isBlank() ? " " : campoAlvo.getText());
        topo.add(lblPreview, BorderLayout.CENTER);

        card.add(topo, BorderLayout.NORTH);

        painelTeclas = new JPanel(new GridLayout(4, 10, EstiloBase.escalar(8, tela), EstiloBase.escalar(8, tela)));
        painelTeclas.setOpaque(false);
        construirTeclas(painelTeclas, lblPreview);
        card.add(painelTeclas, BorderLayout.CENTER);

        JPanel barraInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, EstiloBase.escalar(10, tela), 0));
        barraInferior.setOpaque(false);

        JButton btnShift = criarTeclaEspecial("Shift", 118, 58);
        btnShift.addActionListener(e -> {
            maiusculas = !maiusculas;
            btnShift.setText(maiusculas ? "Shift" : "shift");
            construirTeclas(painelTeclas, lblPreview);
            painelTeclas.revalidate();
            painelTeclas.repaint();
        });

        JButton btnEspaco = criarTeclaEspecial("Espaco", 300, 58);
        btnEspaco.addActionListener(e -> {
            campoAlvo.setText(campoAlvo.getText() + " ");
            lblPreview.setText(campoAlvo.getText());
        });

        JButton btnBackspace = criarTeclaEspecial("Apagar", 104, 58);
        btnBackspace.addActionListener(e -> {
            String atual = campoAlvo.getText();
            if (!atual.isEmpty()) {
                campoAlvo.setText(atual.substring(0, atual.length() - 1));
                lblPreview.setText(campoAlvo.getText().isBlank() ? " " : campoAlvo.getText());
            }
        });

        JButton btnLimpar = criarTeclaEspecial("Limpar", 104, 58);
        btnLimpar.addActionListener(e -> {
            campoAlvo.setText("");
            lblPreview.setText(" ");
        });

        JButton btnOK = EstiloBase.criarBotaoPrimario("Confirmar");
        btnOK.setFont(EstiloBase.fonteResponsiva(18f, tela));
        btnOK.setPreferredSize(new Dimension(EstiloBase.escalar(156, tela), EstiloBase.escalar(58, tela)));
        btnOK.addActionListener(e -> dispose());

        barraInferior.add(btnShift);
        barraInferior.add(btnEspaco);
        barraInferior.add(btnBackspace);
        barraInferior.add(btnLimpar);
        barraInferior.add(btnOK);

        card.add(barraInferior, BorderLayout.SOUTH);
        setContentPane(fundo);
        getRootPane().setOpaque(false);
    }

    private void construirTeclas(JPanel painel, JLabel preview) {
        painel.removeAll();
        for (String[] linha : LINHAS) {
            for (String tecla : linha) {
                String label = maiusculas ? tecla : tecla.toLowerCase();
                JButton btn = criarTeclaLetra(label);
                btn.addActionListener(e -> {
                    campoAlvo.setText(campoAlvo.getText() + label);
                    preview.setText(campoAlvo.getText());
                });
                painel.add(btn);
            }
        }
    }

    private JButton criarTeclaLetra(String texto) {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed() ? new Color(255, 115, 54, 180)
                        : getModel().isRollover() ? new Color(255, 255, 255, 16)
                        : new Color(255, 255, 255, 10);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                GradientPaint borda = new GradientPaint(0, 0, EstiloBase.COR_CARD_BORDA,
                        getWidth(), getHeight(), EstiloBase.COR_CARD_GLOW);
                g2.setPaint(borda);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();

                setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
                setFont(EstiloBase.fonteResponsiva(18f, tela));
                super.paintComponent(g);
            }
        };
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(EstiloBase.escalar(78, tela), EstiloBase.escalar(54, tela)));
        return btn;
    }

    private JButton criarTeclaEspecial(String texto, int largura, int altura) {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        JButton btn = EstiloBase.criarBotaoSecundario(texto);
        btn.setFont(EstiloBase.fonteResponsiva(17f, tela));
        btn.setPreferredSize(new Dimension(EstiloBase.escalar(largura, tela), EstiloBase.escalar(altura, tela)));
        return btn;
    }
}
