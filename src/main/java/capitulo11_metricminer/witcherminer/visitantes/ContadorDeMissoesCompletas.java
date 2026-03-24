package capitulo11_metricminer.witcherminer.visitantes;

import capitulo11_metricminer.witcherminer.domain.*;
import capitulo11_metricminer.witcherminer.ports.*;

public class ContadorDeMissoesCompletas implements VisitanteDeContrato {
    @Override
    public void processar(RegistroHistoricoGuilda registro, ContratoRegistrado contrato, MecanismoDePersistencia saida) {
        long concluidas = contrato.getAcoes().stream()
                                  .filter(a -> a.getTipo() == TipoDeAcao.MISSAO_CONCLUIDA)
                                  .count();
        saida.escrever(registro.getNome(), contrato.getId(), contrato.getCacador(), concluidas);
    }

    @Override
    public String nome() { return "ContadorDeMissoesCompletas"; }
}
