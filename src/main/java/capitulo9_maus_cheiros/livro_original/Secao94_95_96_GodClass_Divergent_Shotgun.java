package capitulo9_maus_cheiros.livro_original;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 9
// Seções 9.4 (God Class), 9.5 (Divergent Changes), 9.6 (Shotgun Surgery)
//
// Nota: O livro descreve esses três smells conceitualmente, sem exemplos de
// código completos como fez nas seções anteriores. Os trechos abaixo
// reproduzem o raciocínio do Aniche com classes ilustrativas fiéis ao texto.
// ══════════════════════════════════════════════════════════════════════════════

// ─── Seção 9.4: God Class ────────────────────────────────────────────────────

/**
 * Aniche, p.123:
 * "Uma god class é aquela classe que controla muitos outros objetos do sistema.
 *  Classes assim tendem a crescer mais do que deveriam e passam a 'fazer tudo'.
 *  Os problemas de classes como essas, altamente acopladas, também já são
 *  conhecidos. Uma dependência pode forçar mudanças na classe que a consome,
 *  e quando uma classe depende de outras 30, isso a faz frágil."
 *
 * "Gerenciar as dependências é a parte mais difícil em um sistema OO.
 *  A ideia é você usar de tudo o que aprendeu aqui, e encontrar maneiras de
 *  diminuir esse acoplamento, seja pensando em abstrações, ou mesmo dividindo
 *  as responsabilidades em classes com responsabilidades menores."
 *
 * O livro ilustra com um diagrama de classe altamente acoplada (classe A
 * dependendo de B, C, D, E que propagam mudanças). Abaixo, o exemplo
 * conceitual equivalente em código:
 */

import java.util.ArrayList;
import java.util.List;

/**
 * ❌ GodClass ilustrativa — representa o padrão descrito por Aniche.
 * Uma classe que "faz tudo" e depende de muitos outros objetos.
 */
class SistemaDeNotaFiscalGodClass {

    // ❌ depende de tudo
    private List<NotaFiscalGC> notas = new ArrayList<>();
    private List<String> clientes = new ArrayList<>();
    private List<String> pagamentos = new ArrayList<>();
    private double totalArrecadado = 0;

    // ❌ emite nota (responsabilidade 1)
    public void emitirNota(String cliente, double valor) { notas.add(new NotaFiscalGC(cliente, valor)); }

    // ❌ calcula imposto (responsabilidade 2)
    public double calcularImposto(double valor) { return valor * 0.15; }

    // ❌ registra pagamento (responsabilidade 3)
    public void registrarPagamento(String cliente, double valor) {
        pagamentos.add(cliente);
        totalArrecadado += valor;
    }

    // ❌ gera relatório (responsabilidade 4)
    public String gerarRelatorio() {
        return "Total arrecadado: " + totalArrecadado +
                " | Notas: " + notas.size() +
                " | Clientes: " + clientes.size();
    }

    // ❌ envia para a Receita Federal (responsabilidade 5)
    public void enviarParaReceitaFederal() { System.out.println("Enviando " + notas.size() + " notas..."); }
}

class NotaFiscalGC {
    final String cliente;
    final double valor;

    public NotaFiscalGC(String cliente, double valor) {
        this.cliente = cliente;
        this.valor = valor;
    }
}

// ─── Seção 9.5: Divergent Changes ────────────────────────────────────────────

/**
 * Aniche, p.123:
 * "Divergent changes é o nome do mau cheiro para quando a classe não é coesa,
 *  e sofre alterações constantes, devido às suas diversas responsabilidades.
 *  Classes não coesas possuem baixo reúso, apresentam mais bugs e são mais
 *  complexas do que deveriam."
 *
 * "Uma classe que tem muitas responsabilidades tem muitas razões para mudar.
 *  Ela também provavelmente possui muitas dependências, e reutilizá-la não é
 *  fácil. Divida as responsabilidades o máximo que puder."
 *
 * Aniche, p.124:
 * "Use tudo o que aprendeu aqui para criar classes coesas."
 *
 * O livro não dá código concreto aqui — descreve o smell e remete aos
 * princípios já aprendidos (SRP). A classe abaixo representa o padrão:
 */

/**
 * ❌ ProcessadorDeNotaFiscalDivergente — muda por muitas razões:
 *   1. Lei de imposto muda         → calcularImposto() muda
 *   2. Formato de relatório muda   → gerarRelatorio() muda
 *   3. Canal de envio muda         → enviar() muda
 *   4. Regra de cliente muda       → validarCliente() muda
 */
class ProcessadorDeNotaFiscalDivergente {

    // razão 1 para mudar: regra tributária
    public double calcularImposto(double valor) { return  valor * 0.15; }

    // razão 2 para mudar: formato de saída
    public String gerarRelatorio(NotaFiscalGC nf) { return "NF | Cliente: " + nf.cliente + " | Valor: R$" + nf.valor; }

    // razão 3 para mudar: canal de comunicação
    public void enviar(NotaFiscalGC nf) { System.out.println("Enviando NF de " + nf.cliente + " por e-mail..."); }

    // razão 4 para mudar: regra de negócio de cliente
    public boolean validarClientes(String cliente) { return cliente != null && !cliente.isBlank(); }
}

// ─── Seção 9.6: Shotgun Surgery ──────────────────────────────────────────────

/**
 * Aniche, p.124:
 * "Sabe quando seu usuário pede uma mudança no sistema e, para que isso
 *  aconteça, você precisa modificar 20 arquivos de uma só vez? Esse mau cheiro
 *  é conhecido por shotgun surgery, e é comum em códigos procedurais."
 *
 * "Isso é geralmente típico de sistemas cujas abstrações foram mal boladas.
 *  Nesses casos, como a abstração não é suficiente, desenvolvedores acabam
 *  implementando pequenos trechos de código relacionados em diferentes lugares.
 *  Isso provoca a alteração em cascata."
 *
 * "Lembre-se que além de você precisar alterar em diversos pontos,
 *  provavelmente você precisará encontrar esses pontos. Ou seja, você apelará
 *  para algum tipo de grep ou CTRL+F, e com certeza deixará algum deles passar."
 *
 * O livro não dá código — descreve o cenário e remete ao princípio de
 * centralização de conceitos. As classes abaixo representam o padrão:
 */

/**
 * ❌ Alíquota de imposto hardcoded em múltiplos lugares.
 * Mudar 0.15 para 0.12 = cirurgia de espingarda em todos esses lugares.
 */
class EmissorDeNotaShotgun {
    public double calcularImposto(double valor) {
        return valor * 0.15; // ❌ hardcoded aqui
    }
}

class ValidadorDeNotaShotgun {
    public boolean impostoCorreto(double valor, double imposto) {
        return imposto == valor * 0.15; // ❌ hardcoded aqui também
    }
}

class RelatorioDeNotaShotgun {
    public String gerarLinha(double valor) {
        double imposto = valor * 0.15; // ❌ e aqui
        return "Valor: " + valor + " | Imposto: " + imposto;
    }
}

/**
 * ✅ Solução: alíquota centralizada em uma classe.
 * Mudar a alíquota? Apenas aqui.
 */
class AliquotaDeImposto {
    private static final double PADRAO = 0.15; // ✅ um único lugar

    public double calcular(double valorBase)   { return valorBase * PADRAO; }
    public double calcularLiquido(double base) { return base - calcular(base); }
    public double getAliquota()                { return PADRAO; }
}