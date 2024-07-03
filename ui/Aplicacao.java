package ui;

import javax.swing.*;

import gerenciamento.ACMERescue;

public class Aplicacao extends JFrame {

    private MenuPrincipal menuPrincipal;
    private PainelAtendimento painelAtendimento;
    private PainelEquipe painelEquipe;
    private PainelEquipamento painelEquipamento;
    private PainelEvento painelEvento;
    private ACMERescue acmeRescue;

    public Aplicacao() {

        super();
        acmeRescue = new ACMERescue();
        menuPrincipal = new MenuPrincipal(acmeRescue, this);
        painelAtendimento = new PainelAtendimento(acmeRescue, this);
        painelEquipe = new PainelEquipe(acmeRescue, this);
        painelEquipamento = new PainelEquipamento(acmeRescue, this);
        painelEvento = new PainelEvento(acmeRescue, this);
        this.setContentPane(menuPrincipal);
        this.setTitle("ACMERescue");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setVisible(true);
    }

    public void switchPanel(int panel) {

        switch (panel) {
            case 1:
                this.setContentPane(menuPrincipal);
                break;
            case 2:
                this.setContentPane(painelAtendimento);
                break;
            case 3:
                this.setContentPane(painelEquipe);
                break;
            case 4:
                this.setContentPane(painelEquipamento);
                break;
            case 5:
                this.setContentPane(painelEvento);
                break;
            default:
                break;
        }
        this.validate();
    }
}
