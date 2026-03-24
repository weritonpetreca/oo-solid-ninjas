package capitulo10_metricas;

import capitulo10_metricas.v1_complexidade_ciclomatica.DemoV1;
import infra.Console;

/**
 * ⚔️ CAMPO DE TREINAMENTO: MÉTRICAS DE CÓDIGO E HEURÍSTICAS
 *
 * Aqui simulamos a análise de métricas como Complexidade Ciclomática (CC),
 * Tamanho (LOC, NOA, NOM) e Coesão (LCOM).
 */
public class SimuladorDeMetricas {

    public static void main(String[] args) {
        Console.consertarAcentuacao();

        System.out.println("==================================================");
        System.out.println("📊 CAPÍTULO 10: MÉTRICAS DE CÓDIGO (A VISÃO DO BRUXO)");
        System.out.println("==================================================\n");

        System.out.println(">>> CENÁRIO 1: A Lâmina Cega (Alta Complexidade Ciclomática)\n");
        DemoV1.executar();

        System.out.println("\n--------------------------------------------------\n");
        
        System.out.println(">>> CENÁRIO 2: O Gigante Lento (Tamanho e Coesão)\n");
        System.out.println("  ❌ A classe 'MissaoGigante' (v2) é um monstro:");
        System.out.println("    - NOA (Número de Atributos) alto.");
        System.out.println("    - LOC (Linhas de Código) alto.");
        System.out.println("    - LCOM (Coesão) ruim, pois mistura dados financeiros com notificação.");
        System.out.println();
        System.out.println("  ✅ A classe 'ProcessadorDeMissao' (v2) é afiada:");
        System.out.println("    - NOM (Número de Métodos) = 2.");
        System.out.println("    - MLOC (Linhas por Método) pequeno.");
        System.out.println("    - CC = 4 (O limite aceitável é manter abaixo de 5).");
        System.out.println();
        System.out.println("  (Nota: Veja o código em v2_tamanho/TamanhoDeClasse.java para a prova matemática)");
    }
}
