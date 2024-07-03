package ui;

import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

import gerenciamento.ACMERescue;

public class PainelEquipe extends JPanel implements ActionListener {

    private ACMERescue acmeRescue;
    private Aplicacao aplicacao;
    private JButton botaoCadastrar;
    private JButton botaoVoltar;

    public PainelEquipe(ACMERescue acmeRescue, Aplicacao aplicacao) {

        super();
        this.acmeRescue = acmeRescue;
        this.aplicacao = aplicacao;
        botaoCadastrar = new JButton("Cadastrar Equipe");
        botaoVoltar = new JButton("Voltar");

        botaoCadastrar.addActionListener(this);
        botaoVoltar.addActionListener(this);

        JPanel painelPrincipal = new JPanel();

        painelPrincipal.add(botaoCadastrar);
        painelPrincipal.add(botaoVoltar);

        GridLayout layout = new GridLayout(2, 1);
        layout.setVgap(10);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(125, 10, 100, 10));
        painelPrincipal.setLayout(layout);
        painelPrincipal.setAlignmentX(CENTER_ALIGNMENT);

        this.add(painelPrincipal);
        this.setVisible(true);
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == botaoCadastrar) {
                String codinome = JOptionPane.showInputDialog("Digite o codinome da equipe: ");
                int quantidade = Integer.parseInt(JOptionPane.showInputDialog("Digite a quantidade de membros: "));
                double latitude = Double.parseDouble(JOptionPane.showInputDialog("Digite a latitude: "));
                double longitude = Double.parseDouble(JOptionPane.showInputDialog("Digite a longitude: "));
                acmeRescue.cadastrarEquipe(codinome, quantidade, latitude, longitude);
                JOptionPane.showMessageDialog(null, "Equipe cadastrada com sucesso!", "Sucesso",
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
