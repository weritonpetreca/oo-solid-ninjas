package capitulo8_consistencia.v3_bom_vizinho;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap8 v3 - Bom Vizinho e Null Object")
class BomVizinhoTest {

    @Test
    @DisplayName("CacadorReal deve retornar seus dados corretamente")
    void cacadorRealDeveRetornarDados() {
        CacadorNullable cacador = new CacadorReal("Geralt","Lobo", 85);
        assertEquals("Geralt", cacador.getNome());
        assertEquals("Lobo", cacador.getEscola());
        assertEquals(850.0, cacador.calcularBonus(), 0.001);
        assertTrue(cacador.isConhecido());
    }

    @Test
    @DisplayName("CacadorDesconhecido deve ter comportamento neutro sem NPE")
    void cacadorDesconhecidoDeveComportarNeutro() {
        CacadorNullable desconhecido = new CacadorDesconhecido();
        assertNotNull(desconhecido.getNome());
        assertNotNull(desconhecido.getEscola());
        assertEquals(0.0, desconhecido.calcularBonus(), 0.001);
        assertFalse(desconhecido.isConhecido());
    }

    @Test
    @DisplayName("ServicoDeContratoSeguro deve processar se NPE passando CacadorDesconhecido")
    void servicoDeveProcessarSemPPEComNullObject() {
        ServicoDeContratoSeguro servico = new ServicoDeContratoSeguro();
        assertDoesNotThrow(() ->
                servico.processarMissao("Caça à Strige",new CacadorDesconhecido())
        );
    }

    @Test
    @DisplayName("ServicoDeContratoSeguro deve processar CacadorReal corretamente")
    void servicoDeveProcessarCacadorReal() {
        ServicoDeContratoSeguro servico = new ServicoDeContratoSeguro();
        assertDoesNotThrow(() ->
                servico.processarMissao("Caça ao Grifo",new CacadorReal("Lambert", "Lobo", 65))
        );
    }
}
