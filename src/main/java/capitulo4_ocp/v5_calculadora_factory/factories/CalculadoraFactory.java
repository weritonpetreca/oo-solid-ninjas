package capitulo4_ocp.v5_calculadora_factory.factories;

import capitulo4_ocp.v5_calculadora_factory.adapters.FreteCorreios;
import capitulo4_ocp.v5_calculadora_factory.adapters.FreteGratis;
import capitulo4_ocp.v5_calculadora_factory.adapters.TabelaDePrecoVip;
import capitulo4_ocp.v5_calculadora_factory.adapters.TabelaSemDesconto;
import capitulo4_ocp.v5_calculadora_factory.ports.ServicoDeEntrega;
import capitulo4_ocp.v5_calculadora_factory.ports.TabelaDePreco;
import capitulo4_ocp.v5_calculadora_factory.usecases.CalculadoraDePrecos;

/**
 * 🏭 A FORJA DE ARMAS (FACTORY)
 *
 * Esta classe tem a responsabilidade de montar a {@link CalculadoraDePrecos}
 * com as ferramentas (estratégias) corretas.
 *
 * 🛡️ O PROBLEMA SUTIL (VIOLAÇÃO DO OCP):
 * Embora a Calculadora esteja fechada para modificação, esta Factory está ABERTA.
 *
 * - Se um novo tipo de cliente surgir ("CLIENTE_OURO"), teremos que adicionar um novo `if`.
 * - Se um novo tipo de frete surgir ("SEDEX_10"), teremos que adicionar um novo `if`.
 *
 * A Factory centraliza a criação, mas ela mesma se torna um ponto de manutenção constante.
 *
 * ⚔️ PRÓXIMO PASSO (NÃO IMPLEMENTADO AQUI):
 * A solução seria usar um mapa de estratégias ou injeção de dependência (com Spring, por exemplo)
 * para registrar as implementações e tornar a Factory extensível.
 */
public class CalculadoraFactory {

    public CalculadoraDePrecos criar(String tipoCliente, String tipoFrete) {

        TabelaDePreco tabela;
        if ("VIP".equalsIgnoreCase(tipoCliente)) {
            tabela = new TabelaDePrecoVip();
        } else {
            tabela = new TabelaSemDesconto();
        }

        ServicoDeEntrega entrega;
        if ("GRATIS".equalsIgnoreCase(tipoFrete)) {
            entrega = new FreteGratis();
        } else {
            entrega = new FreteCorreios();
        }

        return new CalculadoraDePrecos(tabela, entrega);
    }
}
