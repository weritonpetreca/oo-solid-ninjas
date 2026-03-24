package capitulo11_metricminer.witcherminer.core;

import capitulo11_metricminer.witcherminer.adapters.CSVGuilda;
import capitulo11_metricminer.witcherminer.adapters.RegistroHistoricoFactory;
import capitulo11_metricminer.witcherminer.visitantes.ContadorDeMissoesCompletas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Core: Teste de Integração do Navegador")
class NavegadorDeHistoricoGuildaTest {

    @Test
    @DisplayName("Deve navegar por todos os contratos e executar o visitante")
    void deveNavegarEProcessar() {
        var opcoes = new OpcoesDoWitcherMiner("fake", 1, "saida.csv");
        var registro = RegistroHistoricoFactory.criarGuilda("Teste");
        var csv = new CSVGuilda("teste.csv");
        
        // Configura o navegador para rodar 1 visitante em 1 repositório
        new NavegadorDeHistoricoGuilda(opcoes)
                .em(registro)
                .processar(new ContadorDeMissoesCompletas(), csv)
                .iniciar();
        
        // O repositório fake tem 3 contratos. O visitante deve ter sido chamado 3 vezes.
        assertEquals(3, csv.getLinhas());
    }
}
