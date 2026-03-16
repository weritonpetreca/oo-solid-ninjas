package capitulo8_consistencia.livro_original;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 8, seção 8.6: Imutabilidade x Mutabilidade
// ══════════════════════════════════════════════════════════════════════════════

// ─── Problema: Calendar mutável ───────────────────────────────────────────────

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ❌ PROBLEMA — Objeto mutável usado de forma inesperada.
 *
 * Aniche, p.113:
 * "A classe Calendar, do Java, por exemplo, quando você adiciona alguns dias
 *  nela, muda seu estado interno, para representar a nova data. Isso pode
 *  complicar o desenvolvedor."
 *
 * Código do livro:
 *   Calendar hoje = Calendar.getInstance();  // hoje terá a data de hoje
 *   hoje.add(Calendar.DAY, 1);               // hoje agora terá 1 dia depois de hoje!
 *                                            // então, não é mais hoje!
 *
 * O problema: a variável se chama 'hoje' mas não representa mais hoje.
 */
class DemoCalendarMutavel {
    public static void demosntrar() {
        Calendar hoje = Calendar.getInstance();
        System.out.println("Antes: " + hoje.get(Calendar.DAY_OF_MONTH)); // Ex: 15

        hoje.add(Calendar.DAY_OF_MONTH, 1);
        System.out.println("Depois: " + hoje.get(Calendar.DAY_OF_MONTH)); // Ex: 16";
        // ❌ 'hoje' agora representa amanhã — silenciosamente errado
    }
}

// ─── Problema: Pedido com lista interna exposta ───────────────────────────────

/**
 * ❌ PROBLEMA — getItens() devolve a lista original.
 *
 * Aniche, p.113-114:
 * "A classe está bem escrita. Ela inclusive tem o método adicionaItem(), que
 *  encapsula toda a regra de negócio para se adicionar um item dentro desse
 *  pedido. O problema é que, como esse getItens() devolve a lista original,
 *  qualquer classe pode colocar novos itens nessa lista, sem passar pelas
 *  regras de negócio que um pedido tem."
 */
class PedidoComListaExposta {

    private final List<Item> itens = new ArrayList<>();

    public void adicionaItem(String produto) {
        // regra de negocio totalmente encapsulada aqui para se adicionar um item
        itens.add(new Item(produto, 0));
    }

    // ❌ Devolve a lista original — qualquer código pode modificá-la
    public List<Item> getItens() { return itens; }

    // ✅ Returns a wrapper that blocks modifications
    // public List<Item> getItens() {
    //    return Collections.unmodifiableList(itens);
    // }
}

// ─── Solução: Endereco imutável ───────────────────────────────────────────────

/**
 * ✅ SOLUÇÃO — Endereco imutável do livro.
 *
 * Aniche, p.114:
 * "Escrever uma classe imutável não é complicado. Basta evitar o uso de
 *  setters, por exemplo. Ou, se você precisar dar um método que modifica o
 *  conteúdo do objeto, esse objeto deve devolver uma nova instância dessa
 *  classe, com o novo valor."
 *
 * Aniche, p.115:
 * "Muitas APIs hoje seguem essa ideia. A biblioteca Joda Time é totalmente
 *  imutável. Sempre que você muda algo na data, ela devolve uma nova instância
 *  de LocalDateTime, com os novos dados."
 *
 * "Imagine o mundo real mesmo: algumas coisas não mudam nunca mesmo.
 *  Uma data não muda nunca (o dia 23/01/1986 será o mesmo pra sempre),
 *  um endereço não muda nunca (Rua Vergueiro, 3185, existirá pra sempre),
 *  portanto, são ótimas candidatas a serem classes imutáveis."
 */
final class EnderecoImutavel {

    private final String rua;
    private final int numero;

    public EnderecoImutavel(String rua, int numero) {
        this.rua = rua;
        this.numero = numero;
    }

    public String getRua() { return rua; }
    public int getNumero() { return numero; }

    /**
     * ✅ "Mudar a rua" retorna uma nova instância — original intacta.
     * Aniche, p.114: "ao mudar a rua, ela devolve uma nova instância da classe"
     */
    public EnderecoImutavel setRua(String novaRua) { return new EnderecoImutavel(novaRua, numero); }

    /**
     * ✅ Mesmo para o número.
     */
    public EnderecoImutavel setNumero(int novoNumero) { return new EnderecoImutavel(rua, novoNumero); }

    @Override public String toString() { return rua + ", " + numero; }
}