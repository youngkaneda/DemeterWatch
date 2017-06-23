package br.ufla.dcc.ppoo.gui;

import br.ufla.dcc.ppoo.i18n.I18N;
import br.ufla.dcc.ppoo.imagens.GerenciadorDeImagens;
import br.ufla.dcc.ppoo.util.Utilidades;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Classe que representa a tela Meus Filmes
 * 
 * @author Julio Alves e Paulo Jr.
 */
public class TelaMeusFilmes {

    // referência para a tela principal
    private final TelaPrincipal telaPrincipal;
            
    // componentes da tela
    private JDialog janela;
    private GridBagLayout layout;
    private GridBagConstraints gbc;
    private JButton btnNovoFilme;
    private JButton btnEditarFilme;
    private JButton btnDeletarFilme;
    private JButton btnSalvarFilme;
    private JButton btnCancelar;
    private JTable tbFilmes;
    private JLabel lbNomes;
    private JLabel lbGeneros;
    private JLabel lbAno;
    private JLabel lbDuracao;
    private JLabel lbDescricao;
    private JTextField txtNome;
    private JTextField txtGenero;
    private JTextField txtAno;
    private JTextField txtDuracao;
    private JTextArea taDescricao;

     /**
     * Constrói a tela Meus Filmes guardando a referência da tela principal.
     * 
     * @param telaPrincipal Referência da tela principal.
     */
    public TelaMeusFilmes(TelaPrincipal telaPrincipal) {
        this.telaPrincipal = telaPrincipal;
    }

    /**
     * Inicializa a tela, construindo seus componentes, configurando os eventos
     * e, ao final, exibe a tela.
     */
    public void inicializar() {
        construirTela();
        configurarEventosTela();
        exibirTela();
    }

    /**
     * Constrói a janela tratando internacionalização, componentes e layout.
     */
    private void construirTabela() {
        Object[] titulosColunas = {
            I18N.obterRotuloFilmeNome(),
            I18N.obterRotuloFilmeGenero()
        };

        // Dados "fake"
        Object[][] dados = {
            {"Gravidade", "Ficção Científica"},
            {"Shrek", "Animação"}
        };

        tbFilmes = new JTable(dados, titulosColunas);
        tbFilmes.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tbFilmes.setFillsViewportHeight(true);
    }

    /**
     * Adiciona um componente à tela.
     */
    private void adicionarComponente(Component c,
            int anchor, int fill, int linha,
            int coluna, int largura, int altura) {
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.gridy = linha;
        gbc.gridx = coluna;
        gbc.gridwidth = largura;
        gbc.gridheight = altura;
        gbc.insets = new Insets(5, 5, 5, 5);
        layout.setConstraints(c, gbc);
        janela.add(c);
    }

    /**
     * Trata o estado inicial da tela
     */
    private void prepararComponentesEstadoInicial() {
        tbFilmes.clearSelection();
        tbFilmes.setEnabled(true);

        txtNome.setText("");
        txtGenero.setText("");
        txtAno.setText("");
        txtDuracao.setText("");
        taDescricao.setText("");

        txtNome.setEditable(false);
        txtGenero.setEditable(false);
        txtAno.setEditable(false);
        txtDuracao.setEditable(false);
        taDescricao.setEditable(false);

        btnNovoFilme.setEnabled(true);
        btnEditarFilme.setEnabled(false);
        btnSalvarFilme.setEnabled(false);
        btnDeletarFilme.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    /**
     * Trata o estado da tela para seleção de filmes
     */
    private void prepararComponentesEstadoSelecaoFilme() {
        txtNome.setEditable(false);
        txtGenero.setEditable(false);
        txtAno.setEditable(false);
        txtDuracao.setEditable(false);
        taDescricao.setEditable(false);

        btnNovoFilme.setEnabled(true);
        btnEditarFilme.setEnabled(true);
        btnSalvarFilme.setEnabled(false);
        btnDeletarFilme.setEnabled(true);
        btnCancelar.setEnabled(true);
    }

    /**
     * Trata o estado da tela para cadastro de novo filme
     */
    private void prepararComponentesEstadoNovoFilme() {
        tbFilmes.clearSelection();
        tbFilmes.setEnabled(false);

        txtNome.setText("");
        txtGenero.setText("");
        txtAno.setText("");
        txtDuracao.setText("");
        taDescricao.setText("");

        txtNome.setEditable(true);
        txtGenero.setEditable(true);
        txtAno.setEditable(true);
        txtDuracao.setEditable(true);
        taDescricao.setEditable(true);

        btnNovoFilme.setEnabled(false);
        btnEditarFilme.setEnabled(false);
        btnSalvarFilme.setEnabled(true);
        btnDeletarFilme.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    /**
     * Trata o estado da tela para cadastro filme editado
     */
    private void prepararComponentesEstadoEditouFilme() {
        tbFilmes.setEnabled(false);

        txtNome.setEditable(true);
        txtGenero.setEditable(true);
        txtAno.setEditable(true);
        txtDuracao.setEditable(true);
        taDescricao.setEditable(true);

        btnNovoFilme.setEnabled(false);
        btnEditarFilme.setEnabled(false);
        btnSalvarFilme.setEnabled(true);
        btnDeletarFilme.setEnabled(false);
        btnCancelar.setEnabled(true);
    }

    /**
     * Adiciona os componentes da tela tratando layout e internacionalização
     */
    private void adicionarComponentes() {
        construirTabela();
        JScrollPane scrollPaneTabela = new JScrollPane(tbFilmes);
        adicionarComponente(scrollPaneTabela,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                0, 0, 4, 1);

        lbNomes = new JLabel(I18N.obterRotuloFilmeNome());
        adicionarComponente(lbNomes,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                1, 0, 1, 1);

        txtNome = new JTextField(25);
        adicionarComponente(txtNome,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                1, 1, 3, 1);

        lbGeneros = new JLabel(I18N.obterRotuloFilmeGenero());
        adicionarComponente(lbGeneros,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                2, 0, 1, 1);

        txtGenero = new JTextField(25);
        adicionarComponente(txtGenero,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                2, 1, 3, 1);

        lbAno = new JLabel(I18N.obterRotuloFilmeAno());
        adicionarComponente(lbAno,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                3, 0, 1, 1);

        txtAno = new JTextField(8);
        adicionarComponente(txtAno,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                3, 1, 1, 1);

        lbDuracao = new JLabel(I18N.obterRotuloFilmeDuracao());
        adicionarComponente(lbDuracao,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                3, 2, 1, 1);

        txtDuracao = new JTextField(8);
        adicionarComponente(txtDuracao,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                3, 3, 1, 1);

        lbDescricao = new JLabel(I18N.obterRotuloFilmeDescricao());
        adicionarComponente(lbDescricao,
                GridBagConstraints.LINE_END,
                GridBagConstraints.NONE,
                4, 0, 1, 1);

        taDescricao = new JTextArea(7, 25);
        JScrollPane scrollPaneDescricao = new JScrollPane(taDescricao,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        adicionarComponente(scrollPaneDescricao,
                GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL,
                4, 1, 3, 1);

        btnNovoFilme = new JButton(I18N.obterBotaoNovo(),
                GerenciadorDeImagens.NOVO);

        btnEditarFilme = new JButton(I18N.obterBotaoEditar(),
                GerenciadorDeImagens.EDITAR);

        btnSalvarFilme = new JButton(I18N.obterBotaoSalvar(),
                GerenciadorDeImagens.OK);

        btnDeletarFilme = new JButton(I18N.obterBotaoDeletar(),
                GerenciadorDeImagens.DELETAR);

        btnCancelar = new JButton(I18N.obterBotaoCancelar(),
                GerenciadorDeImagens.CANCELAR);

        prepararComponentesEstadoInicial();

        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnNovoFilme);
        painelBotoes.add(btnEditarFilme);
        painelBotoes.add(btnSalvarFilme);
        painelBotoes.add(btnDeletarFilme);
        painelBotoes.add(btnCancelar);

        adicionarComponente(painelBotoes,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                6, 0, 4, 1);
    }

    /**
     * Trata a selação de filmes na grade.
     */
    private void selecionouFilme() {
        // Dados "fake"
        String texto = String.format("Linha selecionada: %d", tbFilmes.getSelectedRow());
        txtNome.setText(texto);
        txtGenero.setText(texto);
        txtAno.setText(texto);
        txtDuracao.setText(texto);
        taDescricao.setText(texto);
    }

    /**
     * Configura os eventos da tela.
     */
    private void configurarEventosTela() {
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                janela.dispose();
            }
        });

        tbFilmes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                prepararComponentesEstadoSelecaoFilme();
                selecionouFilme();
            }
        });

        btnEditarFilme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararComponentesEstadoEditouFilme();
            }
        });

        btnSalvarFilme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararComponentesEstadoInicial();
            }
        });

        btnNovoFilme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prepararComponentesEstadoNovoFilme();
            }
        });

        btnDeletarFilme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Utilidades.msgConfirmacao(I18N.obterConfirmacaoDeletar())) {
                    // Remover filme!
                }
            }
        });
    }

    /**
     * Constrói a janela tratando internacionalização, componentes e layout.
     */
    private void construirTela() {
        janela = new JDialog();
        janela.setTitle(I18N.obterTituloTelaMeusFilmes());
        layout = new GridBagLayout();
        gbc = new GridBagConstraints();
        janela.setLayout(layout);
        adicionarComponentes();
        janela.pack();
    }

    /**
     * Exibe a tela.
     */
    private void exibirTela() {
        janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janela.setLocationRelativeTo(telaPrincipal.obterJanela());
        janela.setModal(true);
        janela.setVisible(true);
        janela.setResizable(false);
    }
}
