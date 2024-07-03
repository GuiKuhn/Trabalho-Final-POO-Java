package ui;

import javax.swing.*;

import gerenciamento.ACMERescue;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

public class PainelAtendimento extends JPanel implements ActionListener {

    private ACMERescue acmeRescue;
    private Aplicacao aplicacao;
    private JButton botaoCadastrar;
    private JButton alocarAtendimentos;
    private JButton botaoSituacao;
    private JButton botaoRelatorio;
    private JButton botaoVoltar;

    public PainelAtendimento(ACMERescue acmeRescue, Aplicacao aplicacao) {
        this.acmeRescue = acmeRescue;
        this.aplicacao = aplicacao;
        botaoCadastrar = new JButton("Cadastrar Atendimento");
        alocarAtendimentos = new JButton("Alocar Atendimentos");
        botaoSituacao = new JButton("Alterar Status");
        botaoRelatorio = new JButton("Relatório Atendimentos");
        botaoVoltar = new JButton("Voltar");

        botaoCadastrar.addActionListener(this);
        alocarAtendimentos.addActionListener(this);
        botaoSituacao.addActionListener(this);
        botaoRelatorio.addActionListener(this);
        botaoVoltar.addActionListener(this);

        JPanel painelPrincipal = new JPanel();

        painelPrincipal.add(botaoCadastrar);
        painelPrincipal.add(alocarAtendimentos);
        painelPrincipal.add(botaoSituacao);
        painelPrincipal.add(botaoRelatorio);
        painelPrincipal.add(botaoVoltar);

        GridLayout layout = new GridLayout(5, 1);
        layout.setVgap(10);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(70, 10, 70, 10));
        painelPrincipal.setLayout(layout);
        painelPrincipal.setAlignmentX(CENTER_ALIGNMENT);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == botaoCadastrar) {
                ArrayList<String> eventos = acmeRescue.consultaEventos();
                String[] opcoes = new String[eventos.size()];
                for (int i = 0; i < eventos.size(); i++) {
                    opcoes[i] = eventos.get(i);
                }
                String opcao = (String) JOptionPane.showInputDialog(null, "Escolha uma opção", "Opções",
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                int cod = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do atendimento: "));
                String dataInicio = JOptionPane.showInputDialog("Digite a data de início do atendimento: ");
                int duracao = Integer.parseInt(JOptionPane.showInputDialog("Digite a duração do atendimento: "));
                acmeRescue.cadastrarAtendimento(cod, dataInicio, duracao, eventos.indexOf(opcao));
            } else if (e.getSource() == alocarAtendimentos) {
                acmeRescue.alocarAtendimentos();
                JOptionPane.showMessageDialog(null, "Atendimentos alocados com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (e.getSource() == botaoVoltar) {
                aplicacao.switchPanel(1);
            } else if (e.getSource() == botaoSituacao) {
                int cod = Integer.parseInt(JOptionPane.showInputDialog("Digite o código do atendimento: "));
                String[] opcoes = { "PENDENTE", "EXECUTANDO", "FINALIZADO", "CANCELADO" };
                String situacao = (String) JOptionPane.showInputDialog(null, "Escolha uma opção", "Opções",
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                acmeRescue.alterarStatusAtendimento(cod, situacao);
                JOptionPane.showMessageDialog(null, "Status alterado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (e.getSource() == botaoRelatorio) {
                JOptionPane.showMessageDialog(null, acmeRescue.relatorioAtendimentos());
            }
        } catch (NullPointerException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro: Digite dados válidos", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
