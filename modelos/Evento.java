package modelos;

public class Evento {
    private String codigo;
    private String data;
    private double latitude;
    private double longitude;
    private Atendimento atendimento;

    public Evento(String codigo, String data, double latitude, double longitude) {
        this.codigo = codigo;
        this.data = data;
        this.latitude = latitude;
        this.longitude = longitude;
        atendimento = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getData() {
        return data;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

    public boolean addAtendimento(Atendimento atendimento) {
        if (this.atendimento == null) {
            this.atendimento = atendimento;
            return true;
        }
        return false;
    }

    @Override

    public String toString() {

        return codigo + ";" + data + ";" + latitude + ";" + longitude + ";";
    }

}