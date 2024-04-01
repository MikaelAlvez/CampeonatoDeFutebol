package Projeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    
    public void editarPlacarJogo(int indiceJogo, int novoPlacarTime1, int novoPlacarTime2) {
        Jogos jogo = null;
        outerloop:
        for (List<Jogos> jogosGrupo : jogosGrupos) {
            for (Jogos j : jogosGrupo) {
                if (indiceJogo == 0) {
                    jogo = j;
                    break outerloop;
                }
                indiceJogo--;
            }
        }
        
        if (jogo != null) {
            jogo.setGolsTime1(novoPlacarTime1);
            jogo.setGolsTime2(novoPlacarTime2);
            System.out.println("Placar do jogo atualizado com sucesso!");
        } else {
            System.out.println("Índice de jogo inválido.");
        }
    }
}