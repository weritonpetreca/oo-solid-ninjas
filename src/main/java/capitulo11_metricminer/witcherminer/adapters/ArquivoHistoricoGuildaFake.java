package capitulo11_metricminer.witcherminer.adapters;

import capitulo11_metricminer.witcherminer.domain.*;
import capitulo11_metricminer.witcherminer.ports.ArquivoDeContratos;
import java.util.List;

public class ArquivoHistoricoGuildaFake implements ArquivoDeContratos {
    private final String nomeGuilda;

    public ArquivoHistoricoGuildaFake(String nomeGuilda) { 
        this.nomeGuilda = nomeGuilda; 
    }

    @Override
    public List<ReferenciaDeContrato> getReferencias() {
        return List.of(
                new ReferenciaDeContrato("CTR-001"),
                new ReferenciaDeContrato("CTR-002"),
                new ReferenciaDeContrato("CTR-003")
        );
    }

    @Override
    public ContratoRegistrado getContrato(String id) {
        return switch (id) {
            case "CTR-001" -> new ContratoRegistrado(id, "Geralt de Rívia", "Caça ao Grifo Real de Velen",
                    List.of(
                            new AcaoDoCacador("Grifo Real", TipoDeAcao.MISSAO_NOVA, 0.0, "Aceito"),
                            new AcaoDoCacador("Grifo Real", TipoDeAcao.MISSAO_CONCLUIDA, 0.0, "Eliminado"),
                            new AcaoDoCacador("Grifo Real", TipoDeAcao.RECOMPENSA_PAGA, 3000.0, "Pago")));
            case "CTR-002" -> new ContratoRegistrado(id, "Lambert", "Caça à Strige",
                    List.of(
                            new AcaoDoCacador("Strige", TipoDeAcao.MISSAO_NOVA, 0.0, "Aceito"),
                            new AcaoDoCacador("Strige", TipoDeAcao.MISSAO_CANCELADA, 0.0, "Fugiu")));
            case "CTR-003" -> new ContratoRegistrado(id, "Ciri", "Caça ao Djinn",
                    List.of(
                            new AcaoDoCacador("Djinn", TipoDeAcao.MISSAO_NOVA, 0.0, "Aceito"),
                            new AcaoDoCacador("Djinn", TipoDeAcao.MISSAO_CONCLUIDA, 0.0, "Concluída"),
                            new AcaoDoCacador("Djinn", TipoDeAcao.RECOMPENSA_PAGA, 8000.0, "Pago")));
            default -> new ContratoRegistrado(id, "Desconhecido", "Contrato não encontrado", List.of());
        };
    }
}
