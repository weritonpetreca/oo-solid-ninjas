package capitulo4_ocp.v5_calculadora_factory.usecases;

import capitulo4_ocp.v5_calculadora_factory.domain.Compra;
import capitulo4_ocp.v5_calculadora_factory.ports.ServicoDeEntrega;
import capitulo4_ocp.v5_calculadora_factory.ports.TabelaDePreco;

/**
 * 🐺 O BRUXO (USE CASE)
 *
 * Esta classe é o coração da regra de negócio.
 * Ela orquestra o cálculo do preço final, mas não sabe COMO calcular o desconto
 * nem COMO calcular o frete.
 *
 * 🛡️ OCP (OPEN/CLOSED PRINCIPLE):
 * - FECHADA PARA MODIFICAÇÃO: Se a regra de frete mudar (ex: Sedex 10), esta classe NÃO muda.
 * - ABERTA PARA EXTENSÃO: Podemos injetar qualquer implementação de {@link TabelaDePreco}
 *   ou {@link ServicoDeEntrega} no construtor.
 *
 * ⚔️ DIP (DEPENDENCY INVERSION):
 * Ela depende de abstrações (interfaces), não de classes concretas.
 */
public class CalculadoraDePrecos {

    private final TabelaDePreco tabela;
    private final ServicoDeEntrega servico;

    public CalculadoraDePrecos(TabelaDePreco tabela, ServicoDeEntrega servico) {
        this.tabela = tabela;
        this.servico = servico;
    }

    public double calcula(Compra produto) {
        double desconto = tabela.descontoPara(produto.getValor());
        double frete = servico.para(produto.getCidade());
        return produto.getValor() * (1 - desconto) + frete;
    }
}
