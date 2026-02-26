package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 🏍️ FRETE VIA MOTOBOY
 *
 * Uma nova opção de entrega adicionada SEM MODIFICAR a Calculadora.
 * Motoboys são mais rápidos, mas operam apenas localmente.
 *
 * ⚔️ OCP EM AÇÃO:
 * O sistema ganhou um novo comportamento de entrega
 * sem que nenhuma classe existente fosse alterada.
 *
 * Regras:
 * - São Paulo: R$ 20 (entrega no mesmo dia)
 * - Rio de Janeiro: R$ 25
 * - Outras: não operam (lança exceção)
 */

public class FreteMotoboy implements ServicoDeEntrega {
    @Override
    public double para(String cidade) {
        if ("SÃO PAULO".equals(cidade.toUpperCase())) {
            return 20;
        }

        if ("RIO DE JANEIRO".equals(cidade.toUpperCase())) {
            return 25;
        }

        throw new RuntimeException("Motoboy não opera em: " + cidade);
    }
}

