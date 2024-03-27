package Projeto;

import java.util.ArrayList;
import java.util.List;

public class Time {
    private String nome;
    private int pontos;
    private int vitorias;
    private int empate;
    private int derrotas;
    private int golsMarcados;
    private int golsSofridos;
    private int saldoGols;
    private List<Jogadores> jogadores;

    public Time(String nome) {
    	this.nome = nome;
        this.pontos = 0;
        this.vitorias = 0;
        this.golsMarcados = 0;
        this.golsSofridos = 0;
        this.saldoGols = golsMarcados-golsSofridos;
        this.jogadores = new ArrayList<>();
    }

    public void adicionarJogador(Jogadores jogador) {
        jogadores.add(jogador);
    }

    public List<Jogadores> getJogadores() {
        return jogadores;
    }
    
    public void mostrarJogadores() {
        if (jogadores.isEmpty()) {
            System.out.println("Sem jogadores cadastrados para o time " + nome);
        } else {
            System.out.println("Jogadores do time " + nome + ":");
            for (Jogadores jogador : jogadores) {
                System.out.println("Nome: " + jogador.getNome() + ", Time: " + nome + ", Nível: " + jogador.getNivel());
            }
        }
    }

    public double getTotalNivel() {
        double total = 0;
        for (Jogadores jogador : jogadores) {
            total += jogador.getNivel();
        }
        return total;
    }

    public String getNome() {
        return nome;
    }

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public int getVitorias() {
		return vitorias;
	}

	public void setVitorias(int vitorias) {
		this.vitorias = vitorias;
	}

	public int getGolsMarcados() {
		return golsMarcados;
	}

	public void setGolsMarcados(int golsMarcados) {
		this.golsMarcados = golsMarcados;
	}

	public int getGolsSofridos() {
		return golsSofridos;
	}

	public void setGolsSofridos(int golsSofridos) {
		this.golsSofridos = golsSofridos;
	}

	public int getSaldoGols() {
		return getGolsMarcados()-getGolsSofridos();
	}

	public void setSaldoGols(int saldoGols) {
		this.saldoGols = saldoGols;
	}
	
	public void adicionarGolsMarcados(int gols) {
        this.golsMarcados += gols;
    }
	
	public void adicionarGolsSofridos(int gols) {
        this.golsSofridos += gols;
    }
	
	public void incrementarPontos(int pontos) {
        this.pontos += pontos;
    }
	
	public void incrementarVitorias() {
        this.vitorias++;
    }
	
	@Override
    public String toString() {
        return nome;
    }

	public int getEmpate() {
		return empate;
	}

	public void setEmpate(int empate) {
		this.empate = empate;
	}

	public int getDerrotas() {
		return derrotas;
	}

	public void setDerrotas(int derrotas) {
		this.derrotas = derrotas;
	}
}
