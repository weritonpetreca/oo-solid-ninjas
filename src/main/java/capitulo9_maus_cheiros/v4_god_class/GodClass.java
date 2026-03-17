package capitulo9_maus_cheiros.v4_god_class;

import java.util.ArrayList;
import java.util.List;

/**
 * ════════════════════════════════════════════════════════════════
 * MAUS CHEIROS — God Class
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 9, seção 9.4:
 * "Uma god class é aquela que controla muitos outros objetos do sistema.
 *  Classes assim tendem a crescer mais do que deveriam e passam a
 *  'fazer tudo'."
 *
 * Sintomas:
 *   - Centenas ou milhares de linhas
 *   - Depende de 10, 15, 20 outras classes
 *   - Qualquer mudança no sistema passa por ela
 *   - Impossível testar isoladamente
 *   - Todo o time tem medo de mexer nela
 */

// ─── PROBLEMA ────────────────────────────────────────────────────────────────

/**
 * ❌ GuildaDoContinetenteGodClass — faz absolutamente tudo.
 *    Gerencia caçadores, contratos, impostos, notificações, pagamentos,
 *    relatórios, histórico... Uma classe que "sabe demais".
 */
class GuildaDoContienenteGodClass {

    // ❌ Dependências de tudo no sistema
    private List<String> cacadores     = new ArrayList<>();
    private List<String> contratos     = new ArrayList<>();
    private List<String> pagamentos    = new ArrayList<>();
    private List<String> notificacoes  = new ArrayList<>();
    private double saldoDoTesouro      = 100_000.0;
    private int totalMissoesConcluidass = 0;

    // ❌ Registrar caçadores (responsabilidade de um CadastroService)
    public void registrarCacador(String nome) { cacadores.add(nome); }

    // ❌ Criar contratos (responsabilidade de um ContratoService)
    public void criarContrato(String monstro, double valor) {
        contratos.add(monstro + ":" + valor);
    }

    // ❌ Calcular imposto (responsabilidade de um CalculadorDeImposto)
    public double calcularImposto(double valor) { return valor * 0.15; }

    // ❌ Processar pagamento (responsabilidade de um PagamentoService)
    public void processarPagamento(String cacador, double valor) {
        saldoDoTesouro -= valor;
        pagamentos.add(cacador + " recebeu R$" + valor);
    }

    // ❌ Enviar notificações (responsabilidade de um NotificacaoService)
    public void notificarCacador(String cacador, String mensagem) {
        notificacoes.add("[" + cacador + "] " + mensagem);
    }

    // ❌ Gerar relatórios (responsabilidade de um RelatorioService)
    public String gerarRelatorioMensal() {
        return "Saldo: " + saldoDoTesouro + " | Missões: " + totalMissoesConcluidass;
    }

    // ❌ Concluir missão — mistura tudo acima numa única operação
    public void concluirMissao(String cacador, String monstro, double recompensa) {
        double imposto  = calcularImposto(recompensa);
        double liquido  = recompensa - imposto;
        processarPagamento(cacador, liquido);
        notificarCacador(cacador, "Missão concluída: " + monstro);
        totalMissoesConcluidass++;
        // E mais 20 linhas de lógica misturada...
    }

    // ... mais 500 linhas aqui na God Class real de produção
}

// ─── SOLUÇÃO: Distribuir responsabilidades ───────────────────────────────────

/** ✅ Cada classe tem UMA responsabilidade clara. */

class RegistroDeCacadores {
    private final List<String> cacadores = new ArrayList<>();
    public void registrar(String nome)  { cacadores.add(nome); }
    public boolean existe(String nome)  { return cacadores.contains(nome); }
    public int total()                  { return cacadores.size(); }
}

class GestorDeContratos {
    private final List<String> contratos = new ArrayList<>();
    public void criar(String monstro, double valor) {
        contratos.add(monstro + ":" + valor);
        System.out.println("    Contrato criado: " + monstro + " R$" + valor);
    }
    public int total() { return contratos.size(); }
}

class CalculadorDeImpostoGuilda {
    public double calcular(double valor) { return valor * 0.15; }
}

class TesouroDaGuilda {
    private double saldo;
    TesouroDaGuilda(double saldoInicial) { this.saldo = saldoInicial; }
    public void pagar(String cacador, double valor) {
        saldo -= valor;
        System.out.printf("    Pagamento: %s recebeu R$%.2f | Saldo: R$%.2f%n", cacador, valor, saldo);
    }
    public double getSaldo() { return saldo; }
}

class ServicoDeNotificacao {
    public void notificar(String destinatario, String mensagem) {
        System.out.println("    [NOTIF → " + destinatario + "] " + mensagem);
    }
}

/**
 * ✅ GuildaCoordenadora — orquestra os serviços, mas não implementa nada.
 *    Responsabilidade única: coordenar o fluxo de conclusão de missão.
 *    Pequena, testável, fácil de manter.
 */
class GuildaCoordenadora {
    private final RegistroDeCacadores registro;
    private final GestorDeContratos   contratos;
    private final CalculadorDeImpostoGuilda calculador;
    private final TesouroDaGuilda     tesouro;
    private final ServicoDeNotificacao notificacao;

    GuildaCoordenadora(RegistroDeCacadores registro, GestorDeContratos contratos,
                       CalculadorDeImpostoGuilda calculador, TesouroDaGuilda tesouro,
                       ServicoDeNotificacao notificacao) {
        this.registro    = registro;
        this.contratos   = contratos;
        this.calculador  = calculador;
        this.tesouro     = tesouro;
        this.notificacao = notificacao;
    }

    public void concluirMissao(String cacador, String monstro, double recompensa) {
        double imposto = calculador.calcular(recompensa);
        double liquido = recompensa - imposto;
        tesouro.pagar(cacador, liquido);
        notificacao.notificar(cacador, "Missão concluída: " + monstro);
    }
}
