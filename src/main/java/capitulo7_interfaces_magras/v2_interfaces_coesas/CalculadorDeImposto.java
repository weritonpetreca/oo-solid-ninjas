package capitulo7_interfaces_magras.v2_interfaces_coesas;

/**
 * SOLUÇÃO v2: Interface magra — só calcula imposto.
 *
 * Aniche, Cap. 7, seção 7.1:
 * "Se uma interface não é coesa, dividimo-la em duas ou mais interfaces."
 *
 * ✅ Uma responsabilidade: calcular imposto
 * ✅ Qualquer imposto implementa esta — os que geram nota e os que não geram
 * ✅ Estável: poucas razões para mudar
 */
public interface CalculadorDeImposto {
    double imposto(double valorCheio);
}
