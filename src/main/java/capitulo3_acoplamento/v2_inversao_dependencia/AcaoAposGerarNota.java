package capitulo3_acoplamento.v2_inversao_dependencia;

/**
 * O Contrato (Interface).
 *
 * Define o QUE deve ser feito, não COMO.
 * Qualquer classe que assinar este contrato (implementar a interface)
 * pode ser usada pelo Gerador. Isso é o verdadeiro poder.
 *
 * @author Weriton L. Petreca
 */

public interface AcaoAposGerarNota {
    void executa(NotaFiscal nf);
}
