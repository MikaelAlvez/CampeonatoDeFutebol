package Projeto;

class Jogos {
    private Time time1;
    private Time time2;

    public Jogos(Time time1, Time time2) {
        this.time1 = time1;
        this.time2 = time2;
    }

    public Time getTime1() {
        return time1;
    }

    public Time getTime2() {
        return time2;
    }

    public String toString() {
        return time1.getNome() + " x " + time2.getNome();
    }
}
