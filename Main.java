package Projeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Campeonato campeonato = new Campeonato();
        List<Jogadores> jogadores = new ArrayList<>();
        
        System.out.println("-----CAMPEONATO DE FUTEBOL-----");

        int opcao;
        do {
            System.out.println("\n----- MENU -----");
            System.out.println("1. Cadastrar Times");
            System.out.println("2. Sortear Grupos");
            System.out.println("3. Gerar Jogos");
            System.out.println("4. Cadastrar Jogadores");
            System.out.println("5. Distribuir Jogadores nos Times");
            System.out.println("6. Times e Jogadores");
            System.out.println("7. Tabela");
            System.out.println("8. Jogos");
            System.out.println("9. Artilharia");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarTimes(scanner, campeonato);
                    break;
                case 2:
                    sortearGrupos(scanner, campeonato);
                    break;
                case 3:
                    campeonato.gerarJogos();
                    break;
                case 4:
                    cadastrarJogadores(scanner, campeonato);
                    break;
                case 5:
                    distribuirJogadoresNosTimes(scanner, campeonato, jogadores);
                    break;
                case 6:
                    mostrarTimesEJogadores(campeonato.getTimes());
                    break;
                case 7:
                    mostrarTabela(campeonato);
                    break;
                case 8:
                    campeonato.mostrarJogos();
                    editarPlacar(scanner, campeonato);
                    break;
                case 9:
                    mostrarArtilharia(campeonato);
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("\nOpção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarTimes(Scanner scanner, Campeonato campeonato) {
        System.out.print("\nQuantos times deseja cadastrar? ");
        int numTimes = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n-----CADASTRO DE TIMES-----");
        for (int i = 0; i < numTimes; i++) {
            System.out.print("Time " + (i + 1) + ": ");
            String nomeTime = scanner.nextLine();
            campeonato.cadastrarTime(nomeTime);
        }
        
        System.out.print("\nTimes cadastrados com sucesso!\n");
        List<Time> timesCadastrados = campeonato.getTimes();
        List<String> nomesTimes = new ArrayList<>();
        for (Time time : timesCadastrados) {
            nomesTimes.add(time.getNome());
        }
        System.out.println(nomesTimes);
    }

    private static void sortearGrupos(Scanner scanner, Campeonato campeonato) {
        System.out.print("\nQuantidade de grupos: ");
        int numGrupos = scanner.nextInt();
        campeonato.sortearGrupos(numGrupos);
    }

    private static void cadastrarJogadores(Scanner scanner, Campeonato campeonato) {
    	 System.out.print("\nQuantos jogadores deseja cadastrar? ");
         int numJogadores = scanner.nextInt();
         scanner.nextLine();

         List<Jogadores> jogadores = new ArrayList<>();
         for (int i = 0; i < numJogadores; i++) {
             System.out.print("Nome do jogador " + (i + 1) + ": ");
             String nomeJogador = scanner.nextLine();
             System.out.print("Nível do jogador " + (i + 1) + " (de 0 a 10): ");
             double nivelJogador = scanner.nextDouble();
             scanner.nextLine();
             jogadores.add(new Jogadores(nomeJogador, nivelJogador));
         }    
         
         System.out.print("\nJogadores cadastrados com sucesso!\n");

    }

    private static void distribuirJogadoresNosTimes(Scanner scanner, Campeonato campeonato, List<Jogadores> jogadores) {
        System.out.print("Quantos jogadores em cada time? ");
        int numJogadoresPorTime = scanner.nextInt();
        int numJogadores = jogadores.size();
        
        List<Time> times = new ArrayList<>();
        int numTimesJogadores = numJogadores / numJogadoresPorTime;
        for (int i = 0; i < numTimesJogadores; i++) {
            times.add(new Time("Time " + (i + 1)));
        }

        Collections.sort(jogadores, (j1, j2) -> Double.compare(j2.getNivel(), j1.getNivel()));

        int indiceTime = 0;
        for (Jogadores jogador : jogadores) {
            Time timeAtual = times.get(indiceTime);
            timeAtual.adicionarJogador(jogador);
            indiceTime = (indiceTime + 1) % numTimesJogadores;
        }   
        
        System.out.print("\nJogadores sorteados!\nConsulte a opção 6 para verificar jogadores por times\n");
    }


    
    private static void mostrarTimesEJogadores(List<Time> times) {
        System.out.println("\n-----TIMES E JOGADORES-----");
        for (int i = 0; i < times.size(); i++) {
            Time time = times.get(i);
            System.out.println("\nTime " + (i + 1) + ":");
            System.out.println("Total de Nível: " + time.getTotalNivel());
            
            System.out.println("Jogadores:");
            for (Jogadores jogador : time.getJogadores()) {
                System.out.println("Nome: " + jogador.getNome() + ", Nível: " + jogador.getNivel());
            }
        }
    }

    private static void mostrarTabela(Campeonato campeonato) {
        List<Time> times = campeonato.getTimes();
        Tabela tabela = new Tabela(times);
        tabela.atualizarTabela(campeonato.getJogos());
        tabela.mostrarTabela();
    }
    
    private static void editarPlacar(Scanner scanner, Campeonato campeonato) {
        System.out.println("Digite o índice do jogo que deseja editar:");
        int indiceJogo = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("Digite o novo placar para o Time 1:");
        int novoPlacarTime1 = scanner.nextInt();
        System.out.println("Digite o novo placar para o Time 2:");
        int novoPlacarTime2 = scanner.nextInt();
        
        campeonato.editarPlacarJogo(indiceJogo, novoPlacarTime1, novoPlacarTime2);
    }

    private static void mostrarArtilharia(Campeonato campeonato) {
        // Implemente a lógica para mostrar a artilharia do campeonato
    }
}
