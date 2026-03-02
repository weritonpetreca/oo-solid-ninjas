package capitulo5_encapsulamento.v5_modelo_anemico;

import java.util.ArrayList;
import java.util.List;
/**
 * ANTIPADRÃO v5: Modelo Anêmico (Aniche, Cap. 5, seção 5.9)
 *
 * A Fatura é uma casca vazia: tem atributos, mas zero comportamento de negócio.
 * É uma struct disfarçada de classe.
 *
 * O que está errado:
 *   ❌ Nenhuma regra de negócio aqui dentro
 *   ❌ Tudo público via getters e setters
 *   ❌ Qualquer classe pode colocar a Fatura em qualquer estado, a qualquer hora
 *   ❌ A "lógica" vive espalhada nas classes de serviço (FaturaBO, FaturaService...)
 *
 * Aniche cita que isso era "boas práticas" da era JavaEE/EJB.
 * Hoje sabemos: é código procedural em linguagem orientada a objetos.
 * A linguagem é Java. O paradigma é C.
 */
public class Fatura {

    private String cliente;
    private double valor;
    private boolean pago;
    private List<String> pagamentos = new ArrayList<>();

    // Getters e setters para tudo — sem critério, sem proteção
    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public List<String> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<String> pagamentos) {
        this.pagamentos = pagamentos;
    }
}
