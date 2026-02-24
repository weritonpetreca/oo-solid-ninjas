package capitulo4_ocp.v1_o_problema;

import capitulo4_ocp.v3_calculadora_aberta.TabelaDePreco;

/**
 * 💰 TABELA DE PREÇO PADRÃO
 *
 * A única regra de desconto do sistema (por enquanto).
 *
 * ⚔️ SITUAÇÃO ATUAL (v1):
 * Esta classe é instanciada DIRETAMENTE dentro da {@link CalculadoraDePrecos}:
 *
 *     TabelaDePrecoPadrao tabela = new TabelaDePrecoPadrao(); // 🚨 ACOPLAMENTO RÍGIDO
 *
 *
 * Isso significa que a Calculadora está "casada" com esta tabela específica.
 * Não há como trocar a regra de desconto sem mexer no código da Calculadora.
 *
 * 🛡️ REGRA DE NEGÓCIO:
 * - Compras acima de 5.000: 3% de desconto
 * - Compras acima de 1.000: 5% de desconto
 * - Demais: sem desconto
 */

public class TabelaDePrecoPadrao implements TabelaDePreco {

    public double descontoPara(double valor) {
        if (valor > 5000) return 0.03;
        if (valor > 1000) return 0.05;
        return 0;
    }
}
