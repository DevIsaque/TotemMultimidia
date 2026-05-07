package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata base — declara e inicializa TODAS as variáveis do sistema.
 * Chama Executar() automaticamente após a inicialização (padrão Template Method).
 */
public abstract class absPropriedades implements intMetodos {

    // ── Dados do Visitante ─────────────────────────────────────────────────
    protected String nomeVisitante;
    protected String sobrenomeVisitante;
    protected String faixaEtariaVisitante;

    /*
     * Vetor principal exigido pela task:
     * dadosVisitante[0] = Nome
     * dadosVisitante[1] = Sobrenome
     * dadosVisitante[2] = Faixa etária
     */
    protected String[] dadosVisitante;

    // ── Controle de Fluxo ──────────────────────────────────────────────────
    protected int     etapaAtual;
    protected int     obraAtual;
    protected boolean miniGameConcluido;

    // ── Obras / Missões Marcianas ──────────────────────────────────────────
    protected String[]  titulosObras;
    protected String[]  descricoesObras;
    protected String[]  imagensObras;
    protected String[]  anosObras;
    protected String[]  codigosObras;
    protected boolean[] exibirModelo3D;

    // ── Mini-Games ─────────────────────────────────────────────────────────
    protected int[] posicoesMinigame;

    // ── Questionário ───────────────────────────────────────────────────────
    protected String[]   perguntas;
    protected String[][] opcoes;
    protected int[]      gabaritos;
    protected int[]      respostasVisitante;

    // ── Satisfação ─────────────────────────────────────────────────────────
    protected int notaSatisfacao;

    // ── Histórico in-memory ────────────────────────────────────────────────
    protected List<String>  historicoNomes;
    protected List<String>  historicoFaixasEtarias;
    protected List<Integer> historicoPontuacoes;
    protected List<Integer> historicoSatisfacoes;

    // ── Construtor ─────────────────────────────────────────────────────────
    public absPropriedades() {
        inicializarVariaveis();
        Executar();
    }

    // ── Inicialização ──────────────────────────────────────────────────────
    private void inicializarVariaveis() {
        nomeVisitante        = "";
        sobrenomeVisitante   = "";
        faixaEtariaVisitante = "";

        dadosVisitante = new String[3];
        dadosVisitante[0] = "";
        dadosVisitante[1] = "";
        dadosVisitante[2] = "";

        etapaAtual         = 0;
        obraAtual          = 0;
        miniGameConcluido  = false;
        notaSatisfacao     = -1;
        respostasVisitante = new int[]{-1, -1, -1, -1, -1};

        historicoNomes          = new ArrayList<>();
        historicoFaixasEtarias = new ArrayList<>();
        historicoPontuacoes     = new ArrayList<>();
        historicoSatisfacoes    = new ArrayList<>();

        inicializarObras();
        inicializarQuestionario();
        inicializarMinigames();
    }

    private void inicializarObras() {
        titulosObras = new String[]{
                "Obra 1 – PrOP-M (Mars 2, URSS, 1971)",
                "Obra 2 – PrOP-M (Mars 3, URSS, 1971)",
                "Obra 3 – Sojourner (Mars Pathfinder, NASA, 1997)",
                "Obra 4 – Spirit (Mars Exploration Rover A, NASA, 2004)",
                "Obra 5 – Opportunity (Mars Exploration Rover B, NASA, 2004–2018)",
                "Obra 6 – Curiosity (Mars Science Laboratory, NASA, 2012–)",
                "Obra 7 – Perseverance (Mars 2020, NASA, 2021–)",
                "Obra 8 – Ingenuity (helicóptero de Marte, NASA, 2021)",
                "Obra 9 – Zhurong (Tianwen-1, China, 2021)",
                "Obra 10 – Rosalind Franklin (ExoMars, ESA/NASA, lançamento previsto 2028)"
        };

        descricoesObras = new String[]{
                "O PrOP-M foi um pequeno robô soviético desenvolvido para ser o primeiro rover a operar na superfície de Marte, embarcado na missão Mars 2 em 1971. A sonda Mars 2 conseguiu entrar em órbita marciana, mas o módulo de pouso entrou na atmosfera em um ângulo muito íngreme, o sistema de descida falhou e o lander se chocou contra o solo, destruindo todo o equipamento, incluindo o rover PrOP-M, que nunca foi ativado na superfície.\n\n" +
                        "O PrOP-M tinha cerca de 15 kg, formato de caixa metálica montada sobre dois esquis, em vez de rodas, e era preso ao lander por um cabo de aproximadamente 15 m, pensado para limitar a distância e facilitar as comunicações. Por causa do grande atraso de sinal entre Terra e Marte, o rover foi projetado para se movimentar de forma autônoma, usando hastes frontais para detectar obstáculos e mudar de direção sem comando direto humano. Ele carregava sensores como um densitômetro e um penetrômetro para medir densidade e resistência mecânica do solo, fornecendo dados importantes sobre a trafegabilidade marciana, ainda que, na prática, nunca tenha operado devido ao fracasso do pouso.",

                "Um segundo rover idêntico PrOP-M viajou na missão Mars 3, lançada também em 1971, como parte do mesmo programa soviético. Diferentemente da Mars 2, o lander Mars 3 conseguiu realizar o primeiro pouso suave bem-sucedido na história em Marte, em 2 de dezembro de 1971. No entanto, após cerca de 14 a 20 segundos de transmissão de dados, o contato com o módulo de pouso foi perdido, provavelmente devido a uma tempestade de poeira global que envolvia o planeta naquele período.\n\n" +
                        "O plano da missão previa que o PrOP-M descesse por rampas do lander e se movesse em pequenos saltos de aproximadamente 1,5 m, usando suas hastes metálicas para sentir o terreno à frente e evitar obstáculos de forma autônoma. Como a comunicação foi interrompida quase imediatamente após o pouso, o rover provavelmente nunca chegou a deixar a plataforma, transformando-se em um exemplo de tecnologia pronta, mas silenciada pelas condições extremas de Marte.",

                "O Sojourner foi o primeiro rover de fato a operar na superfície de Marte, parte da missão Mars Pathfinder, lançada em dezembro de 1996 e pousada em 4 de julho de 1997 na região de Ares Vallis, em Chryse Planitia. A missão teve grande impacto público e científico, demonstrando o conceito mais rápido, melhor e mais barato da NASA, com um lander experimental e um pequeno veículo móvel realizando ciência de qualidade em Marte.\n\n" +
                        "Com cerca de 11,5 kg, o Sojourner usava painéis solares para gerar aproximadamente 13 W de potência e foi projetado para uma missão primária de 7 dias, com expectativa de até 30 dias, mas acabou operando por cerca de 83 dias de deslocamento, totalizando 92 sols de atividade do conjunto lander + rover. Ele possuía câmeras e o espectrômetro Alpha Proton X-Ray, permitindo analisar a composição química de rochas e solos ao redor do lander.",

                "A Spirit foi uma das duas sondas gêmeas do programa Mars Exploration Rover, também conhecida como MER-A, lançada em 2003 e pousada em janeiro de 2004 na cratera Gusev, local escolhido por indícios de antigos processos ligados à água. A missão primária era de apenas 90 sols, mas a Spirit operou até 2010, totalizando mais de 2200 sols e superando em mais de vinte vezes a vida útil planejada.\n\n" +
                        "Ao longo de aproximadamente 7,7 km percorridos, a Spirit investigou rochas vulcânicas, depósitos alterados por fluidos e materiais ricos em sílica que indicam interação água-rocha, contribuindo para reconstruir a história geológica de Gusev. Em 2009, o rover ficou preso em uma área de solo muito fofo, apelidada de Troy, tornando o episódio de atolamento um símbolo de exploração até o limite.",

                "A Opportunity, ou MER-B, pousou em Marte cerca de três semanas após a Spirit, em janeiro de 2004, na região de Meridiani Planum, uma planície rica em hematita, mineral geralmente associado à presença de água. Assim como sua gêmea, foi projetada para 90 sols, mas permaneceu ativa até junho de 2018, quando uma tempestade global de poeira encobriu o planeta, bloqueando a luz solar e levando à perda definitiva de contato.\n\n" +
                        "Durante quase 15 anos, a Opportunity percorreu mais de 45 km, um recorde para rovers em outro corpo celeste, explorando diversas crateras como Endurance, Victoria e Endeavour. Em Endeavour, encontrou veios de gesso e outras evidências de ambientes de água com pH mais neutro, considerados mais amigáveis à vida do que ambientes extremamente ácidos.",

                "O Curiosity é o rover da missão Mars Science Laboratory, lançado em 2011 e pousado com a complexa manobra de sky crane em agosto de 2012 na cratera Gale. Na época do pouso, era o maior e mais sofisticado rover já enviado a Marte, pesando cerca de 900 kg e equipado com dez instrumentos científicos, incluindo a câmera-laser ChemCam, uma perfuradora de rochas, laboratórios químicos internos e uma estação meteorológica.\n\n" +
                        "O objetivo central da missão é investigar se a região de Gale já ofereceu condições habitáveis para vida microbiana no passado, procurando sinais de água líquida estável, fontes de energia e elementos químicos essenciais. Estudos de rochas sedimentares no leito de antigos lagos e nas encostas do Monte Sharp mostraram camadas de lama solidificada, minerais de argila e sulfatos.",

                "O Perseverance é o rover da missão Mars 2020, lançado em julho de 2020 e pousado na cratera Jezero em 18 de fevereiro de 2021. A cratera foi escolhida por abrigar um antigo delta de rio e um possível lago, considerados um dos locais mais promissores em Marte para a busca de sinais de vida passada.\n\n" +
                        "Entre os objetivos principais do Perseverance estão procurar evidências de vida microbiana antiga, caracterizar a geologia e o clima de Marte e realizar a coleta e o armazenamento de amostras de rocha e solo em tubos metálicos selados, para serem trazidos à Terra por futuras missões de retorno de amostras. O rover possui sete instrumentos científicos principais, 19 câmeras e dois microfones, além do experimento MOXIE.",

                "O Ingenuity é um pequeno helicóptero de aproximadamente 1,8 kg levado preso sob o Perseverance como um demonstrador tecnológico para testar o primeiro voo motorizado controlado em outro planeta. Após ser depositado na superfície marciana, em Jezero, realizou seu primeiro voo em 19 de abril de 2021, subindo cerca de 3 metros, pairando e pousando, marcando um momento histórico na exploração espacial.\n\n" +
                        "Inicialmente planejado para apenas cinco voos em cerca de 30 dias, o Ingenuity continuou operando por quase três anos, realizando 72 voos até janeiro de 2024, atuando como um batedor aéreo para o Perseverance. Seus voos demonstraram que aeronaves leves podem operar na atmosfera rarefeita de Marte.",

                "O Zhurong é o primeiro rover marciano da China e parte da missão Tianwen-1, que inclui orbitador, módulo de descida e rover. Lançada em julho de 2020, a missão entrou em órbita em fevereiro de 2021, e o módulo de descida pousou com sucesso em Utopia Planitia em 14 de maio de 2021, tornando a China o segundo país a operar um rover em Marte.\n\n" +
                        "Com cerca de 240 kg, seis rodas e alimentação por painéis solares, o Zhurong leva câmeras, radar de penetração no solo, espectrômetros e instrumentos para estudar a estrutura do subsolo, a presença de gelo de água, a mineralogia e o ambiente local. O rover foi projetado para 90 sols, mas operou por mais de 347 sols e percorreu quase 2 km até entrar em hibernação em maio de 2022.",

                "A décima obra corresponde ao rover Rosalind Franklin, da missão ExoMars, liderada pela Agência Espacial Europeia com suporte da NASA. Esse rover representa o próximo grande passo da exploração marciana: a busca sistemática por sinais de vida no subsolo de Marte, combinando mobilidade com capacidade avançada de perfuração e análise de amostras.\n\n" +
                        "O Rosalind Franklin tem lançamento previsto para 2028 em um foguete Falcon Heavy, com pouso estimado para 2030 em Oxia Planum, uma região que registra uma longa história de interação com água e depósitos sedimentares antigos. Ele será o primeiro rover em Marte capaz de perfurar até cerca de 2 m de profundidade, coletando amostras protegidas da radiação e da oxidação da superfície."
        };

        imagensObras = new String[]{
                "/assets/obras/obra1-prop-m-mars2.jpeg",
                "/assets/obras/obra2-prop-m-mars3.jpeg",
                "/assets/obras/obra3-sojourner.jpeg",
                "/assets/obras/obra4-spirit.jpeg",
                "/assets/obras/obra5-opportunity.jpeg",
                "/assets/obras/obra6-curiosity.jpeg",
                "/assets/obras/obra7-perseverance.jpeg",
                "/assets/obras/obra8-ingenuity.jpeg",
                "/assets/obras/obra9-zhurong.jpeg",
                "/assets/obras/obra10-rosalind-franklin.jpeg"
        };

        anosObras = new String[]{
                "1971", "1971", "1997", "2004", "2004–2018",
                "2012–", "2021–", "2021", "2021", "2028"
        };

        codigosObras = new String[]{
                "PM-02", "PM-03", "SJ-97", "SP-04", "OP-18",
                "CU-12", "PV-21", "IG-21", "ZH-21", "RF-28"
        };

        exibirModelo3D = new boolean[]{
                true, true, true, true, true,
                true, true, true, true, true
        };
    }

    private void inicializarQuestionario() {
        perguntas = new String[]{
                "Qual foi o primeiro rover que realmente funcionou na superfície de Marte?",
                "Qual rover ficou famoso por voar em Marte?",
                "Qual rover da NASA pousou na cratera Gale em 2012?",
                "Qual rover chinês fez parte da missão Tianwen-1?",
                "Qual rover europeu da missão ExoMars tem lançamento previsto para 2028?"
        };

        opcoes = new String[][]{
                {"PrOP-M", "Sojourner", "Curiosity", "Zhurong"},
                {"Opportunity", "Ingenuity", "Spirit", "Rosalind Franklin"},
                {"Curiosity", "Sojourner", "PrOP-M", "Zhurong"},
                {"Spirit", "Perseverance", "Zhurong", "Opportunity"},
                {"Sojourner", "Curiosity", "Rosalind Franklin", "Ingenuity"}
        };

        gabaritos = new int[]{1, 1, 0, 2, 2};
    }

    private void inicializarMinigames() {
        posicoesMinigame = new int[]{1, 4, 8};
    }

    // ── Implementações padrão de intMetodos ────────────────────────────────

    @Override
    public void registrarResposta(int pergunta, int opcao) {
        if (pergunta >= 0 && pergunta < respostasVisitante.length
                && opcao >= 0 && opcao < opcoes[pergunta].length) {
            respostasVisitante[pergunta] = opcao;
        }
    }

    @Override
    public void registrarSatisfacao(int estrelas) {
        if (estrelas >= 0 && estrelas <= 5) {
            notaSatisfacao = estrelas;
        }
    }

    @Override
    public int calcularPontuacao() {
        int acertos = 0;

        for (int i = 0; i < gabaritos.length; i++) {
            if (respostasVisitante[i] == gabaritos[i]) {
                acertos++;
            }
        }

        return acertos;
    }

    @Override
    public void avancar() {
        etapaAtual++;
    }

    @Override
    public void voltar() {
        if (etapaAtual > 0) {
            etapaAtual--;
        }
    }

    // ── Getters e Setters ──────────────────────────────────────────────────

    public String getNomeVisitante() {
        return nomeVisitante;
    }

    public String getSobrenomeVisitante() {
        return sobrenomeVisitante;
    }

    public String getNomeCompletoVisitante() {
        return nomeVisitante + " " + sobrenomeVisitante;
    }

    public String getFaixaEtariaVisitante() {
        return faixaEtariaVisitante;
    }

    public String[] getDadosVisitante() {
        return dadosVisitante;
    }

    public int getEtapaAtual() {
        return etapaAtual;
    }

    public int getObraAtual() {
        return obraAtual;
    }

    public int getNotaSatisfacao() {
        return notaSatisfacao;
    }

    public String[] getTitulosObras() {
        return titulosObras;
    }

    public String[] getDescricoesObras() {
        return descricoesObras;
    }

    public String[] getImagensObras() {
        return imagensObras;
    }

    public String[] getAnosObras() {
        return anosObras;
    }

    public String[] getCodigosObras() {
        return codigosObras;
    }

    public String getTituloObraBase(int i) {
        return titulosObras[i];
    }

    public String getDescricaoObraBase(int i) {
        return descricoesObras[i];
    }

    public String getImagemObraBase(int i) {
        return imagensObras[i];
    }

    public String getAnoObraBase(int i) {
        return anosObras[i];
    }

    public String getCodigoObraBase(int i) {
        return codigosObras[i];
    }

    public boolean[] getExibirModelo3D() {
        return exibirModelo3D;
    }

    public String[] getPerguntas() {
        return perguntas;
    }

    public String[][] getOpcoes() {
        return opcoes;
    }

    public int[] getGabaritos() {
        return gabaritos;
    }

    public int[] getRespostasVisitante() {
        return respostasVisitante;
    }

    public int[] getPosicoesMinigame() {
        return posicoesMinigame;
    }

    public List<String> getHistoricoNomes() {
        return historicoNomes;
    }

    public List<String> getHistoricoFaixasEtarias() {
        return historicoFaixasEtarias;
    }

    public List<Integer> getHistoricoPontuacoes() {
        return historicoPontuacoes;
    }

    public List<Integer> getHistoricoSatisfacoes() {
        return historicoSatisfacoes;
    }

    public void setNomeVisitante(String nomeVisitante) {
        this.nomeVisitante = nomeVisitante;
    }

    public void setSobrenomeVisitante(String sobrenomeVisitante) {
        this.sobrenomeVisitante = sobrenomeVisitante;
    }

    public void setFaixaEtariaVisitante(String faixaEtariaVisitante) {
        this.faixaEtariaVisitante = faixaEtariaVisitante;
    }

    public void setObraAtual(int obraAtual) {
        this.obraAtual = obraAtual;
    }

    @Deprecated
    public int getIdadeVisitante() {
        return 0;
    }

    @Deprecated
    public List<Integer> getHistoricoIdades() {
        return new ArrayList<>();
    }

    @Deprecated
    public void setIdadeVisitante(int idade) {
        // Não utilizado por regra de LGPD.
    }
}