package Projeto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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
            		"0. Sair\n" +
            		"Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();
            System.out.println("-----------------------------------");

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
                    System.out.println("\n1. Cadastrar jogador\n" +
                    		"2. Dados de um arquivo\n" +
                    		"Escolha uma opção:");
                    int opc = scanner.nextInt();
                    
                    switch (opc) {
	                    case 1:
	                        cadastrarJogadores(scanner, campeonato, jogadores);
	                    break;
	                	case 2:
	                		System.out.print("Digite o nome do arquivo: ");
	                        scanner.nextLine();
	                        String nomeArquivo = scanner.nextLine();

	                        nomeArquivo = Paths.get("").toAbsolutePath().toString() + File.separator + nomeArquivo;
	                        
						lerJogadoresDoArquivo(nomeArquivo, jogadores, campeonato);

						for (Jogadores jogador : jogadores) {
						    System.out.println("Nome: " + jogador.getNome() + ", Nível: " + jogador.getNivel());
						}
	                    break;
				        default:
				            System.out.println("\nOpção inválida. Por favor, escolha uma opção válida.");
                    }
                    break;
                case 5:
                    distribuirJogadoresNosTimes(scanner, campeonato, jogadores);
                    break;
                case 6:
                    mostrarTabela(campeonato);
                    break;
                case 7:
                    campeonato.mostrarJogos();
                    editarPlacar(scanner, campeonato);
                    campeonato.mostrarJogos();
                    mostrarTabela(campeonato);
                    break;
                case 8:
                    mostrarArtilharia(campeonato);
                    break;
                case 9:
                	buscarJogador(jogadores);
                	break;
                case 10:
                	listarJogador(jogadores);
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

    private static void cadastrarJogadores(Scanner scanner, Campeonato campeonato, List<Jogadores> jogadores) {
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
    
    private static void lerJogadoresDoArquivo(String nomeArquivo, List<Jogadores> jogadores, Campeonato campeonato) {
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
    
    private static void distribuirJogadoresNosTimes(Scanner scanner, Campeonato campeonato, List<Jogadores> jogadores) {
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

    private static void mostrarTabela(Campeonato campeonato) {
        List<Time> times = campeonato.getTimes();
        Tabela tabela = new Tabela(times);
        tabela.atualizarTabela(campeonato.getJogos());
        Tabela.mostrarTabela(campeonato);
    }
    
    private static void editarPlacar(Scanner scanner, Campeonato campeonato) {
        List<List<Time>> grupos = campeonato.getGrupos();
        
        System.out.println("\n");
        for (int i = 0; i < grupos.size(); i++) {
            System.out.println((i + 1) + ". Grupo " + (i + 1));
        }
        System.out.println("Escolha o grupo:");
        int indiceGrupo = scanner.nextInt();
        scanner.nextLine();
        
        if (indiceGrupo >= 1 && indiceGrupo <= grupos.size()) {
            List<Time> grupoSelecionado = grupos.get(indiceGrupo - 1);
            
            System.out.println("\n-----GRUPO " + indiceGrupo + "-----");
            int count = 1;
            for (int i = 0; i < grupoSelecionado.size(); i++) {
                for (int j = i + 1; j < grupoSelecionado.size(); j++) {
                    System.out.println(count + ". Jogo " + count + ": " + grupoSelecionado.get(i).getNome() + " x " + grupoSelecionado.get(j).getNome());
                    count++;
                }
            }            
            
            System.out.println("Escolha o jogo:");
            int indiceJogo = scanner.nextInt();
            scanner.nextLine();
            
            if (indiceJogo >= 1 && indiceJogo <= (grupoSelecionado.size() * (grupoSelecionado.size() - 1) / 2)) {
                int index = 1;
                for (int i = 0; i < grupoSelecionado.size(); i++) {
                    for (int j = i + 1; j < grupoSelecionado.size(); j++) {
                        if (index == indiceJogo) {
                            System.out.println("Gols " + grupoSelecionado.get(i).getNome() + ":");
                            int novoPlacarTime1 = scanner.nextInt();
                            System.out.println("Gols " + grupoSelecionado.get(j).getNome() + ":");
                            int novoPlacarTime2 = scanner.nextInt();
                            
                            campeonato.editarPlacarJogo(indiceJogo - 1, novoPlacarTime1, novoPlacarTime2);
                            return;
                        }
                        index++;
                    }
                }
            } else {
                System.out.println("Índice de jogo inválido.");
            }
        } else {
            System.out.println("Índice de grupo inválido.");
        }
    }

    
    private static void mostrarArtilharia(Campeonato campeonato) {
    	
    }
    
    private static void buscarJogador(List<Jogadores> jogadores) {
        Scanner scanner = new Scanner(System.in);
        
        Collections.sort(jogadores, (j1, j2) -> j1.getNome().compareToIgnoreCase(j2.getNome()));
        
        System.out.print("\nNome do jogador: ");
        String nomeBusca = scanner.nextLine().trim();
        
        int indice = buscaBinaria(jogadores, nomeBusca);
        
        if (indice != -1) {
            Jogadores jogadorEncontrado = jogadores.get(indice);
            
            System.out.println("\nJogador encontrado!" +
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
    
    private static void listarJogador(List<Jogadores> jogadores) {
    	
    }
}
