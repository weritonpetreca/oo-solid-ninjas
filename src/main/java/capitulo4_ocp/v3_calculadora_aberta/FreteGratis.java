package capitulo4_ocp.v3_calculadora_aberta;

/**
 * 🎁 FRETE GRÁTIS (VIP)
 *
 * Para clientes VIP, a entrega é por conta da casa.
 *
 * ⚔️ NULL OBJECT PATTERN (NOVAMENTE):
 * Não precisamos de lógica condicional na Calculadora:
 * "Se for VIP, não cobrar frete. Senão, cobrar..."
 *
 * Basta criar esta implementação e passar para a Calculadora.
 * Ela retorna 0, e a Calculadora nunca precisa saber o motivo.
 * Isso é polimorfismo puro.
 */

public class FreteGratis implements ServicoDeEntrega {
    @Override
    public double para(String cidade) {
        return 0;
    }
}
