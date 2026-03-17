package capitulo9_maus_cheiros.livro_original;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 9, seção 9.2: Feature Envy
// ══════════════════════════════════════════════════════════════════════════════

/**
 * Aniche, p.121:
 * "Feature Envy é o nome que damos para quando um método está mais interessado
 *  em outro objeto do que no objeto em que ele está inserido."
 *
 * "Esse é o típico código procedural. Módulos fazem uso intenso de dados que
 *  estão em outros lugares. Isso faz com que a manutenção de tudo relacionado
 *  à nota fiscal fique mais difícil."
 */

// ─── NotaFiscal — classe de domínio ──────────────────────────────────────────

import java.time.LocalDateTime;

/**
 * NotaFiscal com seus atributos — exatamente como no livro.
 * Código do livro, p.121.
 */
class NotaFiscal {
    private double valor;
    private String cliente;
    private int qtdDeItens;
    private LocalDateTime dataDeGeracao;
    private double valorImposto;
    private boolean finalizada;

    public NotaFiscal(double valor, String cliente, int qtdDeItens) {
        this.valor = valor;
        this.cliente = cliente;
        this.qtdDeItens = qtdDeItens;
    }

    public double calculaImposto() { return valor * 0.15; }

    public double getValor() { return valor; }
    public String getCliente() { return cliente; }
    public int getQtdDeItens() { return qtdDeItens; }

    public void setaValorImposto(double imposto) { this.valorImposto = imposto; }
    public void marcaDataDeGeracao() { this.dataDeGeracao = LocalDateTime.now(); }
    public void finaliza() { this.finalizada = true; }
    public boolean isFinalizada() { return finalizada; }
    public double getValorImposto() { return valorImposto; }
}

// ─── PROBLEMA: Gerenciador com Feature Envy ──────────────────────────────────

/**
 * ❌ Gerenciador — Feature Envy clássico.
 *
 * Aniche, p.121-122:
 * "A classe NotaFiscal é uma entidade como qualquer outra, com atributos e
 *  comportamentos. A classe Gerenciador faz uso dela. Mas é fácil ver que o
 *  método processa() poderia estar em qualquer outro lugar: ele não faz uso de
 *  nada que a sua classe tem. E ela faz diversas manipulações na nota fiscal."
 */
class Gerenciador {
    private Usuario usuarioLogado;

    public Gerenciador(Usuario usuarioLogado) { this.usuarioLogado = usuarioLogado; }

    /**
     * ❌ processa() só usa dados de NotaFiscal — Feature Envy total.
     * Código direto do livro, p.122.
     */
    public void processa(NotaFiscal nf) {
        double imposto = nf.calculaImposto();

        if (nf.getQtdDeItens() > 2) {
            imposto = imposto * 1.1;
        }

        nf.setaValorImposto(imposto);
        nf.marcaDataDeGeracao();
        nf.finaliza();
    }
}

class Usuario {
    private final String nome;
    public Usuario(String nome) { this.nome = nome; }
    public String getNome() { return nome; }
}

// ─── SOLUÇÃO: comportamento movido para NotaFiscal ────────────────────────────

/**
 * ✅ NotaFiscalComportamental — comportamento no lugar certo.
 *
 * Aniche, p.122:
 * "Pergunte a você mesmo se ele não poderia estar dentro da classe que aquele
 *  método está usando."
 *
 * O método processa() pertence a NotaFiscal — ela tem todos os dados
 * necessários e não depende de nada externo.
 */
class NotaFiscalComportamental {
    private final double valor;
    private final String cliente;
    private final int qtdDeItens;
    private LocalDateTime dataDeGeracao;
    private double valorImposto;
    private boolean finalizada;

    public NotaFiscalComportamental(double valor, String cliente, int qtdDeItens) {
        this.valor = valor;
        this.cliente = cliente;
        this.qtdDeItens = qtdDeItens;
    }

    /**
     * ✅ processa() agora vive onde os dados vivem.
     *    Gerenciador não precisa mais conhecer os internos de NotaFiscal.
     */
    public void processar() {
        double imposto = calcularImposto();

        if (qtdDeItens > 2) {
            imposto = imposto * 1.1;
        }

        this.valorImposto = imposto;
        marcarDataDeGeracao();
        finalizar();
    }

    private double calcularImposto() { return valor * 0.15; }
    private void marcarDataDeGeracao() { this.dataDeGeracao = LocalDateTime.now(); }
    private void finalizar() { this.finalizada = true; }

    public double getValorImposto() { return valorImposto; }
    public boolean isFinalizada() { return finalizada; }
}

/**
 * ✅ GerenciadorLimpo — sem Feature Envy.
 */
class GerenciadorLimpo {
    public void processa(NotaFiscalComportamental nf) { nf.processar(); } // delega - o comportamento está no lugar certo
}