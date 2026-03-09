package capitulo6_heranca_composicao.v6_mundo_real;

import java.util.ArrayList;
import java.util.List;

/**
 * Caçador de monstros — classe base da hierarquia.
 *
 * Aplica os princípios do Cap. 6:
 *   ✅ Herança onde há relação "X é um Y": Bruxo É UM Caçador, Arqueiro É UM Caçador
 *   ✅ Composição para comportamento variável: CalculadorDeTaxa é plugável
 *   ✅ LSP garantido: calcularRecompensa() funciona em todos sem exceção
 *   ✅ Contrato LSP: toda subclasse retorna recompensa >= 0
 *
 * Contrato do método calcularRecompensa():
 *   Pré-condição:  contrato não nulo
 *   Pós-condição:  retorna valor >= 0, nunca lança exceção
 */
public abstract class Cacador {
    protected final String nome;
    protected final EscolaDeWitcher escola;
    protected final int nivelDeExperiencia; // 1 a 100
    private final CalculadorDeTaxa calculadorDeTaxa;
    private final List<ContratoDeCaca> historico = new ArrayList<>();

    public Cacador(String nome, EscolaDeWitcher escola, int nivelDeExperiencia, CalculadorDeTaxa calculadorDeTaxa) {
        this.nome = nome;
        this.escola = escola;
        this.nivelDeExperiencia = nivelDeExperiencia;
        this.calculadorDeTaxa = calculadorDeTaxa;
    }

    /**
     * Contrato LSP: toda subclasse calcula a recompensa bruta sem exceção.
     * A fórmula varia por tipo de caçador, mas o contrato é o mesmo.
     */
    public abstract double calcularRecompensaBruta(ContratoDeCaca contrato);

    /**
     * Template Method: recompensa líquida = bruta - taxa da guilda.
     * ✅ Usa composição (CalculadorDeTaxa) para o cálculo da taxa
     */

    public double calcularRecompensaLiquida(ContratoDeCaca contrato) {
        double bruta = calcularRecompensaBruta(contrato);
        double taxa = calculadorDeTaxa.calculaTaxa(bruta);
        return bruta - taxa;
    }

    public void registrarContrato(ContratoDeCaca contrato) { historico.add(contrato); }

    public int getTotalContratos() { return historico.size(); }
    public String getNome() { return nome; }
    public EscolaDeWitcher getEscola() { return escola; }

    @Override
    public String toString() {
        return String.format("%-20s | %-7s | Nível %3d",
                nome, escola.name(), nivelDeExperiencia);
    }
}
