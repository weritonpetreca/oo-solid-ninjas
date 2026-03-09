package capitulo6_heranca_composicao.v6_mundo_real;

/**
 * Arqueiro: humano especializado em combate à distância.
 *
 * Fórmula: valor_base + bônus_por_experiência
 * Sem mutações — ganho mais modesto, mas constante.
 *
 * ✅ LSP respeitado: retorna recompensa >= 0, nunca lança exceção
 */
public class Arqueiro extends Cacador {
    public Arqueiro(String nome, int nivel, CalculadorDeTaxa taxa) { super(nome, EscolaDeWitcher.GRIFO, nivel, taxa); }

    @Override
    public double calcularRecompensaBruta(ContratoDeCaca contrato) {
        // ✅ Regra própria: bônus cresce linearmente com experiência
        double bonusExperiencia = nivelDeExperiencia * 5.0;
        return contrato.getValorBase() + bonusExperiencia;
    }


}
