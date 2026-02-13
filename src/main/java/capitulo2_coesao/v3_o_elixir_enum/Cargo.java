package capitulo2_coesao.v3_o_elixir_enum;

/**
 * 🧠 CARGO (RICH DOMAIN MODEL)
 *
 * Aqui aplicamos o princípio: "Quem conhece a regra é quem define o tipo".
 *
 * ⚔️ COMO FUNCIONA O ELIXIR:
 * 1. O construtor do Enum recebe uma instância de RegraDeCalculo.
 * 2. Quando criamos o cargo DESENVOLVEDOR, ele já nasce com a estratégia
 * 'DezOuVintePorCento' embutida em suas veias.
 *
 * 🛡️ BENEFÍCIO (OCP - Open/Closed Principle):
 * Se criarmos um cargo 'GERENTE', basta adicionar uma linha aqui:
 * GERENTE(new RegraDeGerente())
 * Nenhuma outra classe do sistema precisará ser recompilada ou tocada.
 */

public enum Cargo {
    DESENVOLVEDOR(new DezOuVintePorCento()),
    DBA(new QuinzeOuVinteCincoPorCento()),
    TESTER(new QuinzeOuVinteCincoPorCento());

    private final RegraDeCalculo regra;

    Cargo(RegraDeCalculo regra) {
        this.regra = regra;
    }

    public RegraDeCalculo getRegra() {
        return regra;
    }
}