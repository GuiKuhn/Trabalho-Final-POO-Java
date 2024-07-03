package modelos;

public class Escavadeira extends Equipamento {
    private String combustivel;
    private double carga;

    public Escavadeira(int id, String nome, double custoDia, Equipe equipe, String combustivel, double carga) {
        super(id, nome, custoDia, equipe);
        this.combustivel = combustivel;
        this.carga = carga;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public double getCarga() {
        return carga;
    }

    @Override

    public String toString() {
        return super.toString() + 3 + ";" + combustivel + ";" + carga;
    }
}
