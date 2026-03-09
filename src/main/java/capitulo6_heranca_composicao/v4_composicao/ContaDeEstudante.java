package capitulo6_heranca_composicao.v4_composicao;

/**
 * SOLUÇÃO v4: ContaDeEstudante via composição — sem herança de ContaComum.
 *
 * ✅ Não tem rende() — e não precisa ter
 * ✅ Não herda nada problemático
 * ✅ Sem LSP violado — não há contrato sendo quebrado
 *
 * Custo (Aniche menciona): métodos que apenas repassam chamadas
 * para o ManipuladorDeSaldo (deposita → adiciona, saca → retira).
 * É uma troca. "Com frequência, a melhor."
 */
public class ContaDeEstudante {
    private ManipuladorDeSaldo manipulador;

    public ContaDeEstudante() { this.manipulador = new ManipuladorDeSaldo(); }

    public void deposita(double valor) { manipulador.adiciona(valor); }

    public void saca(double valor) { manipulador.retira(valor); }

    // ✅ Sem rende() — sem exceção — sem quebra de contrato

    public double getSaldo() { return manipulador.getSaldo(); }
}
