package capitulo11_metricminer.witcherminer.core;

import capitulo11_metricminer.witcherminer.domain.RegistroHistoricoGuilda;
import capitulo11_metricminer.witcherminer.domain.ReferenciaDeContrato;
import capitulo11_metricminer.witcherminer.domain.ContratoRegistrado;
import capitulo11_metricminer.witcherminer.ports.VisitanteDeContrato;
import capitulo11_metricminer.witcherminer.ports.MecanismoDePersistencia;

import java.util.ArrayList;
import java.util.List;

public class NavegadorDeHistoricoGuilda {
    private final OpcoesDoWitcherMiner opcoes;
    private final List<RegistroHistoricoGuilda> registros = new ArrayList<>();
    private final List<ConfigDeProcessamento> configs = new ArrayList<>();

    public NavegadorDeHistoricoGuilda(OpcoesDoWitcherMiner opcoes) {
        this.opcoes = opcoes;
    }

    public NavegadorDeHistoricoGuilda em(RegistroHistoricoGuilda... registros) {
        for (var r : registros) this.registros.add(r);
        return this;
    }

    public NavegadorDeHistoricoGuilda processar(VisitanteDeContrato visitante, MecanismoDePersistencia saida) {
        configs.add(new ConfigDeProcessamento(visitante, saida));
        return this;
    }

    public void iniciar() {
        System.out.println("    [WitcherMiner] Iniciando análise histórica...");
        System.out.println("    [WitcherMiner] Threads configuradas: " + opcoes.getThreads());

        for (var registro : registros) {
            System.out.println("    [WitcherMiner] Analisando: " + registro.getNome());
            List<ReferenciaDeContrato> refs = registro.getArquivo().getReferencias();

            for (var ref : refs) {
                ContratoRegistrado contrato = registro.getArquivo().getContrato(ref.getId());
                for (var cfg : configs) {
                    cfg.visitante().processar(registro, contrato, cfg.saida());
                }
            }
            for (var cfg : configs) {
                cfg.saida().fechar();
            }
        }
        System.out.println("    [WitcherMiner] Análise concluída.");
    }

    private record ConfigDeProcessamento(VisitanteDeContrato visitante, MecanismoDePersistencia saida) {}
}
