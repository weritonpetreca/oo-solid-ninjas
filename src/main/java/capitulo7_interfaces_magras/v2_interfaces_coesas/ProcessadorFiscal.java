package capitulo7_interfaces_magras.v2_interfaces_coesas;

import java.util.ArrayList;
import java.util.List;

/**
 * Processador que usa as interfaces separadas com segurança.
 *
 * Quem precisa só calcular recebe CalculadorDeImposto.
 * Quem precisa gerar nota recebe GeradorDeNota.
 * Sem surpresas em runtime.
 */
public class ProcessadorFiscal {

    public double somaTodosImpostos(List<CalculadorDeImposto> calculadores, double valor) {
        double total = 0;
        for (CalculadorDeImposto c : calculadores) {
            total += c.imposto(valor);
        }
        return total;
    }

    public List<NotaFiscal> geraTodasAsNotas(List<GeradorDeNota> geradores) {
        List<NotaFiscal> notas = new ArrayList<>();
        for (GeradorDeNota g : geradores) {
            notas.add(g.geraNota()); // ✅ Todos garantidamente geram nota
        }
        return notas;
    }
}
