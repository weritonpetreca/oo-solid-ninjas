package capitulo3_acoplamento;

import capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * 🧪 O TESTE DO ESPECIALISTA (COESÃO)
 *
 * Aqui testamos a regra de negócio de entrega ISOLADAMENTE.
 *
 * 🛡️ VANTAGENS:
 * 1. Teste Focado: Se este teste falhar, sabemos que o problema está na lógica de entrega.
 *    Não precisamos investigar o Despachador, o Imposto ou o DAO.
 * 2. Cobertura de Cenários: É fácil testar todos os caminhos (Urgente vs Comum)
 *    sem precisar montar um cenário complexo de Despachador.
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
