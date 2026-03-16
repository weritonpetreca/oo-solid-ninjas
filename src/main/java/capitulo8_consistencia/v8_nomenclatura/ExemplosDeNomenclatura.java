package capitulo8_consistencia.v8_nomenclatura;

import java.util.List;

/**
 * NOMENCLATURA — Aniche, Cap. 8, seção 8.8.
 *
 * "É por meio dos nomes que damos às coisas que conseguimos entender
 *  o significado de cada um deles."
 *
 * Esta classe demonstra lado a lado nomes ruins vs. bons nomes
 * em métodos de um ServicoDeGuilda típico.
 *
 * Princípios aplicados:
 *   ❌ Nomes muito curtos (x, d, lst) — não dizem nada
 *   ❌ Nomes muito longos — cansativos e difíceis de ler
 *   ❌ Nomes que mentem (getData() que filtra, não só busca)
 *   ✅ Nomes no comprimento certo — claros sem serem verbosos
 *   ✅ Nomes que revelam intenção
 *   ✅ Convenções da linguagem seguidas (camelCase, substantivos para classes)
 */
public class ExemplosDeNomenclatura {

    // ════════════════════════════════════════════
    // ❌ NOMES RUINS
    // ════════════════════════════════════════════

    /** ❌ 'x' não diz absolutamente nada */
    public double calc(double x, double y) { return x * y * 0.15; }

    /** ❌ Nome longo demais — cansa antes de entender */
    public double calcularTaxaDaGuildaDoContineenteParaOBruxoComBaseNoValorTotalDaRecompensa(
            double recompensaTotal) {
        return recompensaTotal * 0.15;
    }

    /** ❌ Nome que mente — filtra, mas parece só buscar */
    public List<String> getDados(List<String> lst, int d) {
        return lst.stream().filter(s -> s.length() > d).toList();
    }

    // ════════════════════════════════════════════
    // ✅ NOMES BONS
    // ════════════════════════════════════════════

    /** ✅ Claro: o que entra, o que sai, o que faz */
    public double calcularTaxaDaGuilda(double recompensa) {
        return recompensa * 0.15;
    }

    /** ✅ Nome revela a intenção de filtrar por tamanho mínimo */
    public List<String> filtrarMissoesPorTamanhoMinimo(List<String> missoes, int tamanhoMinimo) {
        return missoes.stream().filter(m -> m.length() > tamanhoMinimo).toList();
    }

    /** ✅ Verbo + substantivo: ação clara */
    public boolean cacadorEstaDisponivel(String nomeCacador) {
        // lógica de verificação
        return true;
    }

    /** ✅ Nome de classe revela responsabilidade */
    // ❌ class Xpto { }
    // ✅ class GerenciadorDeContratos { }
    // ✅ class RepositorioDeContratos { }
    // ✅ class CalculadorDeTaxaDaGuilda { }

    /** ✅ Booleanos: prefixo "is", "has", "pode", "esta" */
    // ❌ boolean ativo(Cacador c)
    // ✅ boolean isAtivo(Cacador c)
    // ✅ boolean podeConcluirMissao(Cacador c)
    // ✅ boolean temContratoAtivo(Cacador c)

    /** ✅ Coleções: nome no plural e substantivo que descreve o conteúdo */
    // ❌ List<Missao> lista
    // ❌ List<Missao> m
    // ✅ List<Missao> missoesDisponiveis
    // ✅ List<Missao> missoesConcluidasNoMes
}
