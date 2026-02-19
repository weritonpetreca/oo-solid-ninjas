package capitulo3_acoplamento;

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.CalculadorDeImposto;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.Entregador;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.Repositorio;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.usecases.DespachadorDeNotasFiscais;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * 🧪 PROVA DO HEXÁGONO
 *
 * Compare este teste com o DespachadorV1Test e DespachadorV2Test.
 *
 * ⚔️ A DIFERENÇA CRUCIAL:
 * V1: @Mock NFDao, @Mock CalculadorDeImposto, @Mock LeiDeEntrega, @Mock Correios
 *     → 4 mocks, incluindo classes concretas de infraestrutura
 *
 * V3: @Mock CalculadorDeImposto, @Mock Entregador, @Mock Repositorio
 *     → 3 mocks, TODOS interfaces
 *     → Zero conhecimento de Correios, LeiDeEntrega ou NFDao
 *
 * 🛡️ ISSO SIGNIFICA:
 * Se trocarmos NFDao por MongoRepository, este teste continua funcionando.
 * Se trocarmos Correios por Drone, este teste continua funcionando.
 * O teste testa O COMPORTAMENTO DO DESPACHADOR, não sua infraestrutura.
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("🐺 Testes V3: O Despachador Hexagonal (DIP Completo)")
public class DespachadorV3Test {

    @Mock
    private CalculadorDeImposto impostos; // interface, não classe concreta

    @Mock
    private Entregador entregador;       // interface, não EntregadorDeNFs

    @Mock
    private Repositorio repositorio;     // interface, não NFDao

    @Test
    @DisplayName("Deve calcular imposto, entregar e persistir - nesta ordem")
    void deveOrquestrarOProcessoCompleto() {
        // 1. ARRANGE
        DespachadorDeNotasFiscais despachador =
                new DespachadorDeNotasFiscais(impostos, entregador, repositorio);

        NotaFiscal nf = new NotaFiscal(2000.0);
        when(impostos.para(nf)).thenReturn(120.0);

        // 2. ACT
        despachador.processa(nf);

        // 3. ASSERT
        // Verifica se o imposto foi aplicado na nota
        verify(impostos).para(nf);

        // Verifica se delegou a entrega sem tomar decisões por conta própria
        verify(entregador).entrega(nf);

        // Verifica se persistiu após tudo
        verify(repositorio).persiste(nf);
    }

    @Test
    @DisplayName("Deve aplicar o imposto calculado na nota antes de entregar")
    void deveAplicarImpostoNaNota() {

        // 1. ARRENGE
        DespachadorDeNotasFiscais depachador =
                new DespachadorDeNotasFiscais(impostos, entregador, repositorio);

        NotaFiscal nf = new NotaFiscal(1000.0);
        when(impostos.para(nf)).thenReturn(60.0);

        // 2. ACT
        depachador.processa(nf);

        // 3. ASSERT
        assertEquals(60.0, nf.getImposto(), 0.001);

    }

}
