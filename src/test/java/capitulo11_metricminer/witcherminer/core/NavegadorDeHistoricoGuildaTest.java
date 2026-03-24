package capitulo11_metricminer.witcherminer.core;

import capitulo11_metricminer.witcherminer.adapters.CSVGuilda;
import capitulo11_metricminer.witcherminer.adapters.RegistroHistoricoFactory;
import capitulo11_metricminer.witcherminer.domain.RegistroHistoricoGuilda;
import capitulo11_metricminer.witcherminer.visitantes.CalculadoraDeRecompensas;
import capitulo11_metricminer.witcherminer.visitantes.ContadorDeMissoesCompletas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Core: Teste de Integração do Navegador")
class NavegadorDeHistoricoGuildaTest {

    @Test
    @DisplayName("Deve navegar por todos os contratos e executar um visitante")
    void deveNavegarEProcessarUmVisitante() {
        var opcoes = new OpcoesDoWitcherMiner("fake", 1, "saida.csv");
        var registro = RegistroHistoricoFactory.criarGuilda("Teste");
        var csv = new CSVGuilda("teste.csv");
        
        new NavegadorDeHistoricoGuilda(opcoes)
                .em(registro)
                .processar(new ContadorDeMissoesCompletas(), csv)
                .iniciar();
        
        assertEquals(3, csv.getLinhas());
    }

    @Test
    @DisplayName("Deve executar múltiplos visitantes em múltiplos repositórios")
    void deveExecutarMultiplosVisitantes() {
        var opcoes = new OpcoesDoWitcherMiner("fake", 1, "saida.csv");
        var registro1 = RegistroHistoricoFactory.criarGuilda("Guilda do Lobo");
        var registro2 = RegistroHistoricoFactory.criarGuilda("Escola de Aretuza");

        var csvMissoes = new CSVGuilda("missoes.csv");
        var csvRecompensas = new CSVGuilda("recompensas.csv");

        new NavegadorDeHistoricoGuilda(opcoes)
                .em(registro1, registro2)
                .processar(new ContadorDeMissoesCompletas(), csvMissoes)
                .processar(new CalculadoraDeRecompensas(), csvRecompensas)
                .iniciar();

        // 2 repositórios x 3 contratos cada = 6 execuções para cada visitante
        assertEquals(6, csvMissoes.getLinhas());
        assertEquals(6, csvRecompensas.getLinhas());
    }

    @Test
    @DisplayName("Não deve fazer nada se nenhum repositório for fornecido")
    void naoDeveFazerNadaSemRepositorios() {
        var opcoes = new OpcoesDoWitcherMiner("fake", 1, "saida.csv");
        var csv = new CSVGuilda("teste.csv");

        new NavegadorDeHistoricoGuilda(opcoes)
                // .em() -> Nenhum repositório
                .processar(new ContadorDeMissoesCompletas(), csv)
                .iniciar();
        
        assertEquals(0, csv.getLinhas());
    }

    @Test
    @DisplayName("Não deve fazer nada se nenhum visitante for fornecido")
    void naoDeveFazerNadaSemVisitantes() {
        var opcoes = new OpcoesDoWitcherMiner("fake", 1, "saida.csv");
        var registro = RegistroHistoricoFactory.criarGuilda("Teste");
        var csv = new CSVGuilda("teste.csv");

        new NavegadorDeHistoricoGuilda(opcoes)
                .em(registro)
                // .processar() -> Nenhum visitante
                .iniciar();
        
        assertEquals(0, csv.getLinhas());
    }
}
