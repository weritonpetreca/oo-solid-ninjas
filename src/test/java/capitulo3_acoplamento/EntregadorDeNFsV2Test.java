package capitulo3_acoplamento;

import capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * 🧪 TESTE DO ESPECIALISTA EM LOGÍSTICA
 *
 * Aqui testamos o EntregadorDeNFs isoladamente.
 * Este é o único lugar do sistema onde LeiDeEntrega e Correios aparecem nos testes.
 * O DespachadorV2Test não sabe que eles existem. Separação perfeita.
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("\uD83D\uDCE6 Testes V2: O Entregador de NFs")
public class EntregadorDeNFsV2Test {

    @Mock
    private LeiDeEntrega lei;
    @Mock
    private Correios correios;

    @Test
    @DisplayName("Deve usar SEDEX10 quando a entrega for urgente")
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
    @DisplayName("Deve usar SEDEX Comum quando a entrega não for urgente")
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
