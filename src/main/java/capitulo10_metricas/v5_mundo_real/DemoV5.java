package capitulo10_metricas.v5_mundo_real;

import java.util.List;

public class DemoV5 {

    public static void executar() {
        // Simulação de uma análise real das classes criadas ao longo do repositório
        // Os números refletem aproximações das métricas reais de cada classe
        var classes = List.of(
                // Classes bem escritas — deveriam estar em conformidade
                new AnaliseDeClasse("ContratoDeCaca",              2,  3,  3, 3, 0.0,  0, 12),
                new AnaliseDeClasse("TaxaDaGuilda",                1,  1,  3, 1, 0.0,  0, 10),
                new AnaliseDeClasse("FinanceiroDeGuilda",          2,  2,  3, 2, 0.05, 0,  3),
                new AnaliseDeClasse("ReputacaoDeCacador",          3,  3,  4, 1, 0.10, 0,  2),
                new AnaliseDeClasse("ProcessadorDeMissao",         3,  0,  2, 2, 0.0,  0,  4),
                new AnaliseDeClasse("BruxoEstrategista",           1,  0,  1, 2, 0.0,  1,  3),
                new AnaliseDeClasse("Missao (Cap. 9)",             4,  4,  6, 4, 0.20, 0,  8),

                // Classes problemáticas — métricas acima do limite
                new AnaliseDeClasse("GerenciadorCentralComAltoCE", 5,  3,  5, 4, 0.30, 5,  1),
                new AnaliseDeClasse("GuildaComDuasResponsab.",      4,  5,  5, 2, 0.82, 0,  1),
                new AnaliseDeClasse("EstrategiasCacaComAltaCC",    12,  0,  2, 3, 0.0,  3,  2),
                new AnaliseDeClasse("RegistroDeGuildaGigante",      7,  9, 12, 9, 0.75, 4,  1)
        );

        var limites  = new LimitesDeQualidade();
        var relatorio = new RelatorioDeQualidade(limites);
        relatorio.imprimir(classes);

        System.out.println();
        System.out.println("  Limites configurados pela Guilda:");
        System.out.printf ("    CC ≤ %d | NOA ≤ %d | NOM ≤ %d | NOP ≤ %d | LCOM ≤ %.1f | CE ≤ %d%n",
                limites.getMaxCC(), limites.getMaxNOA(), limites.getMaxNOM(),
                limites.getMaxNOP(), limites.getMaxLCOM(), limites.getMaxCE());
        System.out.println("  (Esses limites são do projeto — não há número mágico universal!)");
    }
}