package Projeto;

import java.util.ArrayList;
import java.util.List;

class Time {
    private List<Jogadores> jogadores;
    
    private String nome;
    
    public Time(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
    public Time() {
        jogadores = new ArrayList<>();
    }

    public void adicionarJogador(Jogadores jogador) {
        jogadores.add(jogador);
    }

    public List<Jogadores> getJogadores() {
        return jogadores;
    }

    public int getTotalNivel() {
        int total = 0;
        for (Jogadores jogador : jogadores) {
            total += jogador.getNivel();
        }
        return total;
    }
    
    public String toString() {
        return nome;
    }
}