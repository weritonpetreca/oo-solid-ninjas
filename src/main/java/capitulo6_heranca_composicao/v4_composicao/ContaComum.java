package capitulo6_heranca_composicao.v4_composicao;

/**
 * SOLUÇÃO v4: ContaComum usando composição com ManipuladorDeSaldo.
 *
 * Aniche, Cap. 6, seção 6.5:
 * Não há mais herança entre ContaComum e ContaDeEstudante.
 * Cada uma TEM UM ManipuladorDeSaldo — composição.
 *
 * ✅ ContaComum tem rende() — usa o manipulador para aplicar juros
 * ✅ ContaDeEstudante simplesmente não declara rende() — sem exceção
 * ✅ Sem LSP violado — sem contrato quebrado
 */
public class ContaComum {

    private ManipuladorDeSaldo manipulador;

    public ContaComum() { this.manipulador = new ManipuladorDeSaldo(); }

    public void deposita(double valor) { manipulador.adiciona(valor); }

    public void saca(double valor) { manipulador.retira(valor); }

    public void rende() { manipulador.aplicaJuros(0.1); } // 10%

    public double getSaldo() { return manipulador.getSaldo(); }
}
