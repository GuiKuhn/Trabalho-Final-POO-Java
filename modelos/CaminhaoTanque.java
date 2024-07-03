package modelos;

public class CaminhaoTanque extends Equipamento {
    private double capacidade;

    public CaminhaoTanque(int id, String nome, double custoDia, Equipe equipe, double capacidade) {
        super(id, nome, custoDia, equipe);
        this.capacidade = capacidade;
    }

    public double getCapacidade() {
        return capacidade;
    }

    @Override

    public String toString() {
        return super.toString() + 2 + ";" + capacidade;
    }
}
