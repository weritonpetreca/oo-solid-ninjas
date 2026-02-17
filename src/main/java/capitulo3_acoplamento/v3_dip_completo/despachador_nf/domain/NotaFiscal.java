package capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain;

/**
 * 📜 O TROFÉU (DOMÍNIO PURO)
 *
 * A Nota Fiscal é o coração do nosso sistema.
 * Ela não conhece banco de dados, não conhece correios, não conhece nada
 * além de si mesma. É uma classe de domínio pura.
 *
 * ⚔️ ARQUITETURA HEXAGONAL:
 * No centro do hexágono vivem apenas as classes de domínio.
 * Elas não dependem de ninguém. Todo mundo depende delas.
 * Isso as torna as classes mais estáveis do sistema.
 */

public class NotaFiscal {

    private final double valor;
    private double imposto;

    public NotaFiscal(double valor) {
        this.valor = valor;
        this.imposto = imposto;
    }

    public double getValor() {
        return valor;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public double getImposto() {
        return imposto;
    }
}
