package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 🚚 FRETE VIA CORREIOS
 *
 * Implementação original: preço baseado apenas na cidade.
 *
 * 🛡️ AGORA É UM ADAPTER:
 * Antes era a única opção. Agora é apenas uma das formas de entrega.
 * A Calculadora nem sabe que "Correios" existe — ela vê apenas {@link ServicoDeEntrega}.
 *
 * Regras:
 * - São Paulo: R$ 15
 * - Outras cidades: R$ 30
 */

public class FreteCorreios implements ServicoDeEntrega {
    @Override
    public double para(String cidade) {
        if ("SÃO PAULO".equals(cidade.toUpperCase())) {
            return 15;
        }
        return 30;
    }
}
