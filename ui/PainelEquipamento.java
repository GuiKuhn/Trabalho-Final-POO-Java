package ui;

import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import gerenciamento.ACMERescue;

public class PainelEquipamento extends JPanel implements ActionListener {

    private ACMERescue acmeRescue;
    private Aplicacao aplicacao;
    private JButton botaoCadastrar;
    private JButton vincularEquipamento;
    private JButton botaoVoltar;

    public PainelEquipamento(ACMERescue acmeRescue, Aplicacao aplicacao) {

        super();
        this.acmeRescue = acmeRescue;
        this.aplicacao = aplicacao;
        botaoCadastrar = new JButton("Cadastrar Equipamento");
        vincularEquipamento = new JButton("Vincular Equipamento");
        botaoVoltar = new JButton("Voltar");

        botaoCadastrar.addActionListener(this);
        vincularEquipamento.addActionListener(this);
        botaoVoltar.addActionListener(this);

        JPanel painelPrincipal = new JPanel();

        painelPrincipal.add(botaoCadastrar);
        painelPrincipal.add(vincularEquipamento);
        painelPrincipal.add(botaoVoltar);

        GridLayout layout = new GridLayout(3, 1);
        layout.setVgap(10);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(100, 10, 70, 10));
        painelPrincipal.setLayout(layout);
        painelPrincipal.setAlignmentX(CENTER_ALIGNMENT);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == botaoCadastrar) {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o id do equipamento: "));
                String nome = JOptionPane.showInputDialog("Digite o nome do equipamento: ");
                double custoDia = Double.parseDouble(JOptionPane.showInputDialog("Digite o custo por dia: "));
                String[] opcoesEquipamentos = { "Barco", "Caminhão Tanque", "Escavadeira" };
                String opcaoEquipamento = (String) JOptionPane.showInputDialog(null, "Escolha uma opção", "Opções",
                        JOptionPane.QUESTION_MESSAGE, null, opcoesEquipamentos, opcoesEquipamentos[0]);
                if (opcaoEquipamento.equals("Barco")) {
                    int capacidade = Integer
                            .parseInt(JOptionPane.showInputDialog("Digite a capacidade máxima de pessoas: "));
                    acmeRescue.cadastrarEquipamento(id, nome, custoDia, capacidade);
                } else if (opcaoEquipamento.equals("Caminhão Tanque")) {
                    double capacidade = Double
                            .parseDouble(JOptionPane.showInputDialog("Digite a capacidade em milhares de litros: "));
                    acmeRescue.cadastrarEquipamento(id, nome, custoDia, capacidade);
                } else if (opcaoEquipamento.equals("Escavadeira")) {
                    String combustivel = JOptionPane.showInputDialog("Digite o combustível: ");
                    if (!combustivel.equalsIgnoreCase("Diesel") && !combustivel.equalsIgnoreCase("Gasolina")
                            && !combustivel.equalsIgnoreCase("Alcool"))
                        throw new RuntimeException("Combustível inválido");
                    double carga = Double
                            .parseDouble(JOptionPane.showInputDialog("Digite a carga máxima em metros cubicos: "));
                    acmeRescue.cadastrarEquipamento(id, nome, custoDia, combustivel, carga);
                }
                JOptionPane.showMessageDialog(null, "Equipamento cadastrado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (e.getSource() == vincularEquipamento) {
                ArrayList<String> equipes = acmeRescue.consultaEquipes();
                String[] opcoes = new String[equipes.size()];
                for (int i = 0; i < equipes.size(); i++) {
                    opcoes[i] = equipes.get(i);
                }
                String opcao = (String) JOptionPane.showInputDialog(null, "Escolha uma opção", "Opções",
                        JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
                ArrayList<String> equipamentos = acmeRescue.consultaEquipamentos();
                String[] opcoesEquipamentos = new String[equipamentos.size()];
                for (int i = 0; i < equipamentos.size(); i++) {
                    opcoesEquipamentos[i] = equipamentos.get(i);
                }
                String opcaoEquipamento = (String) JOptionPane.showInputDialog(null, "Escolha uma opção", "Opções",
                        JOptionPane.QUESTION_MESSAGE, null, opcoesEquipamentos, opcoesEquipamentos[0]);
                acmeRescue.equipamentoParaEquipe(equipamentos.indexOf(opcaoEquipamento), equipes.indexOf(opcao));
                JOptionPane.showMessageDialog(null, "Equipamento vinculado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (e.getSource() == botaoVoltar) {
                aplicacao.switchPanel(1);
            }
        } catch (NullPointerException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro: Digite dados válidos", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
