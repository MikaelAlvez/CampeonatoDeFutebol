package Projeto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Tabela {
    private List<Time> times;

    public Tabela(List<Time> times) {
        this.times = times;
    }

    public void atualizarTabela(List<Jogos> jogos) {
        for (Jogos jogo : jogos) {
            Time time1 = jogo.getTime1();
            Time time2 = jogo.getTime2();
            int golsTime1 = jogo.getGolsTime1();
            int golsTime2 = jogo.getGolsTime2();

            time1.adicionarGolsMarcados(golsTime1);
            time1.adicionarGolsSofridos(golsTime2);
            time2.adicionarGolsMarcados(golsTime2);
            time2.adicionarGolsSofridos(golsTime1);

            Time vencedor = jogo.getVencedor();
            if (vencedor != null) {
                vencedor.incrementarPontos(3);
                vencedor.incrementarVitorias();
            } else {
                time1.incrementarPontos(1);
                time2.incrementarPontos(1);
            }
        }
        ordenarTabela();
    }
    
    private void ordenarTabela() {
        Collections.sort(times, Comparator.comparing(Time::getPontos)
                                           .thenComparing(Time::getVitorias)
                                           .thenComparing(Time::getSaldoGols)
                                           .reversed());
    }

    public void mostrarTabela() {
        System.out.println("\nPos | Time           | P | V | GM | GC | SG");
        System.out.println("----------------------------------------------");
        int posicao = 1;
        for (Time time : times) {
            System.out.printf("%-4d| %-15s | %-2d| %-2d| %-3d| %-3d| %-3d\n", posicao++, time.getNome(), time.getPontos(), time.getVitorias(),
                    time.getGolsMarcados(), time.getGolsSofridos(), time.getSaldoGols());
        }
    }
}
