package capitulo5_encapsulamento;

import capitulo5_encapsulamento.v4_solucao_completa.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FaturaTest {

    @Test
    void devePagarFaturaAoAdicionarPagamentoSuficiente() {

        Fatura fatura = new Fatura(500.0);
        Pagamento pagamento = new Pagamento(500.0, MeioDePagamento.BOLETO);

        fatura.adicionaPagamento(pagamento);

        assertTrue(fatura.isPago());
    }

    @Test
    void naoDevePagarFaturaComPagamentoParcial() {

        Fatura fatura = new Fatura(500.0);
        Pagamento pagamento = new Pagamento(300.0, MeioDePagamento.BOLETO);

        fatura.adicionaPagamento(pagamento);

        assertFalse(fatura.isPago());
    }

    @Test
    void devePagarFaturaComMultiplosPagamentos() {

        Fatura fatura = new Fatura(300.0);

        fatura.adicionaPagamento(new Pagamento(100.0, MeioDePagamento.BOLETO));
        fatura.adicionaPagamento(new Pagamento(100.0, MeioDePagamento.CARTAO_CREDITO));
        fatura.adicionaPagamento(new Pagamento(100.0, MeioDePagamento.CARTAO_DEBITO));

        assertTrue(fatura.isPago());
        assertEquals(3, fatura.getPagamentos().size());
    }

    @Test
    void naoDevePermitirModificarListaDePagamentosExternamente() {

        Fatura fatura = new Fatura(500.0);
        fatura.adicionaPagamento(new Pagamento(200.0, MeioDePagamento.BOLETO));

        assertThrows(UnsupportedOperationException.class, () -> {
            fatura.getPagamentos().add(new Pagamento(999.0, MeioDePagamento.BOLETO));
        });
    }

    @Test
    void processadorDeBoletosDeveUsarFaturaCorretamente() {

        Fatura fatura = new Fatura(600.0);
        List<Boleto> boletos = List.of(
                new Boleto("BOL-001", 200.0),
                new Boleto("BOL-002", 200.0),
                new Boleto("BOL-003", 200.0)
        );

        new ProcessadorDeBoletos().processa(boletos, fatura);

        assertTrue(fatura.isPago());
        assertEquals(3, fatura.getPagamentos().size());
    }
}
