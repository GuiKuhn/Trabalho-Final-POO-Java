package modelos;

public class Terremoto extends Evento {

    private double magnitude;

    public Terremoto(String codigo, String data, double latitude, double longitude, double magnitude) {
        super(codigo, data, latitude, longitude);
        this.magnitude = magnitude;
    }

    public double getMagnitude() {
        return magnitude;
    }

    @Override

    public String toString() {
        return super.toString() + 2 + ";" + magnitude;
    }
}
