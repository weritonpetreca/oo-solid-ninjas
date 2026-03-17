package capitulo9_maus_cheiros.v7_mundo_real;

import java.util.ArrayList;
import java.util.List;

/**
 * ══════════════════════════════════════════════════════════════════════
 * CAP 9 — MUNDO REAL: O Sistema Legado da Guilda do Continente
 * ══════════════════════════════════════════════════════════════════════
 *
 * Triss Merigold recebe a missão de auditar o sistema legado da Guilda.
 * O código tem anos, ninguém sabe bem o que faz, e todo deploy é um terror.
 *
 * O sistema original (GuildaLegada) concentra TODOS os smells do Cap. 9.
 * A versão refatorada (GuildaRefatorada) aplica as correções.
 *
 * Smells presentes no legado:
 *   ❌ God Class      → GuildaLegada faz absolutamente tudo
 *   ❌ Feature Envy   → metodos que só usam dados externos
 *   ❌ Intimidade     → pergunta estado interno para decidir
 *   ❌ Divergent Chg  → muitas razões para mudar
 *   ❌ Shotgun Surg   → taxa e regras espalhadas
 *   ❌ Refused Beq    → CacadorMago herda métodos que recusa
 */

// ═══════════════════════════════════════════════════════════════════════
// VERSÃO REFATORADA — cada coisa no seu lugar
// ═══════════════════════════════════════════════════════════════════════

// ─── Entidade: Missao (sem intimidade inapropriada) ──────────────────

class Missao {
    private final String monstro;
    private final double recompensa;
    private final TipoDeMonstro tipo;
    private StatusDaMissao status;

    Missao(String monstro, double recompensa, TipoDeMonstro tipo) {
        this.monstro    = monstro;
        this.recompensa = recompensa;
        this.tipo       = tipo;
        this.status     = StatusDaMissao.DISPONIVEL;
    }

    // ✅ Tell, don't ask — a missão conhece suas próprias regras
    public void aceitar()   {
        if (status != StatusDaMissao.DISPONIVEL)
            throw new IllegalStateException("Missão não disponível");
        status = StatusDaMissao.EM_ANDAMENTO;
    }

    public void concluir()  {
        if (status != StatusDaMissao.EM_ANDAMENTO)
            throw new IllegalStateException("Missão não está em andamento");
        status = StatusDaMissao.CONCLUIDA;
    }

    // ✅ Feature Envy resolvido: regra de classificação vive aqui
    public String descricaoCompleta() {
        return String.format("[%s] %s — R$%.2f (%s)",
                status, monstro, recompensa, tipo.getDescricao());
    }

    public double getRecompensa() { return recompensa; }
    public String getMonstro()    { return monstro; }
    public TipoDeMonstro getTipo(){ return tipo; }
    public StatusDaMissao getStatus() { return status; }
    public boolean isConcluida()  { return status == StatusDaMissao.CONCLUIDA; }
}

enum StatusDaMissao { DISPONIVEL, EM_ANDAMENTO, CONCLUIDA, CANCELADA }

enum TipoDeMonstro {
    HIBRIDO("Híbrido — resistente a espadas comuns"),
    VAMPIRO("Vampiro — vulnerável a prata"),
    AMALDICADO("Amaldiçoado — requer feitiço de cura"),
    ESPECTRE("Espectro — só pode ser ferido com espada de prata encantada"),
    DESCONHECIDO("Tipo não catalogado");

    private final String descricao;
    TipoDeMonstro(String descricao) { this.descricao = descricao; }
    public String getDescricao()    { return descricao; }
}

// ─── Interfaces para os caçadores (Refused Bequest resolvido) ────────

interface Cacador {
    String getNome();
    String getEscola();
    double calcularBonus(double recompensa);
}

interface Feiticeiro {
    double multiplicadorMagico();
    String lançarFeitico(String alvo);
}

class CacadorDeGuerra implements Cacador {
    private final String nome;
    private final String escola;
    private final int nivel;

    CacadorDeGuerra(String nome, String escola, int nivel) {
        this.nome   = nome;
        this.escola = escola;
        this.nivel  = nivel;
    }

    @Override public String getNome()    { return nome; }
    @Override public String getEscola()  { return escola; }
    @Override public double calcularBonus(double recompensa) {
        return recompensa * (nivel / 100.0);
    }
}

/**
 * ✅ CacadorMago implementa AMBAS as interfaces — sem recusar nada.
 *    Não herda de CacadorDeGuerra — não precisa, nem quer.
 */
class CacadorMago implements Cacador, Feiticeiro {
    private final String nome;
    private final int poderMagico;

    CacadorMago(String nome, int poderMagico) {
        this.nome        = nome;
        this.poderMagico = poderMagico;
    }

    @Override public String getNome()    { return nome; }
    @Override public String getEscola()  { return "Aretuza"; }
    @Override public double calcularBonus(double recompensa) {
        return recompensa * multiplicadorMagico();
    }
    @Override public double multiplicadorMagico() { return poderMagico / 50.0; }
    @Override public String lançarFeitico(String alvo) {
        return nome + " lança Aard em " + alvo + "!";
    }
}

// ─── Taxa centralizada (Shotgun Surgery resolvido) ───────────────────

class TaxaDaGuilda {
    private static final double PADRAO = 0.15;

    public double calcular(double bruta)        { return bruta * PADRAO; }
    public double calcularLiquida(double bruta) { return bruta - calcular(bruta); }
    public double getPercentual()               { return PADRAO; }
}

// ─── Serviços coesos (Divergent Changes resolvido) ───────────────────

class ServicoDeRecompensa {
    private final TaxaDaGuilda taxa;
    ServicoDeRecompensa(TaxaDaGuilda taxa) { this.taxa = taxa; }

    public double calcularPagamento(Cacador c, Missao m) {
        double liquida = taxa.calcularLiquida(m.getRecompensa());
        double bonus   = c.calcularBonus(liquida);
        return liquida + bonus;
    }
}

class RelatorioDeGuilda {
    private final TaxaDaGuilda taxa;
    RelatorioDeGuilda(TaxaDaGuilda taxa) { this.taxa = taxa; }

    public String gerarLinhaDeRelatorio(Cacador c, Missao m) {
        double bruta   = m.getRecompensa();
        double liquida = taxa.calcularLiquida(bruta);
        double bonus   = c.calcularBonus(liquida);
        return String.format("%-20s | %-12s | Bruta: R$%8.2f | Pago: R$%8.2f",
                c.getNome(), m.getMonstro(), bruta, liquida + bonus);
    }
}

// ─── GuildaRefatorada: coordena sem ser God Class ────────────────────

/**
 * ✅ GuildaRefatorada — coordenadora, não realizadora.
 *    Cada serviço tem sua responsabilidade.
 *    A guilda apenas orquestra o fluxo.
 */
public class GuildaRefatorada {

    private final TaxaDaGuilda       taxa        = new TaxaDaGuilda();
    private final ServicoDeRecompensa recompensas = new ServicoDeRecompensa(taxa);
    private final RelatorioDeGuilda  relatorio   = new RelatorioDeGuilda(taxa);
    private final List<String>       historico   = new ArrayList<>();

    public void concluirMissao(Cacador cacador, Missao missao) {
        missao.concluir(); // ✅ Tell, don't ask

        double pagamento = recompensas.calcularPagamento(cacador, missao);
        String linha     = relatorio.gerarLinhaDeRelatorio(cacador, missao);

        historico.add(linha);
        System.out.println("  " + linha);
        System.out.printf("  → Pagamento total: R$%.2f (taxa de %.0f%% deduzida)%n",
                pagamento, taxa.getPercentual() * 100);
    }

    public void imprimirHistorico() {
        System.out.println("─".repeat(75));
        System.out.println("  HISTÓRICO DA GUILDA");
        System.out.println("─".repeat(75));
        historico.forEach(l -> System.out.println("  " + l));
        System.out.println("─".repeat(75));
        System.out.println("  Total de missões concluídas: " + historico.size());
    }
}
