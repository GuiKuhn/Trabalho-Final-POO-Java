package modelos;

public class Barco extends Equipamento {
    private int capacidade;

    public Barco(int id, String nome, double custoDia, Equipe equipe, int capacidade) {
        super(id, nome, custoDia, equipe);
        this.capacidade = capacidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    @Override

    public String toString() {
        return super.toString() + 1 + ";" + capacidade;
    }
}
