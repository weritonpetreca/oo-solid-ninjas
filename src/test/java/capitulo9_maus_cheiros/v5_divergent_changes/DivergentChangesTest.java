package capitulo9_maus_cheiros.v5_divergent_changes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap9 v5 — Divergent Changes: classes coesas")
class DivergentChangesTest {

    @Test
    @DisplayName("CalculadorDeImpostoMissao deve calcular 15% sobre o valor base")
    void calculadorDeveCalcuarImpostoCorrecto() {
        var dados = new DadosDaMissao("Lobisomem", 2000.0, "Geralt");
        assertEquals(300.0, new CalculadorDeImpostoMissao().calcular(dados), 0.001);
    }

    @Test
    @DisplayName("ClassificadorDeMonstro deve identificar Grifo como Híbrido")
    void classificadorDeveIdentificarGrifo() {
        assertEquals("Híbrido", new ClassificadorDeMonstro().classificar("Grifo Real"));
    }

    @Test
    @DisplayName("ClassificadorDeMonstro deve identificar Strige como Vampiro")
    void classificadorDeveIdentificarStrige() {
        assertEquals("Vampiro", new ClassificadorDeMonstro().classificar("Grande Strige"));
    }

    @Test
    @DisplayName("ClassificadorDeMonstro deve retornar Desconhecido para tipo não mapeado")
    void classificadorDeveRetornarDesconhecido() {
        assertEquals("Desconhecido", new ClassificadorDeMonstro().classificar("Entidade Misteriosa"));
    }

    @Test
    @DisplayName("GeradorDeRelatorioMissao deve incluir nome do monstro e caçador")
    void geradorDeveIncluirDadosPrincipais() {
        var dados    = new DadosDaMissao("Manticora", 5000.0, "Yennefer");
        var relatorio = new GeradorDeRelatorioMissao().gerar(dados);
        assertTrue(relatorio.contains("Manticora"));
        assertTrue(relatorio.contains("Yennefer"));
    }
}
