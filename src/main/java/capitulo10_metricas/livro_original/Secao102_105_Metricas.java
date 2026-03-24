package capitulo10_metricas.livro_original;

import java.util.*;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 10, seção 10.2: Tamanho de Métodos
// ══════════════════════════════════════════════════════════════════════════════

/**
 * Aniche, p.129:
 * "Uma classe com 70 atributos provavelmente é mais difícil de ser mantida.
 *  Uma classe com 80 métodos provavelmente é difícil de ser mantida e faz
 *  coisa demais."
 *
 * Métricas de tamanho:
 *   - LOC  (Lines of Code)       — linhas de código por método/classe
 *   - NOM  (Number of Methods)   — quantidade de métodos na classe
 *   - NOA  (Number of Attributes)— quantidade de atributos
 *   - NOP  (Number of Parameters)— parâmetros por método
 *   - MLOC (Method LOC)          — linhas por método
 */

/**
 * Classe com boas métricas de tamanho — referência para comparação.
 */
class ClasseCoesa {
    private String nome;      // NOA = 1
    private double valor;     // NOA = 2

    public ClasseCoesa(String nome, double valor) {
        this.nome  = nome;
        this.valor = valor;
    }

    public String getNome()  { return nome;  }    // 1 linha
    public double getValor() { return valor; }    // 1 linha
    public double calcular() { return valor * 0.15; } // 1 linha
}
// NOA = 2, NOM = 3 → saudável

/**
 * ❌ Classe com péssimas métricas de tamanho.
 * Representa o antipadrão que as métricas detectam.
 */
class ClasseGigante {
    // NOA = 8 — muitos atributos = muitas responsabilidades
    private String nome;
    private String email;
    private String cpf;
    private double salario;
    private double imposto;
    private String departamento;
    private String cargo;
    private String gerente;

    // NOP = 8 — construtor monstruoso, fácil de passar args na ordem errada
    public ClasseGigante(String nome, String email, String cpf, double salario,
                         double imposto, String departamento, String cargo, String gerente) {
        this.nome         = nome;
        this.email        = email;
        this.cpf          = cpf;
        this.salario      = salario;
        this.imposto      = imposto;
        this.departamento = departamento;
        this.cargo        = cargo;
        this.gerente      = gerente;
    }

    /**
     * ❌ Método com muitas linhas e muitos desvios — alto LOC + alta CC.
     * NOP = 4 parâmetros — já é um sinal de problema.
     */
    public String processar(boolean urgente, boolean vip, double bonus, String tipo) {
        String resultado = "";
        if (tipo.equals("FOLHA")) {
            double total = salario + bonus;
            if (total > 5000) {
                imposto = total * 0.275;
                resultado = "IRPF 27.5%";
            } else if (total > 3000) {
                imposto = total * 0.15;
                resultado = "IRPF 15%";
            } else {
                imposto = 0;
                resultado = "IRPF ISENTO";
            }
            if (urgente) resultado += " URGENTE";
            if (vip) resultado += " VIP";
        } else if (tipo.equals("FERIAS")) {
            double adicional = salario / 3;
            resultado = "FÉRIAS: " + (salario + adicional);
        }
        return resultado;
    }
    // CC = 7, LOC ≈ 20 — candidata a refatoração
}

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 10, seção 10.3: Coesão e a LCOM
// ══════════════════════════════════════════════════════════════════════════════

/**
 * Aniche, p.130:
 * "Se a classe for coesa, esses comportamentos manipulam boa parte desses
 *  atributos [...] Se a classe não for coesa, ela provavelmente contém um
 *  conjunto de atributos que são manipulados apenas por alguns métodos,
 *  e outro conjunto [...] apenas por outros métodos."
 *
 * LCOM HS = 1 - (sum(MF) / M*F)
 *   onde MF = métodos que acessam o atributo F
 *        M  = total de métodos
 *        F  = total de atributos
 *
 * Valor 0 = máxima coesão (todos os métodos acessam todos os atributos)
 * Valor 1 = mínima coesão (cada método acessa apenas seus próprios atributos)
 */

/**
 * ✅ Classe coesa — LCOM próximo de 0.
 * Todos os métodos usam os mesmos atributos.
 */
class NotaFiscalCoesa {
    private double valor;    // F1 — usada por calcularImposto(), encerrar() e cancelar()
    private boolean encerrada; // F2 — usada por calcularImposto(), encerrar() e cancelar()

    public NotaFiscalCoesa(double valor) {
        this.valor = valor;
        this.encerrada = false;
    }

    // Método A: usa F1 e F2
    public void encerrar() {
        if (this.valor <= 0) {
            throw new IllegalStateException("Não é possível encerrar uma nota sem valor.");
        }
        this.encerrada = true;
    }

    // Método B: usa F1 e F2
    public double calcularImposto() {
        if (!this.encerrada) {
            return 0.0;
        }
        return this.valor * 0.15;
    }

    // Método C: usa F1 e F2
    public void cancelar() {
        this.valor = 0.0;
        this.encerrada = false;
    }

    // Todos os métodos compartilham atributos → LCOM = 0 → COESÃO PERFEITA
}

/**
 * ❌ Classe não coesa — LCOM próximo de 1.
 * Os métodos A, B não compartilham atributos com C, D.
 * "É como se a classe estivesse dividida em 2, dentro dela mesma." — Aniche
 */
class ClasseComDuasResponsabilidades {
    // Grupo 1: Cálculo Fiscal
    private double valorBase;
    private double aliquota;

    // Grupo 2: Notificação
    private String emailDestinatario;
    private String template;

    public ClasseComDuasResponsabilidades() {
        this.valorBase = 1000.0;
        this.aliquota = 0.15;
        this.emailDestinatario = "contato@guilda.com";
        this.template = "Olá, {email}! Sua fatura chegou.";
    }

    // Métodos do Grupo 1 — só usam valorBase e aliquota
    public double calcularImposto()  { return valorBase * aliquota; }
    public double calcularLiquido()  { return valorBase - calcularImposto(); }

    // Métodos do Grupo 2 — só usam emailDestinatario e template
    public String gerarMensagem()    { return template.replace("{email}", emailDestinatario); }
    public void  enviarNotificacao() { System.out.println("Enviando para: " + emailDestinatario); }

    // LCOM ≈ 1 → dois grupos independentes → DIVIDIR essa classe!
}

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 10, seção 10.4: Acoplamento Aferente e Eferente
// ══════════════════════════════════════════════════════════════════════════════

/**
 * Aniche, p.132-133:
 *
 * Acoplamento EFERENTE: quantas classes esta classe DEPENDE.
 * "Quanto maior o acoplamento eferente de uma classe, mais ela depende de
 *  outras classes e, por consequência, mais frágil ela é."
 *
 * Acoplamento AFERENTE: quantas classes DEPENDEM desta classe.
 * "Se ela é importante para o resto do sistema, provavelmente você não a
 *  alterará com frequência; ela é estável."
 *
 * Exemplo do livro: interface List<T> do Java
 *   - Acoplamento EFERENTE = 0 (não depende de ninguém)
 *   - Acoplamento AFERENTE = alto (muitas classes dependem dela)
 *   → Estável por design
 */

// Simula classes externas que GerenciadorDeNotaFiscal depende
class BancoDeDados { public void salvar(Object obj) {} }
class EnviadorDeEmail { public void enviar(String email, String corpo) {} }
class ServicoExterno { public void notificar(String mensagem) {} }

/**
 * ❌ GerenciadorDeNotaFiscal — alta CE (acoplamento eferente).
 * Depende de BancoDeDados, EnviadorDeEmail e ServicoExterno.
 * CE = 3 → frágil. Qualquer uma dessas 3 pode forçar mudanças aqui.
 */
class GerenciadorDeNotaFiscal {

    // Acoplamento Eferente = 3 (dependências de saída)
    private BancoDeDados banco          = new BancoDeDados();
    private EnviadorDeEmail email       = new EnviadorDeEmail();
    private ServicoExterno servicoSap   = new ServicoExterno();

    public void processar(NotaFiscalCoesa nf) {
        banco.salvar(nf);
        email.enviar("admin@empresa.com", "NF processada");
        servicoSap.notificar("NF emitida");
    }
}

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 10, seção 10.5: Má Nomenclatura
// ══════════════════════════════════════════════════════════════════════════════

/**
 * Aniche, p.133:
 * "Variáveis cujo nome contenha muitos caracteres são complicadas de serem
 *  lidas. O mesmo para variáveis de uma única letra."
 */
class ExemplosDeMaNomenclatura {

    // ❌ Exemplos de má nomenclatura
    public double c(double x, double y) { return x * y * 0.15; } // 'c', 'x', 'y'
    public double calcularTaxaDaGuildaDoContineenteParaOBruxoComBaseNoValorTotalDaRecompensa(double v) {
        return v * 0.15; // Nome ridiculamente longo
    }
    int q = 0; // Atributo sem significado

    // ✅ Exemplos de boa nomenclatura
    public double calcularTaxaDaGuilda(double recompensa) { return recompensa * 0.15; }
    int quantidadeDeCacadores = 0;
    boolean isAtivo = false;
    List<String> missoesConcluidasNoMes = new ArrayList<>();
}
