package capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain;

/**
 * 📜 FATURA (OBJETO DE DOMÍNIO)
 *
 * Representa o contrato inicial — a demanda do cliente.
 * É uma classe de dados pura, sem dependências externas.
 *
 * ⚔️ ARQUITETURA HEXAGONAL:
 * Esta classe vive no centro do hexágono.
 * Ela não sabe o que é HTTP, banco de dados ou e-mail.
 * É o vocabulário ubíquo do sistema — todos falam "Fatura" e entendem.
 */

public class Fatura {

    private final String cliente;
    private final double valorMensal;

    public Fatura(String cliente, double valorMensal) {
        this.cliente = cliente;
        this.valorMensal = valorMensal;
    }

    public String getCliente() {
        return cliente;
    }

    public double getValorMensal() {
        return valorMensal;
    }
}
