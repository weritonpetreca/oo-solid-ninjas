package capitulo3_acoplamento;

import capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DespachadorV1Test {

    @Mock
    private NFDao dao;
    @Mock
    private CalculadorDeImposto impostos;
    @Mock
    private LeiDeEntrega lei;
    @Mock
    private Correios correios;

    @Test
    void deveEnviarPorSedex10QuandoForUrgente() {
        // 1. ARRANGE
        DespachadorDeNotasFiscais despachador = new DespachadorDeNotasFiscais(dao, impostos, lei, correios);
        NotaFiscal nf = new NotaFiscal(2000.0);

        // Ensinando os mocks a se comportarem
        when(impostos.para(nf)).thenReturn(120.0);
        when(lei.deveEntregarUrgente(nf)).thenReturn(true); // Forçamos ser urgente

        // 2. ACT
        despachador.processa(nf);

        // 3. ASSERT
        // O Despachador V1 é responsável por decidir qual método do correio chamar.
        // Testamos se ele tomou a decisão certa.
        verify(correios).enviarPorSedex10(nf);
        verify(correios, never()).enviarPorSedexComum(nf);
        verify(dao).persiste(nf);
    }

    @Test
    void deveEnviarPorSedexComumQuandoNaoForUrgente() {
        // 1. ARRANGE
        DespachadorDeNotasFiscais despachador = new DespachadorDeNotasFiscais(dao, impostos, lei, correios);
        NotaFiscal nf = new NotaFiscal(500.0);

        when(impostos.para(nf)).thenReturn(30.0);
        when(lei.deveEntregarUrgente(nf)).thenReturn(false); // Forçamos NÃO ser urgente

        // 2. ACT
        despachador.processa(nf);

        // 3. ASSERT
        verify(correios).enviarPorSedexComum(nf);
        verify(correios, never()).enviarPorSedex10(nf);
        verify(dao).persiste(nf);
    }
}
