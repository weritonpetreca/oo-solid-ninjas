package capitulo3_acoplamento;

import capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntregadorDeNFsTest {

    @Mock
    private LeiDeEntrega lei;
    @Mock
    private Correios correios;

    @Test
    void deveUsarSedex10SeForUrgente() {
        // 1. ARRANGE
        EntregadorDeNFs entregador = new EntregadorDeNFs(lei, correios);
        NotaFiscal nf = new NotaFiscal(2000.0);

        when(lei.deveEntregarUrgente(nf)).thenReturn(true);

        // 2. ACT
        entregador.entrega(nf);

        // 3. ASSERT
        verify(correios).enviarPorSedex10(nf);
        verify(correios, never()).enviarPorSedexComum(nf);
    }

    @Test
    void deveUsarSedexComumSeNaoForUrgente() {
        // 1. ARRANGE
        EntregadorDeNFs entregador = new EntregadorDeNFs(lei, correios);
        NotaFiscal nf = new NotaFiscal(500.0);

        when(lei.deveEntregarUrgente(nf)).thenReturn(false);

        // 2. ACT
        entregador.entrega(nf);

        // 3. ASSERT
        verify(correios).enviarPorSedexComum(nf);
        verify(correios, never()).enviarPorSedex10(nf);
    }
}
