package capitulo6_heranca_composicao.v6_mundo_real;

/**
 * Mago: usa magia para potencializar recompensas em contratos de alto perigo.
 * Recebe bônus dobrado em monstros de nível 4 e 5.
 *
 * ✅ LSP respeitado: retorna recompensa >= 0 em qualquer nível de perigo
 */
public class Mago extends Cacador {

    public Mago(String nome, int nivel, CalculadorDeTaxa taxa) { super(nome, EscolaDeWitcher.VIBORA, nivel, taxa); }

    @Override
    public double calcularRecompensaBruta(ContratoDeCaca contrato) {
        double base = contrato.getValorBase() * contrato.getNivelDePerigo();
        if (contrato.getNivelDePerigo() >= 4 ) {
            return base * 2;
        }
        return base;
    }
}
