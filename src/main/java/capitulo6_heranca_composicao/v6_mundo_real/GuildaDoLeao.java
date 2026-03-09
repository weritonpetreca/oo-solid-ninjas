package capitulo6_heranca_composicao.v6_mundo_real;

import java.util.List;

/**
 * Guilda do Leão — gerencia caçadores e distribui contratos.
 *
 * ✅ LSP garantido: itera List<Cacador> e chama calcularRecompensaBruta()
 *    em todos sem risco de exceção — qualquer subclasse honra o contrato
 * ✅ Composição: recebe List<Cacador> — plugável, extensível
 */
public class GuildaDoLeao {

    public void imprimirRelatorio(List<Cacador> cacadores, ContratoDeCaca contrato) {
        System.out.println("=".repeat(55));
        System.out.println("  GUILDA DO LEÃO - RELATÓRIO DE RECOMPENSAS");
        System.out.printf("  Contrato: %s (Perigo: %d)%n",
                contrato.getMonstro(), contrato.getNivelDePerigo());
        System.out.println("=".repeat(55));

        double totalBruto = 0;
        for (Cacador c: cacadores) {
            double bruta = c.calcularRecompensaBruta(contrato);
            double liquida = c.calcularRecompensaLiquida(contrato);
            System.out.printf("%-20s | Bruta: R$%7.2f | Líquida: R$%7.2f%n",
                    c.getNome(), bruta, liquida);
            totalBruto += bruta;
        }

        System.out.println("-".repeat(55));
        System.out.printf("  Total bruto desembolsado: R$%.2f%n", totalBruto);
        System.out.println("-".repeat(55));
    }
}
