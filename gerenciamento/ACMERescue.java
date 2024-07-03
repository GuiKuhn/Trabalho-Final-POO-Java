package gerenciamento;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import modelos.*;

public class ACMERescue {

    Gerenciamento gerenciamento;
    private BufferedReader reader;
    private PrintWriter writer;

    public ACMERescue() {
        gerenciamento = new Gerenciamento();
        Locale.setDefault(Locale.ENGLISH);
    }

    public void cadastrarEquipe(String codinome, int quantidade, double latitude, double longitude) {
        Equipe equipe = new Equipe(codinome, quantidade, latitude, longitude);

        if (!gerenciamento.cadastrarEquipe(equipe)) {
            throw new RuntimeException("Equipe já cadastrada");
        }
    }

    public void cadastrarEvento(String codigo, String dataInicio, double latitude, double longitude, double velocidade,
            double precipitacao) {
        Ciclone evento = new Ciclone(codigo, dataInicio, latitude, longitude, velocidade, precipitacao);
        if (!gerenciamento.cadastrarEvento(evento)) {
            throw new RuntimeException("Evento já cadastrado");
        }
    }

    public void cadastrarEvento(String codigo, String dataInicio, double latitude, double longitude, double magnitude) {
        Terremoto evento = new Terremoto(codigo, dataInicio, latitude, longitude, magnitude);
        if (!gerenciamento.cadastrarEvento(evento)) {
            throw new RuntimeException("Evento já cadastrado");
        }
    }

    public void cadastrarEvento(String codigo, String dataInicio, double latitude, double longitude, int estiagem) {
        Seca evento = new Seca(codigo, dataInicio, latitude, longitude, estiagem);
        if (!gerenciamento.cadastrarEvento(evento)) {
            throw new RuntimeException("Evento já cadastrado");
        }
    }

    public void cadastrarEquipamento(int id, String nome, double custoDia, double capacidade) {

        CaminhaoTanque equipamento = new CaminhaoTanque(id, nome, custoDia, null, capacidade);
        if (!gerenciamento.cadastrarEquipamento(equipamento)) {
            throw new RuntimeException("Equipamento já cadastrado");
        }
    }

    public void cadastrarEquipamento(int id, String nome, double custoDia, int capacidade) {

        Barco equipamento = new Barco(id, nome, custoDia, null, capacidade);
        if (!gerenciamento.cadastrarEquipamento(equipamento)) {
            throw new RuntimeException("Equipamento já cadastrado");
        }
    }

    public void cadastrarEquipamento(int id, String nome, double custoDia, String combustivel, double carga) {

        Escavadeira equipamento = new Escavadeira(id, nome, custoDia, null, combustivel, carga);
        if (!gerenciamento.cadastrarEquipamento(equipamento)) {
            throw new RuntimeException("Equipamento já cadastrado");
        }
    }

    public void cadastrarAtendimento(int cod, String dataInicio, int duracao, int indexEvento) {
        Evento evento = gerenciamento.getEventos().get(indexEvento);

        Atendimento atendimento = new Atendimento(cod, dataInicio, duracao, "PENDENTE", evento);

        if (evento.getAtendimento() != null) {
            throw new RuntimeException("Evento já possui atendimento");
        }
        if (gerenciamento.cadastrarAtendimento(atendimento)) {
            evento.addAtendimento(atendimento);
        } else {
            throw new RuntimeException("Atendimento já cadastrado");
        }

    }

    public void leArquivoEquipes(String arquivo) {
        try {
            Path path = Paths.get("dados/" + arquivo + "-EQUIPES.CSV");
            System.out.println(path.toAbsolutePath());
            reader = Files.newBufferedReader(path, Charset.defaultCharset());
            reader.readLine();
            String linha = reader.readLine().trim();

            Scanner scanner;
            while (linha != null) {
                linha = linha.trim();
                scanner = new Scanner(linha).useDelimiter(";");
                String codinome = scanner.next();
                int quantidade = scanner.nextInt();
                double latitude = scanner.nextDouble();
                double longitude = scanner.nextDouble();
                Equipe equipe = new Equipe(codinome, quantidade, latitude, longitude);
                scanner.close();
                if (!gerenciamento.cadastrarEquipe(equipe))
                    throw new RuntimeException("Equipe de codinome " + codinome + " já está cadastrada");
                linha = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo");
        }

    }

    public void leArquivoAtendimentos(String arquivo) {
        try {
            Path path = Paths.get("dados/" + arquivo + "-ATENDIMENTOS.CSV");
            reader = Files.newBufferedReader(path, Charset.defaultCharset());
            reader.readLine();
            String linha = reader.readLine().trim();
            Scanner scanner;
            while (linha != null) {
                linha = linha.trim();
                scanner = new Scanner(linha).useDelimiter(";");
                int cod = scanner.nextInt();
                String dataInicio = scanner.next();
                int duracao = scanner.nextInt();
                String status = scanner.next();
                String codEvento = scanner.next();
                Evento evento = gerenciamento.procuraEvento(codEvento);

                if (evento != null) {
                    if (evento.getAtendimento() != null) {
                        scanner.close();
                        throw new RuntimeException("Evento de codigo " + codEvento + " já possui atendimento");
                    }
                    if (scanner.hasNext()) {
                        String codinomeEquipe = scanner.next();
                        Equipe equipe = gerenciamento.procuraEquipe(codinomeEquipe);
                        Atendimento atendimento = new Atendimento(cod, dataInicio, duracao, status, evento);
                        atendimento.addEquipe(equipe);
                        gerenciamento.cadastrarAtendimento(atendimento);
                    } else {
                        Atendimento atendimento = new Atendimento(cod, dataInicio, duracao, status, evento);
                        gerenciamento.cadastrarAtendimento(atendimento);
                    }
                } else {
                    scanner.close();
                    throw new RuntimeException("Evento não encontrado");
                }
                scanner.close();
                linha = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo");
        }

    }

    public void leArquivoEventos(String arquivo) {
        try {
            Path path = Paths.get("dados/" + arquivo + "-EVENTOS.CSV");
            reader = Files.newBufferedReader(path, Charset.defaultCharset());
            reader.readLine();
            String linha = reader.readLine().trim();
            Scanner scanner;
            while (linha != null) {
                linha = linha.trim();
                scanner = new Scanner(linha).useDelimiter(";");
                String cod = scanner.next();
                String nome = scanner.next();
                double latitude = scanner.nextDouble();
                double longitude = scanner.nextDouble();
                int tipo = scanner.nextInt();
                Evento evento = null;
                if (tipo == 1) {
                    double velocidade = scanner.nextDouble();
                    double precipitacao = scanner.nextDouble();
                    evento = new Ciclone(cod, nome, latitude, longitude, velocidade, precipitacao);
                } else if (tipo == 2) {
                    double magnitude = scanner.nextDouble();
                    evento = new Terremoto(cod, nome, latitude, longitude, magnitude);
                } else if (tipo == 3) {
                    int estiagem = scanner.nextInt();
                    evento = new Seca(cod, nome, latitude, longitude, estiagem);
                } else {
                    scanner.close();
                    throw new RuntimeException("Tipo de evento inválido");
                }
                scanner.close();
                if (!gerenciamento.cadastrarEvento(evento))
                    throw new RuntimeException("Evento de código " + cod + " já está cadastrado");
                linha = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo");
        }

    }

    public void leArquivoEquipamentos(String arquivo) {
        try {
            Path path = Paths.get("dados/" + arquivo + "-EQUIPAMENTOS.CSV");
            System.out.println(path.toAbsolutePath());
            reader = Files.newBufferedReader(path, Charset.defaultCharset());
            reader.readLine();
            String linha = reader.readLine().trim();
            Scanner scanner;
            while (linha != null) {
                linha = linha.trim();
                scanner = new Scanner(linha).useDelimiter(";");
                int id = scanner.nextInt();
                String nome = scanner.next();
                double custoDia = scanner.nextDouble();
                String codinomeEquipe = scanner.next();
                Equipe equipe = gerenciamento.procuraEquipe(codinomeEquipe);
                int tipo = scanner.nextInt();
                Equipamento equipamento = null;
                if (tipo == 1) {
                    int capacidade = scanner.nextInt();
                    equipamento = new Barco(id, nome, custoDia, equipe, capacidade);
                } else if (tipo == 2) {
                    double capacidade = scanner.nextDouble();
                    equipamento = new CaminhaoTanque(id, nome, custoDia, equipe, capacidade);
                } else if (tipo == 3) {
                    String combustivel = scanner.next();
                    if (!combustivel.equalsIgnoreCase("DIESEL") && !combustivel.equalsIgnoreCase("GASOLINA")
                            && !combustivel.equalsIgnoreCase("ALCOOL")) {
                        scanner.close();
                        throw new RuntimeException("Combustível inválido");
                    }
                    double carga = scanner.nextDouble();
                    equipamento = new Escavadeira(id, nome, custoDia, equipe, combustivel, carga);
                } else {
                    scanner.close();
                    throw new RuntimeException("Tipo de equipamento inválido");
                }
                scanner.close();
                if (!gerenciamento.cadastrarEquipamento(equipamento)) {
                    throw new RuntimeException("Equipamento de id " + id + " já está cadastrado");
                }
                linha = reader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo");
        }

    }

    public void salvarDados(String arquivo) {
        try {
            if (gerenciamento.isEmpty()) {
                throw new RuntimeException("Não há dados cadastrados");
            }
            if (!gerenciamento.getEquipes().isEmpty()) {
                Path path = Paths.get("dados/" + arquivo + "-EQUIPES.CSV");
                writer = new PrintWriter(Files.newBufferedWriter(path, Charset.defaultCharset()));
                writer.println("codinome;quantidade;latitude;longitude");
                for (Equipe e : gerenciamento.getEquipes()) {
                    writer.println(e);
                }
                writer.close();
            }

            if (!gerenciamento.getEventos().isEmpty()) {
                Path path = Paths.get("dados/" + arquivo + "-EVENTOS.CSV");
                writer = new PrintWriter(Files.newBufferedWriter(path, Charset.defaultCharset()));
                writer.println("codigo;data;latitude;longitude;tipo;velocidade_magnitude_estiagem;precipitacao");
                for (Evento e : gerenciamento.getEventos()) {
                    writer.println(e);
                }
                writer.close();

            }

            if (!gerenciamento.getEquipamentos().isEmpty()) {
                Path path = Paths.get("dados/" + arquivo + "-EQUIPAMENTOS.CSV");
                writer = new PrintWriter(Files.newBufferedWriter(path, Charset.defaultCharset()));
                writer.println("identificador;nome;custodiario;codinome;tipo;capacidade_combustivel;carga");
                for (Equipamento e : gerenciamento.getEquipamentos()) {
                    writer.println(e);
                }
                writer.close();

            }
            if (!gerenciamento.getAtendimentos().isEmpty()) {
                Path path = Paths.get("dados/" + arquivo + "-ATENDIMENTOS.CSV");
                writer = new PrintWriter(Files.newBufferedWriter(path, Charset.defaultCharset()));
                writer.println("cod;dataInicio;duracao;status;codigo");
                for (Atendimento a : gerenciamento.getAtendimentos()) {
                    writer.println(a);
                }
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo");
        }
    }

    public ArrayList<String> consultaEventos() {
        if (gerenciamento.getEventos().isEmpty()) {
            throw new RuntimeException("Não há eventos cadastrados");
        }
        ArrayList<String> eventos = new ArrayList<String>();
        for (Evento e : gerenciamento.getEventos()) {
            String aux = "Código: " + e.getCodigo() + " Latitude: " + e.getLatitude()
                    + " Longitude: " + e.getLongitude() + " Tipo: ";
            if (e instanceof Ciclone) {
                Ciclone c = (Ciclone) e;
                aux += "Ciclone" + " Velocidade: " + c.getVelocidade() + " Precipitação: " + c.getPrecipitacao();
            } else if (e instanceof Terremoto) {
                Terremoto t = (Terremoto) e;
                aux += "Terremoto" + " Magnitude: " + t.getMagnitude();
            } else {
                Seca s = (Seca) e;
                aux += "Seca" + " Estiagem: " + s.getEstiagem();
            }
            eventos.add(aux);
        }
        return eventos;
    }

    public String relatorioGeral() {

        if (gerenciamento.isEmpty()) {
            throw new RuntimeException("Não há dados cadastrados");
        }

        String aux = "";

        aux += "Relatório Geral\n";

        aux += "Eventos\n";

        for (Evento e : gerenciamento.getEventos()) {
            aux += "Código: " + e.getCodigo() + " Latitude: " + e.getLatitude()
                    + " Longitude: " + e.getLongitude() + " Tipo: ";
            if (e instanceof Ciclone) {
                Ciclone c = (Ciclone) e;
                aux += "Ciclone" + " Velocidade: " + c.getVelocidade() + " Precipitação: " + c.getPrecipitacao();
            } else if (e instanceof Terremoto) {
                Terremoto t = (Terremoto) e;
                aux += "Terremoto" + " Magnitude: " + t.getMagnitude();
            } else {
                Seca s = (Seca) e;
                aux += "Seca" + " Estiagem: " + s.getEstiagem();
            }
            aux += "\n";
        }

        aux += "Equipamentos\n";

        for (Equipamento e : gerenciamento.getEquipamentos()) {
            aux += "ID: " + e.getId() + " Nome: " + e.getNome() + " Custo por dia: " + e.getCustoDia() + " Equipe: "
                    + e.getEquipe().getCodinome() + " Tipo: ";
            if (e instanceof Barco) {
                Barco b = (Barco) e;
                aux += "Barco" + " Capacidade: " + b.getCapacidade();
            } else if (e instanceof CaminhaoTanque) {
                CaminhaoTanque c = (CaminhaoTanque) e;
                aux += "Caminhão Tanque" + " Capacidade: " + c.getCapacidade();
            } else {
                Escavadeira esc = (Escavadeira) e;
                aux += "Escavadeira" + " Combustível: " + esc.getCombustivel() + " Carga: " + esc.getCarga();
            }
            aux += "\n";
        }

        aux += "Equipes\n";

        for (Equipe e : gerenciamento.getEquipes()) {
            aux += "Codinome: " + e.getCodinome() + " Quantidade: " + e.getQuantidade() + " Latitude: "
                    + e.getLatitude() + " Longitude: " + e.getLongitude() + "\n";
        }

        aux += "Atendimentos\n";

        for (Atendimento a : gerenciamento.getAtendimentos()) {
            aux += "Código: " + a.getCod() + " Data de início: " + a.getDataInicio() + " Duração: " + a.getDuracao()
                    + " Status: " + a.getStatus() + " Evento: " + a.getEvento().getCodigo();
            if (a.getEquipe() != null) {
                aux += " Equipe: " + a.getEquipe().getCodinome() + "\n";
            } else {
                aux += "\n";
            }
        }

        return aux;
    }

    public String relatorioAtendimentos() {
        if (gerenciamento.getAtendimentos().isEmpty()) {
            throw new RuntimeException("Não há atendimentos cadastrados");
        }
        String aux = "Atendimentos\n\n";
        for (Atendimento a : gerenciamento.getAtendimentos()) {
            aux += "Código: " + a.getCod() + " Data de início: " + a.getDataInicio() + " Duração: " + a.getDuracao()
                    + " Status: " + a.getStatus() + "\nCódigo Evento: " + a.getEvento().getCodigo()
                    + " Data de início: "
                    + a.getEvento().getData() + "Latitude: " + a.getEvento().getLatitude() + "Longitude: "
                    + a.getEvento().getLongitude() + " Tipo: ";
            if (a.getEvento() instanceof Ciclone) {
                Ciclone c = (Ciclone) a.getEvento();
                aux += "Ciclone" + " Velocidade: " + c.getVelocidade() + " Precipitação: " + c.getPrecipitacao() + "\n";
            } else if (a.getEvento() instanceof Terremoto) {
                Terremoto t = (Terremoto) a.getEvento();
                aux += "Terremoto" + " Magnitude: " + t.getMagnitude() + "\n";
            } else {
                Seca s = (Seca) a.getEvento();
                aux += "Seca" + " Estiagem: " + s.getEstiagem() + "\n";
            }
            if (a.getEquipe() != null) {
                aux += "Codinome Equipe: " + a.getEquipe().getCodinome() + " Quantidade: "
                        + a.getEquipe().getQuantidade()
                        + " Latitude: "
                        + a.getEquipe().getLatitude() + " Longitude: " + a.getEquipe().getLongitude() + " Custo: R$ "
                        + String.format("%.2f", a.calculaCusto()) + "\n";
                for (Equipamento e : a.getEquipe().getEquipamentos()) {
                    aux += "ID Equipamento: " + e.getId() + " Nome: " + e.getNome() + " Custo por dia: R$"
                            + e.getCustoDia()
                            + " Tipo: ";
                    if (e instanceof Barco) {
                        Barco b = (Barco) e;
                        aux += "Barco" + " Capacidade: " + b.getCapacidade();
                    } else if (e instanceof CaminhaoTanque) {
                        CaminhaoTanque c = (CaminhaoTanque) e;
                        aux += "Caminhão Tanque" + " Capacidade: " + c.getCapacidade();
                    } else {
                        Escavadeira esc = (Escavadeira) e;
                        aux += "Escavadeira" + " Combustível: " + esc.getCombustivel() + " Carga: " + esc.getCarga();
                    }
                    aux += "\n";
                }
            } else {
                aux += "\n";
            }
            aux += "\n";
        }

        return aux;
    }

    public void alterarStatusAtendimento(int indexAtendimento, String status) {
        Atendimento atendimento = gerenciamento.procuraAtendimento(indexAtendimento);
        if (atendimento == null) {
            throw new RuntimeException("Atendimento não encontrado");
        }

        if (atendimento.getStatus().equals(status)) {
            return;
        }

        if (atendimento.getStatus().equals("FINALIZADO")) {
            throw new RuntimeException("Atendimento já está finalizado");
        }

        if (status.equals("PENDENTE")) {
            gerenciamento.atendimentoPendente(atendimento);
            atendimento.getEquipe().removeAtendimento(atendimento);
            atendimento.removeEquipe();
            atendimento.getEquipe().setAlocado(false);
        }
        if (status.equals("EXECUTANDO")) {
            for (Equipe e : gerenciamento.getEquipes()) {
                if (e.addAtendimento(atendimento)) {
                    atendimento.addEquipe(e);
                    break;
                }
            }
            if (atendimento.getEquipe() == null) {
                throw new RuntimeException("Não há equipes disponíveis");
            }
        }
        if (status.equals("FINALIZADO")) {
            atendimento.finalizarAtendimento();
            atendimento.getEquipe().setAlocado(false);
        }
        if (status.equals("CANCELADO")) {
            atendimento.cancelarAtendimento();
            atendimento.getEquipe().setAlocado(false);
        }
    }

    public void equipamentoParaEquipe(int indexEquipamento, int indexEquipe) {
        Equipamento equipamento = gerenciamento.getEquipamentos().get(indexEquipamento);
        Equipe equipe = gerenciamento.getEquipes().get(indexEquipe);
        if (equipamento.getEquipe() != null) {
            throw new RuntimeException("Equipamento já está alocado");
        }
        equipamento.setEquipe(equipe);
        equipe.addEquipamento(equipamento);
    }

    public ArrayList<String> consultaEquipes() {
        if (gerenciamento.getEquipes().isEmpty()) {
            throw new RuntimeException("Não há equipes cadastradas");

        }
        return (ArrayList<String>) gerenciamento.getEquipes().stream().map(e -> e.getCodinome())
                .collect(Collectors.toList());
    }

    public ArrayList<String> consultaEquipamentos() {
        if (gerenciamento.getEquipamentos().isEmpty()) {
            throw new RuntimeException("Não há equipamentos cadastrados");
        }
        return (ArrayList<String>) gerenciamento.getEquipamentos().stream().map(e -> e.getNome())
                .collect(Collectors.toList());
    }

    public void alocarAtendimentos() {
        if (!gerenciamento.alocarAtendimentos()) {
            throw new RuntimeException("Não há atendimentos pendentes");
        }
    }
}