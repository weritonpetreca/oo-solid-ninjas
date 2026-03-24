package capitulo10_metricas.v4_acoplamento;

import java.util.ArrayList;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * MÉTRICAS — Acoplamento Aferente (CA) e Eferente (CE)
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 10, seção 10.4:
 *
 * CE (Coupling Efferent) — acoplamento de SAÍDA.
 * "Quando uma classe depende de diversas outras classes, dizemos que esse é
 *  o acoplamento eferente. Quanto maior, mais ela depende de outras classes
 *  e, por consequência, mais frágil ela é."
 *
 * CA (Coupling Afferent) — acoplamento de ENTRADA.
 * "Ele mede quantas classes dependem da classe principal. Se ela é importante
 *  para o resto do sistema, provavelmente você não a alterará com frequência;
 *  ela é estável."
 *
 * Analogia do livro:
 *   List<T> do Java:
 *     CA = muito alto (todo mundo usa List)
 *     CE = zero      (List não depende de ninguém)
 *   → Estável por design — a interface List raramente muda.
 */

// ─── Classes estáveis com CA alto (todos dependem delas) ─────────────────────

/**
 * ✅ ContratoDeCaca — CA alto, CE baixo.
 * Classe de domínio estável: todos os serviços dependem dela.
 * Como quase não tem dependências externas, raramente quebra.
 * Análoga à interface List<T> do exemplo do livro.
 */
class ContratoDeCaca {
    private final String monstro;
    private final double valorBase;
    private final String regiao;

    ContratoDeCaca(String monstro, double valorBase, String regiao) {
        this.monstro  = monstro;
        this.valorBase = valorBase;
        this.regiao   = regiao;
    }

    // CE = 0 — não depende de nenhuma outra classe do projeto
    String getMonstro()    { return monstro; }
    double getValorBase()  { return valorBase; }
    String getRegiao()     { return regiao; }
}

/**
 * ✅ TaxaDaGuilda — CA alto (todos usam), CE = 0 (não depende de ninguém).
 * Conceito centralizado (lembram do Shotgun Surgery do Cap. 9?).
 * Alta estabilidade por design.
 */
class TaxaDaGuilda {
    private static final double PADRAO = 0.15;

    double calcular(double bruta)        { return bruta * PADRAO; }
    double calcularLiquida(double bruta) { return bruta - calcular(bruta); }
    // CE = 0 → não depende de ContratoDeCaca, nem de nada mais
}

// ─── PROBLEMA: Classe com CE alto → frágil ───────────────────────────────────

// Simulando "classes de infraestrutura" que podem mudar
class BancoDeDadosPostgres { public void salvar(Object o) { /* ... */ } }
class ServicoDeEmailSMTP   { public void enviar(String dest, String msg) { /* ... */ } }
class ApiDeCorreiosSedex   { public void despachar(String addr, Object obj) { /* ... */ } }
class SistemaERP           { public void integrar(Object obj) { /* ... */ } }
class LogDeAuditoriaDisco  { public void registrar(String msg) { /* ... */ } }

/**
 * ❌ GerenciadorCentralComAltoCE — CE = 5.
 * Depende de 5 classes de infraestrutura diferentes.
 * Qualquer uma delas mudar (ex: trocar Postgres por Mongo) força mudança aqui.
 *
 * Aniche, p.132:
 * "Se esse número fosse 20, precisaríamos nos preocupar com ela."
 */
class GerenciadorCentralComAltoCE {

    // CE = 5 — cinco dependências de saída (todas instanciadas diretamente)
    private final BancoDeDadosPostgres banco  = new BancoDeDadosPostgres();
    private final ServicoDeEmailSMTP   email  = new ServicoDeEmailSMTP();
    private final ApiDeCorreiosSedex   correios = new ApiDeCorreiosSedex();
    private final SistemaERP           erp    = new SistemaERP();
    private final LogDeAuditoriaDisco  log    = new LogDeAuditoriaDisco();

    public void processarContrato(ContratoDeCaca contrato, TaxaDaGuilda taxa) {
        double liquida = taxa.calcularLiquida(contrato.getValorBase());
        banco.salvar(contrato);
        email.enviar("guilda@continente.com", "Contrato: " + contrato.getMonstro());
        correios.despachar(contrato.getRegiao(), contrato);
        erp.integrar(contrato);
        log.registrar("Contrato processado: " + contrato.getMonstro());
    }
}

// ─── SOLUÇÃO: DIP reduz CE, estabiliza o módulo ──────────────────────────────

/**
 * Interfaces estáveis que definem as portas de saída (DIP do Cap. 3).
 * O GerenciadorLimpo depende dessas interfaces, não das implementações.
 */
interface Repositorio        { void salvar(ContratoDeCaca c); }
interface Notificador        { void notificar(String destinatario, String mensagem); }
interface Despachador        { void despachar(String destino, ContratoDeCaca c); }
interface IntegracaoExterna  { void integrar(ContratoDeCaca c); }
interface Log                { void registrar(String mensagem); }

/**
 * ✅ GerenciadorLimpo — CE ainda = 5, mas depende de INTERFACES estáveis.
 *
 * A diferença crucial (Aniche, Cap. 3 e Cap. 10):
 * Antes: CE = 5 dependências INSTÁVEIS (classes concretas que mudam)
 * Agora: CE = 5 dependências ESTÁVEIS  (interfaces que raramente mudam)
 *
 * O número CE é o mesmo, mas a ESTABILIDADE das dependências mudou.
 * É por isso que DIP e métricas de acoplamento caminham juntos.
 */
class GerenciadorLimpo {

    private final Repositorio       repositorio;
    private final Notificador       notificador;
    private final Despachador       despachador;
    private final IntegracaoExterna integracao;
    private final Log               auditoria;

    GerenciadorLimpo(Repositorio repositorio, Notificador notificador,
                     Despachador despachador, IntegracaoExterna integracao, Log auditoria) {
        this.repositorio = repositorio;
        this.notificador = notificador;
        this.despachador = despachador;
        this.integracao  = integracao;
        this.auditoria   = auditoria;
    }

    public void processarContrato(ContratoDeCaca contrato, TaxaDaGuilda taxa) {
        double liquida = taxa.calcularLiquida(contrato.getValorBase());
        repositorio.salvar(contrato);
        notificador.notificar("guilda@continente.com", "Contrato: " + contrato.getMonstro());
        despachador.despachar(contrato.getRegiao(), contrato);
        integracao.integrar(contrato);
        auditoria.registrar("Contrato processado: " + contrato.getMonstro() + " | R$" + liquida);
    }
}

// ─── Relatório de acoplamento ─────────────────────────────────────────────────

record RelatorioAcoplamento(String nomeClasse, int ca, int ce) {
    String avaliarCA() {
        if (ca == 0)   return "🟡 Não usada por ninguém — candidata a remoção";
        if (ca <= 3)   return "✅ Usada por poucos (menos estável)";
        if (ca <= 10)  return "✅ Estável — bem utilizada";
        return "✅ Muito estável — altere com cuidado (impacto alto)";
    }
    String avaliarCE() {
        if (ce == 0)   return "✅ Independente — altamente estável";
        if (ce <= 5)   return "✅ CE aceitável";
        if (ce <= 10)  return "🟠 CE alto — verifique se as dependências são estáveis";
        return "🔴 CE muito alto — fragilidade elevada";
    }
}