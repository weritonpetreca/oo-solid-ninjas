package capitulo9_maus_cheiros.v2_feature_envy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap9 v2 — Feature Envy")
class FeatureEnvyTest {

    @Test
    @DisplayName("ContratoDeCaca deve calcular imposto internamente sem perigo <= 3")
    void contratoDeveCalcularImpostoBasico() {
        var contrato = new ContratoDeCaca("Lobisomem", 1000.0, 2);
        contrato.processar();
        assertEquals(150.0, contrato.getImposto(), 0.001); // 15% de 1000
    }

    @Test
    @DisplayName("ContratoDeCaca deve aplicar multiplicador para perigo > 3")
    void contratoDeveAplicarMultiplicadorParaPerigoAlto() {
        var contrato = new ContratoDeCaca("Manticora", 1000.0, 4);
        contrato.processar();
        assertEquals(225.0, contrato.getImposto(), 0.001); // 15% * 1.5
    }

    @Test
    @DisplayName("ContratoDeCaca deve marcar como encerrado após processar")
    void contratoDeveMarcarComoEncerrado() {
        var contrato = new ContratoDeCaca("Grifo", 2000.0, 3);
        contrato.processar();
        assertTrue(contrato.isEncerrado());
    }

    @Test
    @DisplayName("ProcessadorDeContratosLimpo deve delegar para o contrato sem Feature Envy")
    void processadorLimpoDeveDelegarParaOContrato() {
        var contrato = new ContratoDeCaca("Basilisco", 3000.0, 5);
        new ProcessadorDeContratosLimpo().processar(contrato);
        assertTrue(contrato.isEncerrado());
        assertTrue(contrato.getImposto() > 0);
    }
}
