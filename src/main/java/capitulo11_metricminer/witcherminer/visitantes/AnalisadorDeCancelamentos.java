package capitulo11_metricminer.witcherminer.visitantes;

import capitulo11_metricminer.witcherminer.domain.*;
import capitulo11_metricminer.witcherminer.ports.*;

public class AnalisadorDeCancelamentos implements VisitanteDeContrato {
    private final int limite;

    public AnalisadorDeCancelamentos(int limite) { 
        this.limite = limite; 
    }

    @Override
    public void processar(RegistroHistoricoGuilda registro, ContratoRegistrado contrato, MecanismoDePersistencia saida) {
        long canceladas = contrato.getAcoes().stream()
                                  .filter(a -> a.getTipo() == TipoDeAcao.MISSAO_CANCELADA)
                                  .count();
        if (canceladas >= limite) {
            saida.escrever(registro.getNome(), contrato.getId(), contrato.getCacador(), canceladas, "ALERTA");
        }
    }

    @Override
    public String nome() { return "AnalisadorDeCancelamentos"; }
}
