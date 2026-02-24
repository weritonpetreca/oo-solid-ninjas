package capitulo4_ocp.v2_o_problema_dos_ifs;
/**
 * 🧟 A CALCULADORA DOS IFS (SEGUNDA MALDIÇÃO)
 *
 * Quando o sistema cresce e novas regras são necessárias,
 * o desenvolvedor ingênuo resolve o problema adicionando IFs.
 *
 * ⚔️ O PENSAMENTO ERRADO:
 * "A solução é simples: vou perguntar qual regra usar e decidir com um IF."
 *
 * 😈 O RESULTADO É UMA NOVA GOD CLASS:
 * Esta classe agora:
 * - Conhece TODAS as tabelas de preços do sistema (REGRA_1, REGRA_2, REGRA_3...)
 * - Cresce a cada nova regra de negócio
 * - É difícil de testar (quantos caminhos existem?)
 * - Viola o SRP (ela decide qual regra usar E calcula o preço)
 * - Viola o OCP (para adicionar REGRA_4, eu preciso abrir e modificar esta classe)
 *
 * 🛡️ PROBLEMA ADICIONAL — OS IFS DENTRO DAS CLASSES:
 * A segunda solução ruim é colocar os IFs dentro das próprias classes,
 * como na {@link FreteComIfs}. A complexidade fica "escondida", mas continua lá.
 *
 * ⚔️ ANALOGIA WITCHER:
 * É como um Bruxo que tenta aprender TODOS os estilos de luta numa única espada.
 * A espada fica tão pesada e complexa que ele não consegue mais manejar.
 *
 * 🧙 A LIÇÃO DO CONCÍLIO:
 * "Balancear acoplamento e coesão é o desafio. O IF resolve o imediato,
 * mas cobra seu preço com juros no futuro."
 */

public class CalculadoraDePrecos {

    public static final int REGRA_PADRAO = 1;
    public static final int REGRA_DIFERENCIADA = 2;
    public static final int REGRA_VIP = 3;

    private final int regraAtiva;

    public CalculadoraDePrecos(int regraAtiva) { this.regraAtiva = regraAtiva; }

    /**
     * ⚠️ COMPLEXIDADE CICLOMÁTICA CRESCENTE.
     *
     * Cada novo IF aqui é um sinal de que o design precisa ser repensado.
     * Para adicionar uma REGRA_4, PRECISAMOS ABRIR ESTA CLASSE — isso viola o OCP.
     */

    double calcula(Compra produto) {

        FreteComIfs correios = new FreteComIfs();

        double desconto;

        // 🚨 CADA NOVA REGRA AUMENTA O CAOS DESTA CLASSE
        if (regraAtiva == REGRA_PADRAO) {
            TabelaDePrecoPadrao tabela = new TabelaDePrecoPadrao();
            desconto = tabela.descontoPara(produto.getValor());
        } else if (regraAtiva == REGRA_DIFERENCIADA) {
            TabelaDePrecoDiferenciada tabela = new TabelaDePrecoDiferenciada();
            desconto = tabela.descontoPara(produto.getValor());
        } else if (regraAtiva == REGRA_VIP) {
            TabelaDePrecoVip tabela = new TabelaDePrecoVip();
            desconto = tabela.descontoPara(produto.getValor());
        } else {
            throw new RuntimeException("Regra desconhecida: " + regraAtiva);
        }

        // A regra de frete também cresce dentro do FreteComIfs
        double frete = correios.para(produto.getCidade(), regraAtiva);

        return produto.getValor() * (1 - desconto) + frete;
    }
}
