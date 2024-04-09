package Projeto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Campeonato {
    private List<Time> times;
    private List<List<Time>> grupos;
    private List<List<Jogos>> jogosGrupos;
    private List<Jogos> todosJogos;
    private List<Jogadores> jogadores;
    
    public Campeonato() {
        times = new ArrayList<>();
        grupos = new ArrayList<>();
        jogosGrupos = new ArrayList<>();
        todosJogos = new ArrayList<>();
        jogadores = new ArrayList<>();
    }

    public void cadastrarTime(String nome) {
        times.add(new Time(nome));
    }
    
    public void adicionarJogador(Jogadores jogador) {
        jogadores.add(jogador);
    }
    
    public List<Time> getTimes() {
        return times;
    }
    
    static void sortearGrupos(Scanner scanner, Campeonato campeonato) {
        System.out.print("\nQuantidade de grupos: ");
        int numGrupos = scanner.nextInt();
        campeonato.sortearGrupos(numGrupos);
    }

    public void sortearGrupos(int numGrupos) {
        Collections.shuffle(times);
        int numTimesPorGrupo = times.size() / numGrupos;
        int numTimesRestantes = times.size() % numGrupos;
        int index = 0;
        for (int i = 0; i < numGrupos; i++) {
            List<Time> grupo = new ArrayList<>();
            for (int j = 0; j < numTimesPorGrupo && index < times.size(); j++) {
                grupo.add(times.get(index++));
            }
            if (numTimesRestantes > 0) {
                grupo.add(times.get(index++));
                numTimesRestantes--;
            }
            grupos.add(grupo);
        }
        System.out.println("\n-----TIMES-----");
        for (int i = 0; i < grupos.size(); i++) {
        	if(grupos.size()>1) {
            System.out.println("Grupo " + (i + 1) + ": " + grupos.get(i));
        	}else {
                System.out.println("Grupo único:");
                System.out.println(grupos.get(i) + "\n");
        	}
        }
        if(grupos.size()>1) {
        	System.out.print("\nSorteio realizado com sucesso!\n");
        }
    }

    public void gerarJogos() {
        for (List<Time> grupo : grupos) {
            List<Jogos> jogosGrupo = new ArrayList<>();
            for (int i = 0; i < grupo.size(); i++) {
                for (int j = i + 1; j < grupo.size(); j++) {
                    jogosGrupo.add(new Jogos(grupo.get(i), grupo.get(j), 0, 0));
                }
            }
            jogosGrupos.add(jogosGrupo);
        }
        mostrarJogos();
        System.out.print("\nJogos gerados com sucesso!\n");
    }

    public void mostrarJogos() {
        for (int i = 0; i < jogosGrupos.size(); i++) {
            List<List<Jogos>> jogosGrupo = jogosGrupos;
            if (grupos.size() > 1) {
                System.out.println("\n-----GRUPO " + (i + 1) + "-----");
            } else {
                System.out.println("\n-----TIMES-----");
            }
            for (int j = 0; j < jogosGrupo.get(i).size(); j++) {
                Jogos jogo = jogosGrupo.get(i).get(j);
                Time time1 = jogo.getTime1();
                Time time2 = jogo.getTime2();
                int placarTime1 = jogo.getGolsTime1();
                int placarTime2 = jogo.getGolsTime2();
                System.out.println(time1.getNome() + " " + placarTime1 + " x " + placarTime2 + " " + time2.getNome());
            }
        }
    }
    
    static void distribuirJogadoresNosTimes(Scanner scanner, Campeonato campeonato, List<Jogadores> jogadores) {
        System.out.print("Quantos jogadores em cada time? ");
        int numJogadoresPorTime = scanner.nextInt();
        scanner.nextLine();

        List<Time> times = campeonato.getTimes();
        int numTimes = times.size();
        int numJogadores = jogadores.size();

        if (numJogadores < numJogadoresPorTime * numTimes) {
            System.out.println("\nNão há jogadores suficientes para distribuir entre os times.");
        }
        
        System.out.println("\nNúmero de jogadores inscritos: " + numJogadores);
        System.out.println("Número de time inscritos: " + numTimes);
        System.out.println("Número de jogadores por time: " + numJogadoresPorTime);


        Collections.sort(jogadores, (j1, j2) -> Double.compare(j2.getNivel(), j1.getNivel()));

        int indiceJogador = 0;

        for (Time time : times) {
            System.out.println("\n----- " + time.getNome() + " -----");
            double somaNivelTime = 0.0;

            for (int i = 0; i < numJogadoresPorTime; i++) {
                if (indiceJogador < numJogadores) {
                    Jogadores jogadorAtual = jogadores.get(indiceJogador);
                    time.adicionarJogador(jogadorAtual);
                    somaNivelTime += jogadorAtual.getNivel();
                    System.out.println(jogadorAtual.getNome() + " (Nível: " + jogadorAtual.getNivel() + ")");
                    indiceJogador++;
                } else {
                    break;
                }
            }

            double mediaNivelTime = somaNivelTime / numJogadoresPorTime;
            String mediaFormatada = String.format("%.2f", mediaNivelTime);
            
            System.out.println("\nNível total do time: " + somaNivelTime);
            System.out.println("Média do nível do time: " + mediaFormatada);
        }
        System.out.println("\nJogador(es) adicionado(s) à fila de espera: ");
        while (indiceJogador < numJogadores) {
            Jogadores jogador = jogadores.get(indiceJogador);
            Jogadores.adicionarJogadorNaFila(jogador);
            System.out.println("Nome: " + jogador.getNome() + ", Nível: " + jogador.getNivel());
            indiceJogador++;
        }
        
        System.out.println("\nJogadores distribuídos nos times com sucesso!");
    }
    
    static void mostrarTabela(Campeonato campeonato) {
        List<Time> times = campeonato.getTimes();
        Tabela tabela = new Tabela(times);
        tabela.atualizarTabela(campeonato.getJogos());
        Tabela.mostrarTabela(campeonato);
    }
    
    public void escreverJogosEmArquivo(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (int i = 0; i < jogosGrupos.size(); i++) {
                List<List<Jogos>> jogosGrupo = jogosGrupos;
                if (grupos.size() > 1) {
                    writer.write("\n-----GRUPO " + (i + 1) + "-----\n");
                } else {
                    writer.write("\n-----TIMES-----\n");
                }
                for (int j = 0; j < jogosGrupo.get(i).size(); j++) {
                    Jogos jogo = jogosGrupo.get(i).get(j);
                    Time time1 = jogo.getTime1();
                    Time time2 = jogo.getTime2();
                    int placarTime1 = jogo.getGolsTime1();
                    int placarTime2 = jogo.getGolsTime2();
                    String linha = time1.getNome() + " " + placarTime1 + " x " + placarTime2 + " " + time2.getNome() + "\n";
                    writer.write(linha);
                }
            }
            System.out.println("\nArquivo " + nomeArquivo + ".txt gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("\nErro ao escrever o arquivo: " + e.getMessage());
        }
    }

    
    public List<List<Time>> getGrupos() {
        return grupos;
    }
    
    public Jogos getJogoPorIndice(int indice) {
        if (indice >= 0 && indice < todosJogos.size()) {
            return todosJogos.get(indice);
        }
        return null;
    }
    
    public List<Jogos> getJogos() {
        if (todosJogos == null) {
            todosJogos = new ArrayList<>();
            for (List<Jogos> jogosGrupo : jogosGrupos) {
                todosJogos.addAll(jogosGrupo);
            }
        }
        return todosJogos;
    }
    
    public void editarPlacarJogo() {
        Scanner scanner2 = new Scanner(System.in);

        Jogos jogo = null;
        int totalJogos = 0;
        
        System.out.print("Digite o índice do jogo: ");
        int indiceJogo = scanner2.nextInt();
        scanner2.nextLine();
        
        System.out.print("Placar do time 1: ");
        int placarTime1 = scanner2.nextInt();
        scanner2.nextLine();

        System.out.print("Placar do time 2: ");
        int placarTime2 = scanner2.nextInt();
        scanner2.nextLine();
       
        for (List<Jogos> jogosGrupo : jogosGrupos) {
            totalJogos += jogosGrupo.size();
        }

        int contador = 0;
        outerloop:
        for (List<Jogos> jogosGrupo : jogosGrupos) {
            for (Jogos j : jogosGrupo) {
                if (contador == indiceJogo-1) {
                    jogo = j;
                    break outerloop;
                }
                contador++;
            }
        }
        
        if (jogo != null) {
            jogo.setGolsTime1(placarTime1);
            jogo.setGolsTime2(placarTime2);
            System.out.println("\nPlacar do jogo atualizado com sucesso!");
        } else {
            System.out.println("\nÍndice de jogo inválido.");
        }
    }

}