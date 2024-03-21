package Projeto;

import java.util.Objects;

public class Jogos {
    private Time time1;
    private Time time2;
    private int golsTime1;
    private int golsTime2;

    public Jogos(Time time1, Time time2, int golsTime1, int golsTime2) {
        this.time1 = time1;
        this.time2 = time2;
        this.golsTime1 = 0;
        this.golsTime2 = 0;
    }

    public Time getTime1() {
        return time1;
    }

    public Time getTime2() {
        return time2;
    }

    public int getGolsTime1() {
        return golsTime1;
    }

    public void setGolsTime1(int golsTime1) {
        this.golsTime1 = golsTime1;
    }

    public int getGolsTime2() {
        return golsTime2;
    }

    public void setGolsTime2(int golsTime2) {
        this.golsTime2 = golsTime2;
    }

    public int getGolsMarcados(Time time) {
        if (Objects.equals(time, time1)) {
            return golsTime1;
        } else if (Objects.equals(time, time2)) {
            return golsTime2;
        } else {
            throw new IllegalArgumentException("O time especificado não está envolvido neste jogo.");
        }
    }

    public int getGolsSofridos(Time time) {
        if (Objects.equals(time, time1)) {
            return golsTime2;
        } else if (Objects.equals(time, time2)) {
            return golsTime1;
        } else {
            throw new IllegalArgumentException("O time especificado não está envolvido neste jogo.");
        }
    }

    public Time getVencedor() {
        if (golsTime1 > golsTime2) {
            return time1;
        } else if (golsTime2 > golsTime1) {
            return time2;
        } else {
            return null;
        }
    }
    
    public void editarPlacar(int golsTime1, int golsTime2) {
        this.golsTime1 = golsTime1;
        this.golsTime2 = golsTime2;
    }

    @Override
    public String toString() {
        return time1.getNome() + " X " + time2.getNome();
    }
}

