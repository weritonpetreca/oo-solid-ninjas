package capitulo8_consistencia.livro_original;

import java.util.ArrayList;
import java.util.List;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 8, seção 8.1: Construtores Ricos
// ══════════════════════════════════════════════════════════════════════════════

// ─── Problema: Pedido sem construtor rico ─────────────────────────────────────

/**
 * ❌ PROBLEMA — Pedido instanciado sem cliente.
 *
 * Aniche, p.100:
 * "Apesar de o método adicionaItem() encapsular corretamente a ideia de se
 *  adicionar um item ao pedido, veja que conseguimos instanciar um pedido sem
 *  cliente. Repare que o método setCliente() até existe e deve ser usado para
 *  armazenar o cliente. Mas agora depende de a classe cliente invocá-lo;
 *  se ela não invocar, o objeto estará em um estado inválido."
 *
 * Uso problemático (do livro):
 *   Pedido festa = new Pedido();
 *   festa.adicionaItem(new Item("SALGADO", 50.0));
 *   festa.adicionaItem(new Item("REFRIGERANTE", 50.0));
 *   System.out.println(festa.getValorTotal()); // cliente é nulo!
 */
class PedidoSemConstrutorRico {

    private Cliente cliente;
    private double valorToral;
    private List<Item> itens;

    public PedidoSemConstrutorRico() { this.itens = new ArrayList<>(); }

    public void adicionaItem(Item it) {
        itens.add(it);
        recalculaValorTotal();
    }

    private void recalculaValorTotal() {
        this.valorToral = itens.stream()
                .mapToDouble(Item::getValor)
                .sum();
    }

    public Cliente getCliente() { return cliente; }
}

// ─── Solução: Pedido com construtor rico ─────────────────────────────────────

/**
 * ✅ SOLUÇÃO — Pedido exige Cliente no construtor.
 *
 * Aniche, p.101:
 * "Repare que agora é impossível criar um Pedido sem passar um Cliente.
 *  Podemos levar essa mesma ideia para ela. Se não faz sentido criar um cliente
 *  sem nome e telefone, devemos pedi-los no construtor."
 */
class Pedido {

    private final Cliente cliente;
    private double valorTotal;
    private final List<Item> itens;

    // ✅ Construtor rico — impossível criar Pedido sem cliente
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.valorTotal = 0;
        this.itens = new ArrayList<>();
    }

    public void adicionaItem(Item it) {
        itens.add(it);
        recalculaValorTotal();
    }

    public void recalculaValorTotal() {
        this.valorTotal = itens.stream()
                .mapToDouble(Item::getValor)
                .sum();
    }

    public double getValorTotal() { return valorTotal;}
    public Cliente getCliente() { return cliente; }
    public List<Item> getItens() { return itens; }
}

/**
 * ✅ SOLUÇÃO — Cliente exige nome e telefone.
 *
 * Aniche, p.101-102:
 * "Se não faz sentido criar um cliente sem nome e telefone, devemos pedi-los
 *  no construtor."
 *
 * Uso do livro:
 *   Cliente mauricio = new Cliente("Mauricio", "1234-5678");
 *   Pedido festa = new Pedido(mauricio);
 *   festa.adicionaItem(new Item("MUSICA AMBIENTE", 450.0));
 */
class Cliente {

    private final String nome;
    private final String telefone;

    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone;
    }

    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
}

// ─── Item com construtor rico ─────────────────────────────────────────────────

/**
 * Aniche, p.102:
 * "Apesar de você não ter reparado, desde o primeiro exemplo, a classe Item
 *  já possuía um construtor mais rico."
 */
class Item {
    private final String nome;
    private final double valor;

    public Item(String nome, double valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() { return nome; }
    public double getValor() { return valor; }
}

// ─── Carro: dois construtores (um com padrões) ───────────────────────────────

/**
 * ✅ Dois construtores — Aniche, p.102-103:
 * "Outro bom exemplo é o caso de atributos que são necessários, mas que é
 *  possível prover um valor padrão para ele. Imagine uma classe Carro que
 *  tem como atributos Pneu e Motor. Todo carro tem pneu e motor, mas você é
 *  capaz de prover um pneu e um motor padrões. Por que não dois construtores,
 *  sendo que um deles provê os valores padrão?"
 */
class Carro {
    private final Pneu pneu;
    private final Motor motor;

    public Carro(Pneu pneu, Motor motor) {
        this.pneu = pneu;
        this.motor = motor;
    }

    // ✅ Construtor de conveniência com valores padrão
    public Carro() { this(new PneuPadrao(), new MotorPadrao()); }

    public Pneu getPneu() { return pneu; }
    public Motor getMotor() { return motor; }
}

interface Pneu { String getTipo(); }
interface Motor { String getTipo(); }

class PneuPadrao implements Pneu { @Override public String getTipo() { return "Pneu Padrão"; } }
class MotorPadrao implements Motor { @Override public String getTipo() { return "Motor Padrão"; } }
class PneuEsportivo implements Pneu { @Override public String getTipo() { return "Pneu Esportivo"; } }