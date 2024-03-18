package Projeto;

class Jogadores {
    private String nome;
    private double nivel;

    public Jogadores(String nome, double nivel) {
        this.nome = nome;
        this.nivel = nivel;
    }

    public String getNome() {
        return nome;
    }

    public double getNivel() {
        return nivel;
    }

    public String toString() {
        return nome + " (" + nivel + " estrelas)";
    }
}