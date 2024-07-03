package modelos;

public class Equipamento {
    private int id;
    private String nome;
    private double custoDia;
    private Equipe equipe;

    public Equipamento(int id, String nome, double custoDia, Equipe equipe) {
        this.id = id;
        this.nome = nome;
        this.custoDia = custoDia;
        this.equipe = equipe;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getCustoDia() {
        return custoDia;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    @Override

    public String toString() {
        if (equipe == null) {
            return id + ";" + nome + ";" + custoDia + ";" + "sem-equipe" + ";";
        }
        return id + ";" + nome + ";" + custoDia + ";" + equipe.getCodinome() + ";";
    }
}
