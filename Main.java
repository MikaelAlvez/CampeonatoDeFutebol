package Projeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Defina o Locale para usar ponto como separador decimal
        Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
        Campeonato campeonato = new Campeonato();

        System.out.println("-----CAMPEONATO DE FUTEBOL-----");

        System.out.print("Quantos times terá a competição? ");
        int numTimes = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        System.out.println("\n-----CADASTRO DE TIMES-----");
        for (int i = 0; i < numTimes; i++) {
            System.out.print("Time " + (i + 1) + ": ");
            String nomeTime = scanner.nextLine();
            campeonato.cadastrarTime(nomeTime);
        }

        System.out.print("\nQuantidade de grupos: ");
        int numGrupos = scanner.nextInt();

        campeonato.sortearGrupos(numGrupos);

        campeonato.gerarJogos();
        campeonato.mostrarJogos();
        
        System.out.print("\nQuantos jogadores deseja cadastrar? ");
        int numJogadores = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner

        List<Jogadores> jogadores = new ArrayList<>();
        for (int i = 0; i < numJogadores; i++) {
            System.out.print("Nome do jogador " + (i + 1) + ": ");
            String nomeJogador = scanner.nextLine();
            System.out.print("Nível do jogador " + (i + 1) + " (de 0 a 10): ");
            double nivelJogador = scanner.nextDouble();
            scanner.nextLine(); // Limpar o buffer do scanner
            jogadores.add(new Jogadores(nomeJogador, nivelJogador));
        }

        System.out.print("Quantos jogadores em cada time? ");
        int numJogadoresPorTime = scanner.nextInt();

        List<Time> times = new ArrayList<>();
        int numTimesJogadores = numJogadores / numJogadoresPorTime;
        for (int i = 0; i < numTimesJogadores; i++) {
            times.add(new Time());
        }

        // Ordena os jogadores por nível
        Collections.sort(jogadores, (j1, j2) -> Double.compare(j2.getNivel(), j1.getNivel()));

        // Distribui os jogadores nos times de forma balanceada
        int indiceTime = 0;
        for (Jogadores jogador : jogadores) {
            Time timeAtual = times.get(indiceTime);
            timeAtual.adicionarJogador(jogador);
            indiceTime = (indiceTime + 1) % numTimesJogadores; // Avança para o próximo time
        }

        // Mostra os times e seus jogadores
        System.out.println("\n-----TIMES E JOGADORES-----");
        for (int i = 0; i < times.size(); i++) {
            Time time = times.get(i);
            System.out.println("\nTime " + (i + 1) + ":");
            System.out.println("Total de Nível: " + time.getTotalNivel());
            for (Jogadores jogador : time.getJogadores()) {
                System.out.println(jogador);
            }
        }
    }
}