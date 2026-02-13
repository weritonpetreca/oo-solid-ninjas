package capitulo1_mindset;

interface Monstro {
    void receberDano(int dano);
    boolean estaVivo();
}

class Grifo implements Monstro {

    private int vida = 100;

    @Override
    public void receberDano(int dano) {
        this.vida -= dano;
        System.out.println("Grifo guinchou de dor! Vida restante: " + vida);
    }

    @Override
    public boolean estaVivo() {
        return vida > 0;
    }
}

class EspadaDePrata {
    public void atacar(Monstro monstro) {
        // A lógica do dano pertence à arma, não ao guerreiro
        int dano = 20;
        monstro.receberDano(dano);
    }
}

// O Bruxo apenas coordena (Mensagens entre objetos)
class Geralt {

    private EspadaDePrata espadaDePrata;

    public Geralt(EspadaDePrata espadaDePrata) {
        this.espadaDePrata = espadaDePrata;
    }

    public void cacar(Monstro monstro) {
        if (monstro.estaVivo()) {
            // O Bruxo não calcula int i = x * y
            // Ele envia uma mensagem para o objeto EspadaDePrata
            espadaDePrata.atacar(monstro);
        }
    }
}

public class BatalhaOO {
    public static void main(String[] args) {
        Grifo real = new Grifo();
        EspadaDePrata aerongight = new EspadaDePrata();
        Geralt loboBranco = new Geralt(aerongight);

        loboBranco.cacar(real);
    }
}
