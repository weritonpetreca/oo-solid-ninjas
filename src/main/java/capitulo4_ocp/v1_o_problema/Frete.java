package capitulo4_ocp.v1_o_problema;
/**
 * 🚚 FRETE (SERVIÇO DE ENTREGA PADRÃO)
 *
 * A única regra de frete do sistema (por enquanto).
 *
 * ⚔️ SITUAÇÃO ATUAL (v1):
 * Esta classe também é instanciada DIRETAMENTE na {@link CalculadoraDePrecos}:
 *
 *     Frete correios = new Frete(); // 🚨 ACOPLAMENTO RÍGIDO
 *
 *
 * 🛡️ REGRA DE NEGÓCIO:
 * - Entregas para São Paulo: R$ 15
 * - Demais cidades: R$ 30
 */

public class Frete {

    public double para(String cidade) {
        if ("SÃO PAULO".equals(cidade.toUpperCase())) {
            return 15;
        }
        return 30;
    }
}
