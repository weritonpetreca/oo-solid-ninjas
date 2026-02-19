package capitulo1_mindset;

/**
 * 🐺 CAPÍTULO 1: O MINDSET DO BRUXO (ORIENTAÇÃO A OBJETOS)
 *
 * Diferente do soldado raso (Procedural), o Bruxo não tenta fazer tudo sozinho.
 * Ele confia em suas ferramentas e nos contratos estabelecidos.
 *
 * AQUI VEMOS:
 * 1. Encapsulamento: O Grifo cuida da sua própria vida.
 * 2. Coesão: A Espada sabe quanto dano causa.
 * 3. Troca de Mensagens: Geralt apenas diz "Ataque", ele não calcula subtração de HP.
 */

/**
 * 📜 O CONTRATO (INTERFACE)
 * Define o comportamento esperado de qualquer monstro, seja um Grifo, uma Striga ou um Basilisco.
 * O Bruxo não precisa saber QUAL monstro é, apenas que ele pode receber dano.
 */
interface Monstro {
    void receberDano(int dano);
    boolean estaVivo();
}

/**
 * 🦅 O INIMIGO (OBJETO COM ESTADO)
 * O Grifo protege seu estado interno (vida).
 * Ninguém altera 'vida' diretamente (private). Apenas através de métodos (receberDano).
 */
class Grifo implements Monstro {

    private int vida = 100;

    @Override
    public void receberDano(int dano) {
        this.vida -= dano;
        System.out.println("🦅 Grifo guinchou de dor! Vida restante: " + vida);
    }

    @Override
    public boolean estaVivo() {
        return vida > 0;
    }
}

/**
 * ⚔️ A FERRAMENTA (RESPONSABILIDADE ÚNICA)
 * A lógica de cálculo de dano pertence à arma.
 * Se a espada for afiada ou encantada, apenas esta classe muda. Geralt não precisa reaprender a lutar.
 */
class EspadaDePrata {
    public void atacar(Monstro monstro) {
        // A lógica do dano pertence à arma, não ao guerreiro
        int dano = 20;
        monstro.receberDano(dano);
    }
}

/**
 * 👤 O COORDENADOR (TROCA DE MENSAGENS)
 * O Bruxo apenas coordena a batalha.
 * Ele não faz contas de subtração. Ele envia mensagens ("atacar") para seus objetos.
 */
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
        infra.Console.consertarAcentuacao();

        System.out.println("⚔️ INICIANDO A CAÇADA (MODELO OO) ⚔️\n");

        Grifo real = new Grifo();
        EspadaDePrata aerondight = new EspadaDePrata(); // Espada lendária
        Geralt loboBranco = new Geralt(aerondight);

        loboBranco.cacar(real);
    }
}
