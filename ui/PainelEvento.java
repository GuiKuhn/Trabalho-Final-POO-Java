package ui;

import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.*;

import gerenciamento.ACMERescue;

public class PainelEvento extends JPanel implements ActionListener {

    private ACMERescue acmeRescue;
    private Aplicacao aplicacao;
    private JButton botaoCadastrar;
    private JButton botaoVoltar;

    public PainelEvento(ACMERescue acmeRescue, Aplicacao aplicacao) {

        this.acmeRescue = acmeRescue;
        this.aplicacao = aplicacao;
        botaoCadastrar = new JButton("Cadastrar Evento");
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
        painelPrincipal.setAlignmentX(BOTTOM_ALIGNMENT);

        this.add(painelPrincipal);
        this.setSize(400, 300);
        this.setVisible(true);

    }

    @Override

    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == botaoCadastrar) {
                String codigo = String
                        .valueOf(Integer.parseInt(JOptionPane.showInputDialog("Digite o código do evento: ")));
                if (codigo == null)
                    return;
                String data = JOptionPane.showInputDialog("Digite a data do evento: ");
                if (data == null)
                    return;
                double latitude = Double.parseDouble(JOptionPane.showInputDialog("Digite a latitude: "));
                double longitude = Double.parseDouble(JOptionPane.showInputDialog("Digite a longitude: "));
                String[] opcoesEvento = { "Ciclone", "Terremoto", "Seca" };
                String opcaoEvento = (String) JOptionPane.showInputDialog(null, "Escolha uma opção", "Opções",
                        JOptionPane.QUESTION_MESSAGE, null, opcoesEvento, opcoesEvento[0]);
                if (opcaoEvento.equals("Ciclone")) {
                    double velocidadeVento = Double
                            .parseDouble(JOptionPane.showInputDialog("Digite a velocidade do vento em km/h: "));
                    double precipitacao = Double
                            .parseDouble(JOptionPane.showInputDialog("Digite a precipitação: "));
                    acmeRescue.cadastrarEvento(codigo, data, latitude, longitude, velocidadeVento, precipitacao);
                } else if (opcaoEvento.equals("Terremoto")) {
                    double magnitude = Double
                            .parseDouble(JOptionPane.showInputDialog("Digite a magnitude na escala Richter: "));
                    acmeRescue.cadastrarEvento(codigo, data, latitude, longitude, magnitude);
                } else if (opcaoEvento.equals("Seca")) {
                    int estiagem = Integer.parseInt(JOptionPane.showInputDialog("Digite os dias de estiagem: "));
                    acmeRescue.cadastrarEvento(codigo, data, latitude, longitude, estiagem);
                }
                JOptionPane.showMessageDialog(null, "Evento cadastrado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            } else if (e.getSource() == botaoVoltar) {
                aplicacao.switchPanel(1);
            }
        } catch (NullPointerException ex) {
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro: digite dados válidos", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
