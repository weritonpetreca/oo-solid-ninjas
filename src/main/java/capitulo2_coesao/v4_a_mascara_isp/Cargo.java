package capitulo2_coesao.v4_a_mascara_isp;

/**
 * 🧠 CARGO (RICH DOMAIN MODEL)
 *
 * O Enum continua sendo o cérebro que decide qual regra usar.
 *
 * ⚔️ POLIMORFISMO:
 * Cada constante do Enum já nasce sabendo sua estratégia de batalha (Regra).
 * Isso elimina a necessidade de IFs espalhados pelo código perguntando "quem é você?".
 *
 * Princípio OCP (Open/Closed): Para adicionar um novo cargo, basta adicionar uma linha aqui.
 * O resto do sistema permanece intocado.
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