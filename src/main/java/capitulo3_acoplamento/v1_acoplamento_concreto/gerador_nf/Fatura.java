package capitulo3_acoplamento.v1_acoplamento_concreto.gerador_nf;

/**
 * 📜 FATURA (OBJETO DE DOMÍNIO)
 *
 * Esta classe representa a entrada do nosso processo.
 * É um POJO (Plain Old Java Object) simples que carrega os dados brutos.
 *
 * No contexto do Acoplamento, ela é uma "Classe Estável" (Stable Dependency).
 * Depender de classes de dados simples (que mudam pouco e não têm comportamento complexo)
 * não é um problema grave. O problema é depender de classes de SERVIÇO (Instáveis).
 *
 * @author Weriton L. Petreca
 */
public class Fatura {

    private String cliente;
    private double valorMensal;

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
