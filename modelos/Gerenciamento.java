package modelos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Gerenciamento {

    private ArrayList<Atendimento> atendimentos;
    private ArrayList<Equipe> equipes;
    private ArrayList<Equipamento> equipamentos;
    private ArrayList<Evento> eventos;
    private Queue<Atendimento> atendimentosPendentes;

    public Gerenciamento() {
        atendimentos = new ArrayList<Atendimento>();
        equipes = new ArrayList<Equipe>();
        equipamentos = new ArrayList<Equipamento>();
        eventos = new ArrayList<Evento>();
        atendimentosPendentes = new LinkedList<>();
    }

    public boolean cadastrarEquipamento(Equipamento equipamento) {
        boolean existe = false;
        int index = 0;

        for (Equipamento e : equipamentos) {
            if (e.getId() == equipamento.getId()) {
                existe = true;
                break;
            }
            if (e.getId() > equipamento.getId()) {
                index = equipamentos.indexOf(e);
                break;
            }
            if (equipamentos.indexOf(e) == equipamentos.size() - 1) {
                index = equipamentos.size();
            }
        }
        if (!existe) {
            equipamentos.add(index, equipamento);
            if (equipamento.getEquipe() != null)
                equipamento.getEquipe().addEquipamento(equipamento);
            return true;
        }
        return false;
    }

    public void equipamentoParaEquipe(Equipamento equipamento, Equipe equipe) {
        equipe.addEquipamento(equipamento);
        equipamento.setEquipe(equipe);
    }

    public boolean cadastrarEquipe(Equipe equipe) {
        boolean existe = false;
        int index = 0;

        for (Equipe e : equipes) {
            if (e.getCodinome().equals(equipe.getCodinome())) {
                existe = true;
                break;
            }
            if (e.getCodinome().compareTo(equipe.getCodinome()) > 0) {
                index = equipes.indexOf(e);
                break;
            }
            if (equipes.indexOf(e) == equipes.size() - 1) {
                index = equipes.size();
            }
        }
        if (!existe) {
            equipes.add(index, equipe);
            return true;
        }
        return false;
    }

    public boolean cadastrarAtendimento(Atendimento atendimento) {
        boolean existe = false;
        int index = 0;

        for (Atendimento a : atendimentos) {
            if (a.getCod() == atendimento.getCod()) {
                existe = true;
                break;
            }
            if (a.getCod() > atendimento.getCod()) {
                index = atendimentos.indexOf(a);
                break;
            }
            if (atendimentos.indexOf(a) == atendimentos.size() - 1) {
                index = atendimentos.size();
            }
        }
        if (!existe) {
            atendimentos.add(index, atendimento);
            atendimento.getEvento().addAtendimento(atendimento);
            if (atendimento.getStatus().equalsIgnoreCase("Pendente")) {
                atendimentosPendentes.add(atendimento);
            }
            return true;
        }
        return false;
    }

    public boolean cadastrarEvento(Evento evento) {
        boolean existe = false;
        int index = 0;

        for (Evento e : eventos) {
            if (e.getCodigo().equals(evento.getCodigo())) {
                existe = true;
                break;
            }
            if (Integer.parseInt(e.getCodigo()) > Integer.parseInt(evento.getCodigo())) {
                index = eventos.indexOf(e);
                break;
            }
            if (eventos.indexOf(e) == eventos.size() - 1) {
                index = eventos.size();
            }
        }
        if (!existe) {
            eventos.add(index, evento);
            return true;
        }
        return false;
    }

    public boolean alocarAtendimentos() {
        boolean existe = false;
        boolean alocado = false;
        LinkedList<Atendimento> aux = new LinkedList<Atendimento>();
        while (!atendimentosPendentes.isEmpty()) {
            Atendimento a = atendimentosPendentes.remove();
            for (Equipe e : equipes) {

                if (e.isAlocado() && e.distancia(a.getEvento()) > 5000) {
                    alocado = true;
                    continue;
                }
                if (e.isAlocado()) {
                    continue;
                }

                if (e.addAtendimento(a)) {
                    a.addEquipe(e);
                    e.setAlocado(true);
                    alocado = true;
                    break;
                }
                if (equipes.indexOf(e) == equipes.size() - 1) {
                    a.cancelarAtendimento();
                }
            }
            if (alocado) {
                aux.add(a);
            }
            alocado = false;
            existe = true;
        }
        atendimentosPendentes = aux;
        return existe;
    }

    public void atendimentoPendente(Atendimento atendimento) {
        atendimentosPendentes.add(atendimento);
    }

    public Evento procuraEvento(String codigo) {
        Evento evento = null;
        for (Evento e : eventos) {
            if (e.getCodigo().equals(codigo)) {
                evento = e;
                break;
            }
        }
        return evento;
    }

    public Atendimento procuraAtendimento(int cod) {
        Atendimento atendimento = null;
        for (Atendimento a : atendimentos) {
            if (a.getCod() == cod) {
                atendimento = a;
                break;
            }
        }
        return atendimento;
    }

    public Equipe procuraEquipe(String codinome) {
        Equipe equipe = null;
        for (Equipe e : equipes) {
            if (e.getCodinome().equalsIgnoreCase(codinome)) {
                equipe = e;
                break;
            }
        }
        return equipe;
    }

    public ArrayList<Atendimento> getAtendimentos() {
        ArrayList<Atendimento> atendimentos = new ArrayList<Atendimento>();
        for (Atendimento a : this.atendimentos) {
            atendimentos.add(a);
        }
        return atendimentos;
    }

    public ArrayList<Equipe> getEquipes() {
        ArrayList<Equipe> equipes = new ArrayList<Equipe>();
        for (Equipe e : this.equipes) {
            equipes.add(e);
        }
        return equipes;
    }

    public ArrayList<Equipamento> getEquipamentos() {
        ArrayList<Equipamento> equipamentos = new ArrayList<Equipamento>();
        for (Equipamento e : this.equipamentos) {
            equipamentos.add(e);
        }
        return equipamentos;
    }

    public ArrayList<Evento> getEventos() {
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        for (Evento e : this.eventos) {
            eventos.add(e);
        }
        return eventos;
    }

    public boolean isEmpty() {
        return atendimentos.isEmpty() && equipes.isEmpty() && equipamentos.isEmpty() && eventos.isEmpty();
    }

}
