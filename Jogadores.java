package Projeto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Jogadores {
    private String nome;
    private double nivel;
    private Jogadores proximo;
    private Time time;

    public Jogadores(String nome, double nivel) {
        this.nome = nome;
        this.nivel = nivel;
    }
    
    static void cadastrarJogadores(Scanner scanner, Campeonato campeonato, List<Jogadores> jogadores) {
        System.out.print("\nQuantos jogadores deseja cadastrar? ");
        int numJogadores = scanner.nextInt();
        scanner.nextLine();
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        for (int i = 0; i < numJogadores; i++) {
            System.out.print("\nNome do jogador " + (i + 1) + ": ");
            String nomeJogador = scanner.nextLine();
            System.out.print("Nível do jogador " + (i + 1) + " (de 0 a 10): ");
            double nivelJogador = Double.parseDouble(df.format(scanner.nextDouble()));
            
            scanner.nextLine();
            Jogadores jogador = new Jogadores(nomeJogador, nivelJogador);
            jogadores.add(jogador);
            campeonato.adicionarJogador(jogador);
        }


        System.out.print("\nJogadores cadastrados com sucesso!\n");
    }
    
    static void lerJogadoresDoArquivo(String nomeArquivo, List<Jogadores> jogadores, Campeonato campeonato) {
        try {
            File arquivo = new File(nomeArquivo);
            FileReader fr = new FileReader(arquivo);
            BufferedReader br = new BufferedReader(fr);

            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" ");
                if (partes.length >= 2) {
                    String nomeJogador = partes[0];
                    double nivelJogador = Double.parseDouble(partes[1]);
                    Jogadores jogador = new Jogadores(nomeJogador, nivelJogador);
                    jogadores.add(jogador);
                    campeonato.adicionarJogador(jogador);
                }
            }

            br.close();
            fr.close();
        } catch (IOException e) {
            System.err.println("\nErro ao ler o arquivo: " + e.getMessage());
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

        System.out.println("\nJogadores distribuídos nos times com sucesso!");
    }
    
	static void mostrarArtilharia(Campeonato campeonato) {
	    	
	}
	
	static void buscarJogador(List<Jogadores> jogadores) {
        Scanner scanner = new Scanner(System.in);
        
        Collections.sort(jogadores, (j1, j2) -> j1.getNome().compareToIgnoreCase(j2.getNome()));
        
        System.out.print("\nNome do jogador: ");
        String nomeBusca = scanner.nextLine().trim();
        
        int indice = buscaBinaria(jogadores, nomeBusca);
        
        if (indice != -1) {
            Jogadores jogadorEncontrado = jogadores.get(indice);
            
            System.out.println("\nJogador encontrado!\n" +
            		"\nNome: " + jogadorEncontrado.getNome() +
            		"\nNível: " + jogadorEncontrado.getNivel());
        } else {
            System.out.println("\nJogador não encontrado!");
        }
    }

    private static int buscaBinaria(List<Jogadores> jogadores, String nomeBusca) {
        int inicio = 0;
        int fim = jogadores.size() - 1;
        
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            String nomeAtual = jogadores.get(meio).getNome();
            
            if (nomeAtual.equalsIgnoreCase(nomeBusca)) {
                return meio;
            } else if (nomeAtual.compareToIgnoreCase(nomeBusca) < 0) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        
        return -1;
    }
    
    static void listarJogador(List<Jogadores> jogadores) {
        quickSort(jogadores, 0, jogadores.size() - 1);
        
        System.out.println("\n-----Lista de Jogadores (Ordenada por Nível)-----");
        for (Jogadores jogador : jogadores) {
            System.out.println("Nome: " + jogador.getNome() + ", Nível: " + jogador.getNivel());
        }
    }

    private static void quickSort(List<Jogadores> jogadores, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionar(jogadores, inicio, fim);
            quickSort(jogadores, inicio, indicePivo - 1);
            quickSort(jogadores, indicePivo + 1, fim);
        }
    }

    private static int particionar(List<Jogadores> jogadores, int inicio, int fim) {
        double pivo = jogadores.get(fim).getNivel();
        int indicePivo = inicio - 1;
        
        for (int j = inicio; j < fim; j++) {
            if (jogadores.get(j).getNivel() >= pivo) {
                indicePivo++;
                Jogadores temp = jogadores.get(indicePivo);
                jogadores.set(indicePivo, jogadores.get(j));
                jogadores.set(j, temp);
            }
        }
        
        Jogadores temp = jogadores.get(indicePivo + 1);
        jogadores.set(indicePivo + 1, jogadores.get(fim));
        jogadores.set(fim, temp);
        
        return indicePivo + 1;
    }

    public String getNome() {
        return nome;
    }

    public double getNivel() {
        return nivel;
    }
    
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String toString() {
        return nome + " (" + nivel + " estrelas)";
    }

    public Jogadores getProximo() {
        return this.proximo;
    }

	public void setProximo(Jogadores proximo) {
		this.proximo = proximo;
	}
}