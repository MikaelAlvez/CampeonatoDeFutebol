package Projeto;

class Jogadores {
    private String nome;
    private double nivel;
    private Time time;

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
    
    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String toString() {
        return nome + " (" + nivel + " estrelas)";
    }
}