package capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain;

/**
 * 📜 NOTA FISCAL (O TROFÉU)
 *
 * Representa o contrato cumprido, o resultado da caçada.
 * É uma entidade rica de domínio — carrega as regras do negócio.
 *
 * ⚔️ IMUTABILIDADE:
 * Notas fiscais, uma vez geradas, não mudam (exceto o imposto calculado).
 * Por isso todos os atributos são `final` exceto o imposto,
 * que é definido durante o processo de geração.
 *
 * 🛡️ SEM FRAMEWORKS:
 * Sem @Entity, sem @JsonProperty, sem @Column.
 * Domínio puro. Se o Hibernate sumir amanhã, esta classe continua funcionando.
 */

public class NotaFiscal {

    private final double valor;
    private final double imposto;

    public NotaFiscal(double valor, double imposto) {
        this.valor = valor;
        this.imposto = imposto;
    }

    public double getValor() {
        return valor;
    }

    public double getImposto() {
        return imposto;
    }
}
