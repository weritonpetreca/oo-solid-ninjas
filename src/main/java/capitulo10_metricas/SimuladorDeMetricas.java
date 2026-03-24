package capitulo10_metricas;

import capitulo10_metricas.livro_original.DemoLivroOriginal;
import capitulo10_metricas.v1_complexidade_ciclomatica.DemoV1;
import capitulo10_metricas.v2_tamanho.DemoV2;
import capitulo10_metricas.v3_lcom.DemoV3;
import capitulo10_metricas.v4_acoplamento.DemoV4;
import capitulo10_metricas.v5_mundo_real.DemoV5;

/**
 * ⚔️ CAPÍTULO 10 — Métricas de Código
 * Simulador didático: O Sistema de Análise de Qualidade da Guilda
 */
public class SimuladorDeMetricas {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("=".repeat(62));
        System.out.println("  CAP 10 — MÉTRICAS DE CÓDIGO");
        System.out.println("  O Bestiário das Métricas de Qualidade");
        System.out.println("=".repeat(62));
        System.out.println();

        simularLivroOriginal();
        simularV1_ComplexidadeCiclomatica();
        simularV2_Tamanho();
        simularV3_LCOM();
        simularV4_Acoplamento();
        simularV5_MundoReal();
        imprimirResumo();
    }

    static void simularLivroOriginal() {
        System.out.println("[ LIVRO ] Exemplos Originais do Aniche");
        System.out.println();
        DemoLivroOriginal.executar();
        System.out.println();
    }

    static void simularV1_ComplexidadeCiclomatica() {
        System.out.println("[ V1 ] Complexidade Ciclomática — Caminhos de Execução");
        System.out.println();
        DemoV1.executar();
        System.out.println();
    }

    static void simularV2_Tamanho() {
        System.out.println("[ V2 ] Tamanho — NOA, NOM, NOP, LOC");
        System.out.println();
        DemoV2.executar();
        System.out.println();
    }

    static void simularV3_LCOM() {
        System.out.println("[ V3 ] Coesão e a LCOM — Lack of Cohesion of Methods");
        System.out.println();
        DemoV3.executar();
        System.out.println();
    }

    static void simularV4_Acoplamento() {
        System.out.println("[ V4 ] Acoplamento Aferente (CA) e Eferente (CE)");
        System.out.println();
        DemoV4.executar();
        System.out.println();
    }

    static void simularV5_MundoReal() {
        System.out.println("[ V5 ] Mundo Real — Sistema de Análise de Qualidade");
        System.out.println();
        DemoV5.executar();
        System.out.println();
    }

    static void imprimirResumo() {
        System.out.println("=".repeat(62));
        System.out.println("  RESUMO DO CAPÍTULO 10 — MÉTRICAS DE CÓDIGO");
        System.out.println("=".repeat(62));
        System.out.println("  CC   (Complexidade Ciclomática) → caminhos de execução");
        System.out.println("  NOA  (Number of Attributes)     → tamanho da classe");
        System.out.println("  NOM  (Number of Methods)        → responsabilidades");
        System.out.println("  NOP  (Number of Parameters)     → complexidade de API");
        System.out.println("  LCOM (Lack of Cohesion)         → coesão entre métodos");
        System.out.println("  CE   (Coupling Efferent)        → fragilidade (saída)");
        System.out.println("  CA   (Coupling Afferent)        → estabilidade (entrada)");
        System.out.println();
        System.out.println("  Regra de Ouro: métricas são FILTROS, não oráculos.");
        System.out.println("  Não há número mágico — defina os limites do seu projeto.");
        System.out.println("  Ferramentas: SonarQube, PMD, Checkstyle, SpotBugs");
        System.out.println("=".repeat(62));
    }
}