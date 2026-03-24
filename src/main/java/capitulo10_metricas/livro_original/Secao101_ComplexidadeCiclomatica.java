package capitulo10_metricas.livro_original;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 10, seção 10.1: Complexidade Ciclomática
// ══════════════════════════════════════════════════════════════════════════════

/**
 * Aniche, p.128:
 * "Quando um método é complexo? Geralmente quando ele tem muita linha de
 *  código ou quando ele tem muitos possíveis diferentes caminhos a serem
 *  executados."
 *
 * "O número de McCabe para o método acima seria 3 (temos 2 ifs + 1)."
 *
 * Regra prática: CC = número de instruções de desvio (if, for, while,
 * switch, catch) + 1.
 */

// ─── Método do livro — CC = 3 (2 ifs + 1) ───────────────────────────────────

class ExemploComplexidadeCiclomatica {

    /**
     * Exemplo direto do livro, p.128.
     * CC = 2 (dois ifs) + 1 = 3.
     */
    public int conta(int a, int b) {
        int total = 0;
        if (a > 10) total += a + b;     // +1 desvio
        if (b > 20) total += a * 2 + b; // +1 desvio
        return total;
    }

    /**
     * ✅ Método com baixa complexidade (CC = 1).
     * Nenhum desvio de fluxo — apenas uma execução linear.
     */
    public double calcularImposto(double valorBase, double aliquota) {
        return valorBase * aliquota; // CC = 1 (zero desvios + 1)
    }

    /**
     * ❌ Método com alta complexidade (CC = 6).
     * Muitos caminhos diferentes → difícil de testar, difícil de manter.
     */
    public String classificarPedido(double valor, String cliente, boolean urgente,
                                    boolean vip, boolean frete) {
        String resultado = "PADRÃO";

        if (valor > 1000) {              // +1
            resultado = "ALTO VALOR";
        }
        if (urgente) {                   // +1
            resultado += " URGENTE";
        }
        if (vip) {                       // +1
            resultado += " VIP";
        }
        if (frete) {                     // +1
            if (valor > 500) {           // +1
                resultado += " FRETE GRÁTIS";
            } else {
                resultado += " FRETE COBRADO";
            }
        }
        return resultado; // CC = 5 desvios + 1 = 6
    }
}

/**
 * Calculadora simples de Complexidade Ciclomática.
 * Conta desvios de fluxo em um pseudo-código representado por tokens.
 *
 * Em ferramentas reais (SonarQube, PMD), isso é feito via AST (Abstract
 * Syntax Tree). Aqui simulamos com contagem de tokens para fins didáticos.
 */
class CalculadorDeCC {

    private static final String[] TOKENS_DE_DESVIO = {
            "if", "else if", "for", "while", "do", "switch",
            "case", "catch", "&&", "||", "?", "break"
    };

    /**
     * Calcula a complexidade ciclomática de um trecho de código.
     * CC = número de tokens de desvio + 1.
     */
    public int calcular(String codigoPseudo) {
        int desvios = 0;
        for (String token : TOKENS_DE_DESVIO) {
            int pos = 0;
            while ((pos = codigoPseudo.indexOf(token, pos)) != -1) {
                desvios++;
                pos += token.length();
            }
        }
        return desvios + 1;
    }

    /**
     * Aniche, p.128:
     * "Podemos generalizá-la para nível de classe somando a complexidade
     *  ciclomática de cada um dos métodos."
     */
    public int calcularParaClasse(int... ccPorMetodo) {
        int total = 0;
        for (int cc : ccPorMetodo) {
            total += cc;
        }
        return total;
    }
}