package modelos;

public class Seca extends Evento {

    private int estiagem;

    public Seca(String codigo, String data, double latitude, double longitude, int estiagem) {
        super(codigo, data, latitude, longitude);
        this.estiagem = estiagem;
    }

    public double getEstiagem() {
        return estiagem;
    }

    @Override

    public String toString() {
        return super.toString() + 3 + ";" + estiagem;
    }
}
