package Projeto;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
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
            System.out.println("\n1. Cadastrar Times\n" +
            		"2. Sortear Grupos\n" +
            		"3. Gerar Jogos\n" +
            		"4. Cadastrar Jogadores\n" +
            		"5. Distribuir Jogadores nos Times\n" +
            		"6. Tabela\n" +
            		"7. Editar Placar\n" +
            		"8. Artilharia\n" +
            		"9. Buscar Jogador\n" +
            		"10. Listar Jogadores\n" +
            		//"11. Excluir Jogador\n" +
            		"12. Gerar arquivo de jogos\n" +
            		"0. Sair\n" +
            		"Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println("-----------------------------------");

            switch (opcao) {
                case 1:
                    Time.cadastrarTimes(scanner, campeonato);
                    break;
                case 2:
                    Campeonato.sortearGrupos(scanner, campeonato);
                    break;
                case 3:
                    campeonato.gerarJogos();
                    break;
                case 4:
                    System.out.println("\n1. Cadastrar jogador\n" +
                    		"2. Dados de um arquivo\n" +
                    		"Escolha uma opção:");
                    int opc = scanner.nextInt();
                    
                    switch (opc) {
	                    case 1:
	                        Jogadores.cadastrarJogadores(scanner, campeonato, jogadores);
	                    break;
	                	case 2:
	                		System.out.print("Digite o nome do arquivo: ");
	                        scanner.nextLine();
	                        String nomeArquivoLeitura = scanner.nextLine();

	                        nomeArquivoLeitura = Paths.get("").toAbsolutePath().toString() + File.separator + nomeArquivoLeitura;
	                        
						Jogadores.lerJogadoresDoArquivo(nomeArquivoLeitura, jogadores, campeonato);

						for (Jogadores jogador : jogadores) {
						    System.out.println("Nome: " + jogador.getNome() + ", Nível: " + jogador.getNivel());
						}
	                    break;
				        default:
				            System.out.println("\nOpção inválida. Por favor, escolha uma opção válida.");
                    }
                    break;
                case 5:
                	Jogadores.distribuirJogadoresNosTimes(scanner, campeonato, jogadores);
                    break;
                case 6:
                    Campeonato.mostrarTabela(campeonato);
                    break;
                case 7:
                    campeonato.mostrarJogos();
                    campeonato.editarPlacarJogo();
                    campeonato.mostrarJogos();
                    break;
                case 8:
                	Jogadores.mostrarArtilharia(campeonato);
                    break;
                case 9:
                	Jogadores.buscarJogador(jogadores);
                	break;
                case 10:
                	Jogadores.listarJogador(jogadores);
                	break;
                case 11:
                	
                	break;
                case 12:
                    System.out.println("Informe o nome que deseja salvar o arquivo: ");
                    String nomeArquivoEscrita = scanner.nextLine();
                	campeonato.escreverJogosEmArquivo(nomeArquivoEscrita); 
                	break;
                case 0:
                    System.out.println("Saindo do programa...");
                    break;
                default:
                    System.out.println("\nOpção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 0);
    }
}
