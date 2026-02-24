package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 🏢 ENTREGA EM LOJA (RETIRADA)
 *
 * Outra extensão possível: o cliente retira no local.
 * Custo de logística zero.
 *
 * 🛡️ DEMONSTRAÇÃO DO OCP:
 * Quantas classes de entrega queremos criar?
 * Não importa. A Calculadora nunca precisará ser modificada.
 */

public class RetiradaEmLoja implements ServicoDeEntrega {
    @Override
    public double para(String cidade) {
        return 0;
    }
}
