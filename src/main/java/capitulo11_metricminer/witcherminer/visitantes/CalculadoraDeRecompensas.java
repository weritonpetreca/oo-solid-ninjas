package capitulo11_metricminer.witcherminer.visitantes;

import capitulo11_metricminer.witcherminer.domain.*;
import capitulo11_metricminer.witcherminer.ports.*;

public class CalculadoraDeRecompensas implements VisitanteDeContrato {
    private static final double TAXA_GUILDA = 0.15;

    @Override
    public void processar(RegistroHistoricoGuilda registro, ContratoRegistrado contrato, MecanismoDePersistencia saida) {
        double totalBruto = contrato.getAcoes().stream()
                .filter(a -> a.getTipo() == TipoDeAcao.RECOMPENSA_PAGA)
                .mapToDouble(AcaoDoCacador::getRecompensa)
                .sum();
                
        double taxa = totalBruto * TAXA_GUILDA;
        double liquida = totalBruto - taxa;
        
        saida.escrever(
                registro.getNome(), 
                contrato.getId(), 
                contrato.getCacador(), 
                String.format("%.2f", totalBruto), 
                String.format("%.2f", taxa), 
                String.format("%.2f", liquida)
        );
    }

    @Override
    public String nome() { return "CalculadoraDeRecompensas"; }
}
