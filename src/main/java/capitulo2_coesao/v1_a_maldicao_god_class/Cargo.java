package capitulo2_coesao.v1_a_maldicao_god_class;

/**
 * 🏷️ CARGO (ENUM SIMPLES / ANÊMICO)
 *
 * Nesta versão inicial (e amaldiçoada), o Cargo serve apenas como uma etiqueta.
 * Ele não possui inteligência, estratégia ou comportamento próprio.
 *
 * ⚠️ O SINAL DE PERIGO:
 * Como este Enum não sabe "como se calcula seu próprio imposto", ele força
 * classes externas (como a CalculadoraDeSalario) a usarem IFs e SWITCHES
 * para tomar decisões baseadas nele.
 *
 * "Um monstro que não sabe se defender depende de outros para lutar."
 */
public enum Cargo {
    DESENVOLVEDOR,
    DBA,
    TESTER
}