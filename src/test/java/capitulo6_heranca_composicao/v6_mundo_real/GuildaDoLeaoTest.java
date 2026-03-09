package capitulo6_heranca_composicao.v6_mundo_real;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🦁 v6: O Mundo Real (Herança e Composição em Harmonia)")
class GuildaDoLeaoTest {

    private CalculadorDeTaxa taxaPadrao;
    private CalculadorDeTaxa taxaLobo;
    private ContratoDeCaca contratoEstria;
    private ContratoDeCaca contratoWyvern;

    @BeforeEach
    void setup() {
        taxaPadrao = new TaxaPadraoDaGuilda();       // 15%
        taxaLobo   = new TaxaNegociadaEscolaDoLobo();  // 10%
        contratoEstria = new ContratoDeCaca("Estria (Espectro)", 500.0, 3);
        contratoWyvern = new ContratoDeCaca("Wyvern de Gelo", 1000.0, 5);
    }

    @Test
    @DisplayName("Bruxo usa Herança para reutilizar lógica e Composição para taxa")
    void bruxoCalculaRecompensaComBonusDeMutacao() {
        Bruxo geralt = new Bruxo("Geralt de Rívia", EscolaDeWitcher.LOBO, 90, 1.5, taxaLobo);
        // bruta = 500 * 3 * 1.5 = 2250
        assertEquals(2250.0, geralt.calcularRecompensaBruta(contratoEstria), 0.001);
    }

    @Test
    @DisplayName("Composição permite trocar a estratégia de taxa sem mudar a classe Bruxo")
    void bruxoComTaxaNegociadaPagaMenosAGuilda() {
        Bruxo geralt = new Bruxo("Geralt de Rívia", EscolaDeWitcher.LOBO, 90, 1.5, taxaLobo);
        Bruxo lambert = new Bruxo("Lambert", EscolaDeWitcher.LOBO, 90, 1.5, taxaPadrao);

        double liquidaGeralt = geralt.calcularRecompensaLiquida(contratoEstria);  // 10%
        double liquidaLambert = lambert.calcularRecompensaLiquida(contratoEstria); // 15%

        // Geralt (taxa 10%) fica com mais que Lambert (taxa 15%) para mesma bruta base
        assertTrue(liquidaGeralt > liquidaLambert);
    }

    @Test
    @DisplayName("Arqueiro tem sua própria regra de cálculo (Polimorfismo)")
    void arqueiroCalculaRecompensaPorExperiencia() {
        Arqueiro filadelf = new Arqueiro("Filadelf", 60, taxaPadrao);
        // bruta = 500 + (60 * 5) = 800
        assertEquals(800.0, filadelf.calcularRecompensaBruta(contratoEstria), 0.001);
    }

    @Test
    @DisplayName("Mago aplica regra complexa de perigo (Lógica encapsulada)")
    void magoDobraRecompensaEmContratosDeAltoPerigo() {
        Mago yennefer = new Mago("Yennefer de Vengerberg", 95, taxaPadrao);
        // nível 5: bruta = 1000 * 5 * 2 = 10000
        assertEquals(10000.0, yennefer.calcularRecompensaBruta(contratoWyvern), 0.001);
    }

    @Test
    @DisplayName("Mago respeita a regra base quando perigo é baixo")
    void magoNaoDobraEmContratosDePerigoBaixo() {
        Mago yennefer = new Mago("Yennefer de Vengerberg", 95, taxaPadrao);
        // nível 3 (< 4): sem bônus duplo = 500 * 3 = 1500
        assertEquals(1500.0, yennefer.calcularRecompensaBruta(contratoEstria), 0.001);
    }

    @Test
    @DisplayName("LSP Garantido: Guilda processa lista heterogênea sem quebrar")
    void guildaProcessaTodosOsTiposSemExcecao() {
        // ✅ LSP: qualquer subclasse de Cacador na lista funciona sem exceção
        List<Cacador> equipe = List.of(
                new Bruxo("Geralt", EscolaDeWitcher.LOBO, 90, 1.5, taxaLobo),
                new Arqueiro("Eskel", 75, taxaPadrao),
                new Mago("Triss Merigold", 88, taxaPadrao)
        );

        GuildaDoLeao guilda = new GuildaDoLeao();
        assertDoesNotThrow(() -> guilda.imprimirRelatorio(equipe, contratoWyvern));
    }

    @Test
    @DisplayName("Herança de Estado: Todos os caçadores herdam o contador de contratos")
    void todosCacadoresHerdamContadorDeContratos() {
        Bruxo geralt = new Bruxo("Geralt", EscolaDeWitcher.LOBO, 90, 1.5, taxaLobo);
        
        assertEquals(0, geralt.getTotalContratos());
        
        // Agora passamos o contrato como argumento
        geralt.registrarContrato(contratoEstria);
        geralt.registrarContrato(contratoWyvern);
        
        assertEquals(2, geralt.getTotalContratos());
    }
}
