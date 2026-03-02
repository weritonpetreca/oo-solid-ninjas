package capitulo5_encapsulamento.v1_problema_encapsulamento;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEMA v1: Fatura expõe tudo, não encapsula nada.
 *
 * Problemas (Aniche, Cap. 5):
 * 1. getPagamentos() retorna a lista real → qualquer um pode add/remove sem controle
 * 2. setPago(boolean) é público → a regra "quando pagar" vive FORA da Fatura
 *    Quem sabe quando a fatura está paga? O Processador. E amanhã o outro Processador.
 *    E depois mais outro... todos repetindo a mesma lógica.
 */
public class Fatura {
     private double valor;
     private boolean pago;
     private List<Pagamento> pagamentos;

     public Fatura(double valor) {
         this.valor = valor;
         this.pago = false;
         this.pagamentos = new ArrayList<>();
     }

    public double getValor() {
        return valor;
    }

    public boolean isPago() {
        return pago;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }
}
