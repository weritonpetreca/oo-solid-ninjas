package capitulo5_encapsulamento;

import capitulo5_encapsulamento.v1_problema_encapsulamento.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessadorDeBoletosTest {

    @Test
    void devePagarFaturaComBoletoValorExato() {

        Fatura fatura = new Fatura(500.0);
        Boleto boleto = new Boleto("BOL-001", 500.0);

        ProcessadorDeBoletos processador = new ProcessadorDeBoletos();
        processador.processa(List.of(boleto), fatura);

        assertTrue(fatura.isPago(), "Fatura deveria estar paga");
        assertEquals(1, fatura.getPagamentos().size());
    }

    @Test
    void devePagarFaturaComMultiplosBoletos() {

        Fatura fatura = new Fatura(300.0);
        List<Boleto> boletos = List.of(
                new Boleto("BOL-001", 100),
                new Boleto("BOL-002", 100),
                new Boleto("BOL-003", 100)
        );

        ProcessadorDeBoletos processador = new ProcessadorDeBoletos();
        processador.processa(boletos, fatura);

        assertTrue(fatura.isPago());
        assertEquals(3, fatura.getPagamentos().size());
    }

    @Test
    void deveNegarPagarFaturaComBoletoInsuficiente() {

        Fatura fatura = new Fatura(500.0);
        Boleto boleto = new Boleto("BOL-001", 200.0);

        ProcessadorDeBoletos processador = new ProcessadorDeBoletos();
        processador.processa(List.of(boleto), fatura);

        assertFalse(fatura.isPago(), "Fatura não deveria estar paga com valor insuficiente");
    }

    @Test
    void demonstrarProblemaDeEncapsulamento_qualquerUmPodeAdicionarPagamento() {

        Fatura fatura = new Fatura(500.0);

        // ❌ Isso não deveria ser possível — mas v1 permite!
        // Qualquer classe pode adicionar pagamentos na mão, bypassando qualquer regra
        Pagamento pagamentoFraudulento = new Pagamento(999999.0, MeioDePagamento.BOLETO);
        fatura.getPagamentos().add(pagamentoFraudulento);

        // A fatura sequer sabe que foi "paga" — setPago nunca foi chamado
        assertFalse(fatura.isPago(), "Fatura não está marcada como paga, mas tem pagamentos indevidos");
        assertEquals(1, fatura.getPagamentos().size(), "Lista foi modificada externamente sem controle");
    }
}
