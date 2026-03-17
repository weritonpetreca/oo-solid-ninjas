package capitulo9_maus_cheiros.v1_refused_bequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap9 v1 — Refused Bequest")
class RefusedBequestTest {

    @Test
    @DisplayName("EscolaDeAlquimiaComSmell lança exceção ao chamar treinarCombate — LSP violado")
    void escolaDeAlquimiaComSmellDeveLancarExcecao() {
        var alquimia = new EscolaDeAlquimiaComSmell("Escola das Ervas", 80);
        assertThrows(UnsupportedOperationException.class, alquimia::treinarCombate);
    }

    @Test
    @DisplayName("EscolaDeAlquimiaComSmell retorna 0 para calcularBonusDeBatalha — silenciosamente errado")
    void escolaDeAlquimiaComSmellRetornaZeroParaBatalha() {
        var alquimia = new EscolaDeAlquimiaComSmell("Escola das Ervas", 80);
        assertEquals(0, alquimia.calcularBonusDeBatalha());
    }

    @Test
    @DisplayName("EscolaDeGuerraReal treina combate e calcula bônus corretamente")
    void escolaDeGuerraRealFuncionaCorretamente() {
        var lobo = new EscolaDeGuerraReal("Escola do Lobo", 95);
        assertDoesNotThrow(lobo::treinarCombate);
        assertEquals(190, lobo.calcularBonusDeBatalha()); // 95 * 2
    }

    @Test
    @DisplayName("EscolaDeAlquimiaReal não possui treinarCombate — impossível violar LSP")
    void escolaDeAlquimiaRealFabricaPocao() {
        var ervas = new EscolaDeAlquimiaReal("Escola das Ervas", 80);
        assertDoesNotThrow(() -> ervas.fabricarPocao("Poção de Swallow"));
        assertEquals(240, ervas.calcularPotenciaDaFormula()); // 80 * 3
    }

    @Test
    @DisplayName("ambas as escolas geram insígnia com prefixos distintos")
    void escolasGerامInsigniaComPrefixoDistinto() {
        var guerra   = new EscolaDeGuerraReal("Lobo",  90);
        var alquimia = new EscolaDeAlquimiaReal("Ervas", 80);

        assertTrue(guerra.gerarInsignia().startsWith("⚔"));
        assertTrue(alquimia.gerarInsignia().startsWith("⚗"));
    }
}

