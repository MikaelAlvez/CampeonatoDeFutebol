package Projeto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Campeonato campeonato = new Campeonato();

        System.out.println("-----SISTEMA DE CAMPEONATO DE FUTEBOL-----\n");

        System.out.print("Nome do campeonato: ");
        String nomeCampeonato = scanner.nextLine();

        System.out.print("\nQuantos times terá a competição? ");
        int numTimes = scanner.nextInt();
        scanner.nextLine(); // Limpar o buffer do scanner
        
        System.out.println("\n-----CADASTRO DE TIMES-----\n");
        for (int i = 0; i < numTimes; i++) {
            System.out.print("Time " + (i + 1) + ": ");
            String nomeTime = scanner.nextLine();
            campeonato.cadastrarTime(nomeTime);
        }

        System.out.print("\nQuantos grupos terá o campeonato? ");
        int numGrupos = scanner.nextInt();

        campeonato.sortearGrupos(numGrupos);

        campeonato.gerarJogos();
        campeonato.mostrarJogos();
    }
}