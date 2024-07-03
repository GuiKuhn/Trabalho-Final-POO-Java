package modelos;

public class Atendimento {

    private int cod;
    private String dataInicio;
    private int duracao;
    private String status;
    private Evento evento;
    private Equipe equipe;

    public Atendimento(int cod, String dataInicio, int duracao, String status, Evento evento) {
        this.cod = cod;
        this.dataInicio = dataInicio;
        this.duracao = duracao;
        this.status = status;
        this.evento = evento;
    }

    public int getCod() {
        return cod;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getStatus() {
        return status;
    }

    public boolean addEquipe(Equipe equipe) {
        if (this.equipe == null) {
            this.equipe = equipe;
            this.status = "EXECUTANDO";
            return true;
        }
        return false;
    }

    public void removeEquipe() {
        this.equipe = null;
        this.status = "PENDENTE";
    }

    public void finalizarAtendimento() {
        this.status = "FINALIZADO";
    }

    public void cancelarAtendimento() {
        this.status = "CANCELADO";
    }

    public Evento getEvento() {
        return evento;
    }

    public double calculaCusto() {

        if (equipe != null) {
            double custoEquipe = duracao * 250 * equipe.getQuantidade();
            double custoEquipamentos = equipe.custoEquipamentos() * duracao;
            double custoDeslocamento = equipe.distancia(evento)
                    * (100 * equipe.getQuantidade() + equipe.custoEquipamentos() * 0.1);
            return custoEquipe + custoEquipamentos + custoDeslocamento;
        }

        return 0;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    @Override

    public String toString() {
        if (equipe == null) {
            return cod + ";" + dataInicio + ";" + duracao + ";" + status + ";" + evento.getCodigo();
        }
        return cod + ";" + dataInicio + ";" + duracao + ";" + status + ";" + evento.getCodigo() + ";"
                + equipe.getCodinome();
    }
}
