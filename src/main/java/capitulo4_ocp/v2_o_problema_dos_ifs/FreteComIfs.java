package capitulo4_ocp.v2_o_problema_dos_ifs;
/**
 * 🚚 FRETE COM IFS — A SEGUNDA ABORDAGEM PROBLEMÁTICA
 *
 * O livro menciona uma segunda "solução ruim" comum:
 * em vez de colocar os IFs na Calculadora, colocamos dentro da própria classe de serviço.
 *
 * ⚔️ PARECE MELHOR, MAS NÃO É:
 * - A complexidade ainda cresce.
 * - A interface da classe fica complicada (precisa receber a "regra").
 * - Adicionar uma nova regra de frete exige abrir ESTA CLASSE — ainda viola o OCP.
 * - Testar cada variação exige configurar o estado correto (code smell).
 *
 * 🛡️ COMPARANDO AS DUAS ABORDAGENS RUINS:
 *
 * | Abordagem              | Prós (aparentes)       | Contras (reais)                       |
 * |------------------------|------------------------|---------------------------------------|
 * | IFs na Calculadora     | "Tudo num lugar só"    | God Class, viola SRP e OCP            |
 * | IFs dentro do Frete    | "Cada coisa no lugar"  | Interface complexa, ainda viola OCP   |
 *
 * Ambas têm o mesmo problema fundamental: nenhuma cria uma ABSTRAÇÃO.
 *
 * 💡 A LIÇÃO:
 * A solução não é reorganizar os IFs — é ELIMINAR a necessidade de IFs
 * através de polimorfismo e abstrações.
 */

public class FreteComIfs {

    // ⚠️ Precisamos receber a "regra" para saber qual cálculo fazer
    // Isso é um sinal claro de design problemático
    public double para(String cidade, int regra) {

        // 🚨 OS IFS ESCONDIDOS DENTRO DA CLASSE
        // Cada nova regra exige abrir esta classe
        if (regra == CalculadoraDePrecos.REGRA_PADRAO) {
            if ("SÃO PAULO".equals(cidade.toUpperCase())) return 15;
            return 30;
        }

        if (regra == CalculadoraDePrecos.REGRA_DIFERENCIADA) {
            if ("SÃO PAULO".equals(cidade.toUpperCase())) return 10;
            if ("RIO DE JANEIRO".equals(cidade.toUpperCase())) return 12;
            return 25;
        }

        if (regra == CalculadoraDePrecos.REGRA_VIP) {
            // VIPs têm frete grátis para qualquer cidade
            return 0;
        }

        throw new RuntimeException("Regra de frete desconhecida: " + regra);
    }
}
