package Projeto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Campeonato {
    private List<Time> times;
    private List<List<Time>> grupos;
    private List<List<Jogos>> jogosGrupos;

    public Campeonato() {
        times = new ArrayList<>();
        grupos = new ArrayList<>();
        jogosGrupos = new ArrayList<>();
    }

    public void cadastrarTime(String nome) {
        times.add(new Time(nome));
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
                System.out.println(grupos.get(i) + "\n");
        	}
        }
    }

    public void gerarJogos() {
        for (List<Time> grupo : grupos) {
            List<Jogos> jogosGrupo = new ArrayList<>();
            for (int i = 0; i < grupo.size(); i++) {
                for (int j = i + 1; j < grupo.size(); j++) {
                    jogosGrupo.add(new Jogos(grupo.get(i), grupo.get(j)));
                }
            }
            jogosGrupos.add(jogosGrupo);
        }
    }

    public void mostrarJogos() {
        for (int i = 0; i < jogosGrupos.size(); i++) {
        	if(grupos.size()>1) {
	            List<List<Jogos>> jogosGrupo = jogosGrupos;
	            System.out.println("\n-----GRUPO " + (i + 1) + "-----");
	            for (int j = 0; j < jogosGrupo.get(i).size(); j++) {
	                System.out.println(jogosGrupo.get(i).get(j));
	            }
        	}else {
        		List<List<Jogos>> jogosGrupo = jogosGrupos;
	            System.out.println("\n-----TIMES-----");
	            for (int j = 0; j < jogosGrupo.get(i).size(); j++) {
	                System.out.println(jogosGrupo.get(i).get(j));
	            }
        	}
        }
    } 
}