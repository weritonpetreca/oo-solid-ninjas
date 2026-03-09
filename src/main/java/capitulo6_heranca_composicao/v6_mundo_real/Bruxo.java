package capitulo6_heranca_composicao.v6_mundo_real;

public class Bruxo extends Cacador {

    private final double bonusDeMutacao; // fruto das mutações de Kaer Morhen

    public Bruxo(String nome, EscolaDeWitcher escola, int nivel, double bonusDeMutacao, CalculadorDeTaxa taxa) {
        super(nome, escola, nivel, taxa);
        this.bonusDeMutacao = bonusDeMutacao;
    }

    @Override
    public double calcularRecompensaBruta(ContratoDeCaca contrato) {
        // ✅ Regra própria, explícita, sem depender do pai
        double recompensaBase = contrato.getValorBase() * contrato.getNivelDePerigo();
        return recompensaBase * bonusDeMutacao;
    }
}
