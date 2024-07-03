package modelos;

import java.util.ArrayList;

public class Equipe {

    private String codinome;
    private int quantidade;
    private double latitude;
    private double longitude;
    private boolean alocado;
    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Atendimento> atendimentos;

    public Equipe(String codinome, int quantidade, double latitude, double longitude) {
        this.codinome = codinome;
        this.quantidade = quantidade;
        this.latitude = latitude;
        this.longitude = longitude;
        this.alocado = false;
        equipamentos = new ArrayList<Equipamento>();
        atendimentos = new ArrayList<Atendimento>();
    }

    public String getCodinome() {
        return codinome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isAlocado() {
        return alocado;
    }

    public void setAlocado(boolean alocado) {
        this.alocado = alocado;
    }

    public void addEquipamento(Equipamento equipamento) {
        equipamentos.add(equipamento);
    }

    public void removeAtendimento(Atendimento atendimento) {
        atendimentos.remove(atendimento);
    }

    public double distancia(Evento evento) {
        int raioTerra = 6371;

        double lat1 = Math.toRadians(this.latitude);
        double lon1 = Math.toRadians(this.longitude);
        double lat2 = Math.toRadians(evento.getLatitude());
        double lon2 = Math.toRadians(evento.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distancia = raioTerra * c;
        return distancia;
    }

    public ArrayList<Equipamento> getEquipamentos() {
        ArrayList<Equipamento> equipamentos = new ArrayList<Equipamento>();
        for (Equipamento e : this.equipamentos) {
            equipamentos.add(e);
        }
        return equipamentos;
    }

    public double custoEquipamentos() {
        return equipamentos.stream().mapToDouble(Equipamento::getCustoDia).sum();
    }

    public boolean addAtendimento(Atendimento atendimento) {
        if (distancia(atendimento.getEvento()) < 5000 && !alocado) {
            atendimentos.add(atendimento);
            alocado = true;
            return true;
        }
        return false;
    }

    @Override

    public String toString() {
        return codinome + ";" + quantidade + ";" + latitude + ";" + longitude;
    }
}
