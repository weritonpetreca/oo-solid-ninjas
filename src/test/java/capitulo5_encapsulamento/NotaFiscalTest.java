package capitulo5_encapsulamento;

import capitulo5_encapsulamento.v2_intimidade_inapropriada.NotaFiscalEncapsulada;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotaFiscalTest {

    @Test
    void deveAplicarAliquotaReduzida_quandoValorSemImpostoAcimaDe10000() {

        // Nota de alto valor → 6%
        NotaFiscalEncapsulada nf = new NotaFiscalEncapsulada(15000.0, 12000.0);

        double imposto = nf.calcularValorImposto();

        assertEquals(900.0, imposto, 0.001);
    }

    @Test
    void deveAplicarAliquotaCheia_quandoValorSemImpostoAbaixoDe10000() {

        // Nota de valor menor → 12%
        NotaFiscalEncapsulada nf = new NotaFiscalEncapsulada(5000.0, 8000.0);

        double imposto = nf.calcularValorImposto();

        assertEquals(600.0, imposto, 0.001);
    }

    @Test
    void demonstraQueClienteNaoSabeComo_apenasOQue() {

        NotaFiscalEncapsulada nf = new NotaFiscalEncapsulada(10000.0, 11000.0);

        // ✅ O cliente só sabe O QUÊ: calcular o imposto
        // ✅ O cliente não sabe COMO: nenhum if, nenhuma alíquota, nenhum limite visível
        double imposto = nf.calcularValorImposto();

        assertTrue(imposto > 0);
    }
}
