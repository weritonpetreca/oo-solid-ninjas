package capitulo3_acoplamento;

import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.Fatura;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.AcaoAposGerarNota;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.usecases.GeradorDeNotaFiscal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * 🧪 PROVA DO HEXÁGONO — GERADOR
 *
 * Compare este teste com o GeradorDeNotaFiscalV1Test e V2Test.
 *
 * ⚔️ A EVOLUÇÃO:
 * V1: Mockava EnviadorDeEmail e NotaFiscalDao (classes concretas)
 * V2: Mockava AcaoAposGerarNota (interface, mas sem separação física)
 * V3: Mockava AcaoAposGerarNota (interface + hexagonal — zero imports de adapters)
 *
 * 🛡️ O QUE ISSO PROVA?
 * Se trocarmos NotaFiscalDao por MongoRepository,
 * ou EnviadorDeEmail por EnviadorSlack,
 * ESTE TESTE CONTINUA FUNCIONANDO sem alteração.
 *
 * Testamos o comportamento do use case, não a infraestrutura.
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("🐺 Testes V3: O Gerador Hexagonal (DIP Completo)")
public class GeradorDeNotaFiscalV3Test {

    @Mock
    private AcaoAposGerarNota acao1;

    @Mock
    private AcaoAposGerarNota acao2;

    @Mock
    private AcaoAposGerarNota acao3;

    @Test
    @DisplayName("Deve gerar nota fiscal e notificar todos os observadores")
    void deveGerarNotaENotificarObservadores() {

        // 1. ARRANGE
        List<AcaoAposGerarNota> acoes = Arrays.asList(acao1, acao2, acao3);
        GeradorDeNotaFiscal gerador = new GeradorDeNotaFiscal(acoes);

        Fatura fatura = new Fatura("Geralt de Rívia", 1000.0);

        // 2. ACT
        NotaFiscal nf = gerador.gera(fatura);

        // 3. ASSERT
        // Verifica se a nota foi gerada corretamente
        assertEquals(1000.0, nf.getValor(), 0.001);
        assertEquals(60.0, nf.getImposto(), 0.001); // 6% de 1000

        // Verifica se TODOS os observadores foram notificados
        verify(acao1, times(1)).executa(nf);
        verify(acao2, times(1)).executa(nf);
        verify(acao3, times(1)).executa(nf);
    }

    @Test
    @DisplayName("Deve funcionar com lista vazia de ações")
    void deveFuncionarComListaVazia() {

        // 1. ARRANGE
        List<AcaoAposGerarNota> acoesVazias = Arrays.asList();
        GeradorDeNotaFiscal gerador = new GeradorDeNotaFiscal(acoesVazias);

        Fatura fatura = new Fatura("Vesemir", 500.0);

        // 2. ACT
        NotaFiscal nf = gerador.gera(fatura);

        // 3. ASSERT
        // Mesmo sem observadores, a nota é gerada normalmente
        assertEquals(500.0, nf.getValor(), 0.001);
        assertEquals(30.0, nf.getImposto(), 0.001);
    }
}
