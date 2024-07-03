package ui;

import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import gerenciamento.ACMERescue;

public class MenuPrincipal extends JPanel implements ActionListener {

    private ACMERescue acmeRescue;
    private Aplicacao aplicacao;
    private JButton botaoEquipe;
    private JButton botaoEvento;
    private JButton botaoEquipamento;
    private JButton botaoAtendimento;
    private JButton botaoRelatorio;
    private JButton botaoCarregar;
    private JButton botaoSalvar;
    private JButton botaoSair;

    public MenuPrincipal(ACMERescue acmeRescue, Aplicacao aplicacao) {
        super();
        this.aplicacao = aplicacao;
        botaoEquipe = new JButton("Equipe");
        botaoEvento = new JButton("Evento");
        botaoEquipamento = new JButton("Equipamento");
        botaoAtendimento = new JButton("Atendimento");
        botaoRelatorio = new JButton("Relatório Geral");
        botaoCarregar = new JButton("Carregar dados");
        botaoSalvar = new JButton("Salvar dados");
        botaoSair = new JButton("Sair");
        this.acmeRescue = acmeRescue;

        JPanel painelPrincipal = new JPanel();
        GridLayout layout = new GridLayout(9, 1);
        layout.setVgap(10);
        painelPrincipal.setLayout(layout);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Menu Principal");
        label.setFont(label.getFont().deriveFont(20.0f));
        painelPrincipal.setAlignmentX(CENTER_ALIGNMENT);
        painelPrincipal.add(label);

        painelPrincipal.add(botaoEquipe);
        painelPrincipal.add(botaoEvento);
        painelPrincipal.add(botaoEquipamento);
        painelPrincipal.add(botaoAtendimento);
        painelPrincipal.add(botaoRelatorio);
        painelPrincipal.add(botaoCarregar);
        painelPrincipal.add(botaoSalvar);
        painelPrincipal.add(botaoSair);

        botaoEquipe.addActionListener(this);
        botaoEvento.addActionListener(this);
        botaoRelatorio.addActionListener(this);
        botaoEquipamento.addActionListener(this);
        botaoAtendimento.addActionListener(this);
        botaoSair.addActionListener(this);
        botaoCarregar.addActionListener(this);
        botaoSalvar.addActionListener(this);

        this.add(painelPrincipal);
        this.setVisible(true);
        try {
            inicializar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            inicializar();
        }

    }

    private void inicializar() {
        String nomeArquivo = JOptionPane.showInputDialog("Digite o nome do arquivo para ler dados iniciais: ");
        if (nomeArquivo == null)
            return;
        acmeRescue.leArquivoEquipes(nomeArquivo);
        acmeRescue.leArquivoEventos(nomeArquivo);
        acmeRescue.leArquivoEquipamentos(nomeArquivo);
        acmeRescue.leArquivoAtendimentos(nomeArquivo);
        JOptionPane.showMessageDialog(null, "Dados carregados com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);

    }

    @Override

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == botaoEquipe) {
                aplicacao.switchPanel(3);
            } else if (e.getSource() == botaoEvento) {
                aplicacao.switchPanel(5);
            } else if (e.getSource() == botaoEquipamento) {
                aplicacao.switchPanel(4);
            } else if (e.getSource() == botaoAtendimento) {
                aplicacao.switchPanel(2);
            } else if (e.getSource() == botaoRelatorio) {
                JOptionPane.showMessageDialog(null, acmeRescue.relatorioGeral());
            } else if (e.getSource() == botaoSair) {
                System.exit(0);
            } else if (e.getSource() == botaoCarregar) {
                JPanel painelCheckbox = new JPanel();
                JCheckBox checkbox1 = new JCheckBox("Equipes");
                JCheckBox checkbox2 = new JCheckBox("Eventos");
                JCheckBox checkbox3 = new JCheckBox("Equipamentos");
                JCheckBox checkbox4 = new JCheckBox("Atendimentos");

                painelCheckbox.add(checkbox1);
                painelCheckbox.add(checkbox2);
                painelCheckbox.add(checkbox3);
                painelCheckbox.add(checkbox4);

                int result = JOptionPane.showConfirmDialog(null, painelCheckbox, "Selecione os dados a carregar",
                        JOptionPane.OK_CANCEL_OPTION);

                String nomeArquivo = JOptionPane.showInputDialog("Digite o nome do arquivo: ");
                if (nomeArquivo == null)
                    return;
                if (result == JOptionPane.OK_OPTION) {
                    if (checkbox1.isSelected()) {
                        acmeRescue.leArquivoEquipes(nomeArquivo);
                    }
                    if (checkbox2.isSelected()) {
                        acmeRescue.leArquivoEventos(nomeArquivo);
                    }
                    if (checkbox3.isSelected()) {
                        acmeRescue.leArquivoEquipamentos(nomeArquivo);
                    }
                    if (checkbox4.isSelected()) {
                        acmeRescue.leArquivoAtendimentos(nomeArquivo);
                    }
                    JOptionPane.showMessageDialog(null, "Dados carregados com sucesso!", "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else if (e.getSource() == botaoSalvar) {
                String nomeArquivo = JOptionPane.showInputDialog("Digite o nome do arquivo: ");
                if (nomeArquivo == null)
                    return;
                acmeRescue.salvarDados(nomeArquivo);
                JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

}
