package capitulo7_interfaces_magras.v2_interfaces_coesas;

/**
 * SOLUÇÃO v2: Interface magra — só gera nota fiscal.
 *
 * ✅ Uma responsabilidade: gerar nota
 * ✅ Só implementada por impostos que realmente emitem nota
 * ✅ Sem gambiarras: quem não emite, simplesmente não implementa
 */
public interface GeradorDeNota {
    NotaFiscal geraNota();
}
