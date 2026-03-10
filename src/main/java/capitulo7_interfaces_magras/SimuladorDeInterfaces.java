package br.com.oo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================
 * CAPÍTULO 7 — Interfaces Magras e o tal do ISP
 * Simulador didático — Tema: O Serviço de Inteligência do Continente
 * ============================================================
 *
 * Yennefer de Vengerberg recebeu uma missão da Conclave dos Magos:
 * modernizar o sistema de comunicação e tributação do Continente.
 *
 * O problema: as interfaces do sistema atual são gordas, cheias
 * de responsabilidades que não pertencem a todos os implementadores.
 * Cada mago que tenta implementar uma nova classe acaba criando gambiarras.
 *
 * Ao longo deste simulador, vamos ver como interfaces magras
 * resolvem isso de forma elegante — e como a Conclave finalmente
 * para de receber petições de exceção de NaoGeraNotaException.
 *
 * Execute com: gradle run
 */
public class SimuladorDeInterfaces {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();
        System.out.println("=".repeat(62));
        System.out.println("  CAP 7 — INTERFACES MAGRAS E O ISP");
        System.out.println("  O Sistema de Comunicação do Continente");
        System.out.println("=".repeat(62));
        System.out.println();

        simularV1_InterfaceGorda();
        simularV2_InterfacesCoesas();
        simularV3_InterfaceTributavel();
        simularV4_RepositorioEFabrica();
        simularV5_ServicoDeInteligencia();

        imprimirResumo();
    }

    // ============================================================
    // V1 — Interface gorda: Imposto que força geraNota() em todos
    // ============================================================
    static void simularV1_InterfaceGorda() {
        System.out.println("[ V1 ] Interface Gorda (Fat Interface)");
        System.out.println();
        System.out.println("Yennefer precisa modelar os impostos do Continente.");
        System.out.println("O arquiteto criou uma interface única para tudo:");
        System.out.println();
        System.out.println("    interface Imposto {");
        System.out.println("        NotaFiscal geraNota();          // Responsabilidade 1");
        System.out.println("        double imposto(double valor);   // Responsabilidade 2");
        System.out.println("    }");
        System.out.println();
        System.out.println("ISS de Novigrad: calcula imposto E gera nota. Sem problema.");

        ImpostoGordo iss = new ImpostoGordoISS();
        System.out.printf("    ISS sobre R$1000: R$%.2f%n", iss.imposto(1000.0));
        System.out.println("    Nota gerada: " + iss.geraNota());
        System.out.println();

        System.out.println("IXMX de Cintra: calcula imposto mas NÃO emite nota.");
        System.out.println("O que o desenvolvedor faz com geraNota()?");
        System.out.println();
        System.out.println("    ❌ Opção 1: throw new NaoGeraNotaException()  → quebra LSP");
        System.out.println("    ❌ Opção 2: return null                       → NullPointerException esperando");
        System.out.println();

        ImpostoGordo ixmx = new ImpostoGordoIXMX();
        System.out.printf("    IXMX sobre R$1000: R$%.2f%n", ixmx.imposto(1000.0));
        System.out.println("    Tentando gerar nota do IXMX...");
        try {
            ixmx.geraNota();
            System.out.println("    ❌ Retornou null — NullPointerException à espreita!");
        } catch (RuntimeException e) {
            System.out.println("    ❌ Exceção lançada: " + e.getMessage());
            System.out.println("    O processador que chamou geraNota() não esperava isso.");
        }
        System.out.println();
        System.out.println("    Interface gorda = baixo reúso + gambiarras inevitáveis.");
        System.out.println();
    }

    // ============================================================
    // V2 — Interfaces coesas: cada uma com uma responsabilidade
    // ============================================================
    static void simularV2_InterfacesCoesas() {
        System.out.println("[ V2 ] Interfaces Coesas — ISP Aplicado");
        System.out.println();
        System.out.println("Yennefer reescreve. Duas interfaces, uma responsabilidade cada:");
        System.out.println();
        System.out.println("    interface CalculadorDeImposto { double imposto(double valor); }");
        System.out.println("    interface GeradorDeNota       { NotaFiscal geraNota(); }");
        System.out.println();

        CalculadorDeImpostoMagro iss  = new ISSMagro();
        CalculadorDeImpostoMagro ixmx = new IXMXMagro();

        System.out.println("Processando impostos de todos os contratos da Guilda:");
        List<CalculadorDeImpostoMagro> calculadores = List.of(iss, ixmx);
        double totalImpostos = 0;
        for (CalculadorDeImpostoMagro c : calculadores) {
            double imposto = c.imposto(2000.0);
            totalImpostos += imposto;
            System.out.printf("    %-8s → R$%.2f%n", c.getClass().getSimpleName(), imposto);
        }
        System.out.printf("    Total de impostos: R$%.2f%n", totalImpostos);
        System.out.println();

        System.out.println("Gerando notas — apenas de quem realmente emite:");
        List<GeradorDeNotaMagro> geradores = List.of((GeradorDeNotaMagro) iss);
        for (GeradorDeNotaMagro g : geradores) {
            System.out.println("    ✅ Nota gerada: " + g.geraNota());
        }
        System.out.println();
        System.out.println("    IXMX nem está na lista de geradores — sem gambiarra possível.");
        System.out.println("    O tipo garante: quem está aqui, gera nota. Sem exceção.");
        System.out.println();
    }

    // ============================================================
    // V3 — Interface mínima: Tributavel
    // ============================================================
    static void simularV3_InterfaceTributavel() {
        System.out.println("[ V3 ] Interface Mínima — Tributavel");
        System.out.println();
        System.out.println("O CalculadorDeImposto precisa apenas dos itens para calcular.");
        System.out.println("Por que receber NotaFiscal inteira? Ela tem cliente, endereço,");
        System.out.println("forma de pagamento... coisas que o cálculo jamais vai usar.");
        System.out.println();
        System.out.println("    ❌ Acoplado demais:");
        System.out.println("    public double calcula(NotaFiscalCompleta nf) { ... }");
        System.out.println();
        System.out.println("    ✅ Interface mínima com semântica:");
        System.out.println("    public double calcula(Tributavel t) { ... }");
        System.out.println();

        // Demonstração com NotaFiscal do Continente
        NotaFiscalContinente nf = new NotaFiscalContinente("Geralt de Rívia", "Kaer Morhen");
        nf.adicionaItem(new ItemTributavel("Espada de Prata Aerondight", 800.0));
        nf.adicionaItem(new ItemTributavel("Poção Swallow x5", 200.0));
        nf.adicionaItem(new ItemTributavel("Armadura do Gato Aprimorada", 1500.0));

        CalculadorDeImpostoTributavel calc = new CalculadorDeImpostoTributavel();
        double imposto = calc.calcula(nf);

        System.out.println("Nota Fiscal de " + nf.getCliente() + ":");
        for (ItemTributavel item : nf.itensASeremTributados()) {
            String aliquota = item.getValor() > 1000 ? "2%" : "1%";
            System.out.printf("    %-40s R$%7.2f (%s)%n",
                    item.getDescricao(), item.getValor(), aliquota);
        }
        System.out.printf("    Imposto total: R$%.2f%n", imposto);
        System.out.println();

        System.out.println("Testando com outro Tributavel — um simples lambda:");
        Tributavel contratoSimples = () -> List.of(
                new ItemTributavel("Contrato: Wyvern de Gelo (Nível 5)", 5000.0)
        );
        System.out.printf("    Imposto do contrato: R$%.2f (2%% sobre R$5000)%n",
                calc.calcula(contratoSimples));
        System.out.println();
        System.out.println("    O calculador não sabe o que é. Só sabe que é Tributavel.");
        System.out.println("    Isso é interface mínima na prática.");
        System.out.println();
    }

    // ============================================================
    // V4 — Repositório DDD + Fábrica
    // ============================================================
    static void simularV4_RepositorioEFabrica() {
        System.out.println("[ V4 ] Repositório DDD e Fábrica");
        System.out.println();
        System.out.println("A Guilda precisa persistir contratos. O arquiteto propõe:");
        System.out.println();
        System.out.println("    interface RepositorioDeContratos {");
        System.out.println("        List<Contrato> todos();");
        System.out.println("        void salva(Contrato c);");
        System.out.println("        Contrato buscaPorCacador(String nome);");
        System.out.println("    }");
        System.out.println();
        System.out.println("Em produção: ContratoDao (usa banco de dados).");
        System.out.println("Nos testes: ContratoRepositorioEmMemoria (sem banco).");
        System.out.println();

        RepositorioDeContratos repo = new ContratoRepositorioEmMemoria();
        repo.salva(new Contrato("Geralt", "Kikimora do Pântano", 350.0));
        repo.salva(new Contrato("Lambert", "Neófita do Ofir", 800.0));
        repo.salva(new Contrato("Eskel", "Dragão Barrow", 2500.0));

        System.out.println("Contratos registrados no repositório:");
        for (Contrato c : repo.todos()) {
            System.out.printf("    %-10s → %-28s R$%.2f%n",
                    c.getCacador(), c.getMonstro(), c.getRecompensa());
        }
        System.out.println();

        Contrato encontrado = repo.buscaPorCacador("Lambert");
        System.out.println("Buscando contrato de Lambert: " +
                (encontrado != null ? encontrado.getMonstro() : "não encontrado"));
        System.out.println();

        System.out.println("Fábrica — monta as dependências em um só lugar:");
        System.out.println();
        System.out.println("    class FabricaDoSistemaDeContratos {");
        System.out.println("        public ServicoDeContratos constroi() {");
        System.out.println("            return new ServicoDeContratos(");
        System.out.println("                new ContratoDao(),");
        System.out.println("                new CalculadorDeImpostoTributavel()");
        System.out.println("            );");
        System.out.println("        }");
        System.out.println("    }");
        System.out.println();
        System.out.println("    A fábrica é acoplada — mas estável, sem regras de negócio.");
        System.out.println("    Trocar o banco? Muda aqui. Só aqui.");
        System.out.println();
    }

    // ============================================================
    // V5 — Serviço de Inteligência com interfaces magras
    // ============================================================
    static void simularV5_ServicoDeInteligencia() {
        System.out.println("[ V5 ] Serviço de Inteligência do Continente");
        System.out.println();
        System.out.println("A Conclave precisa alertar caçadores sobre ameaças.");
        System.out.println("Interfaces magras tornam o sistema completamente plugável:");
        System.out.println();
        System.out.println("    interface Destinatario   { String identificacao(); String nomeCompleto(); }");
        System.out.println("    interface MeioDeComunicacao { void envia(Destinatario d, String msg); }");
        System.out.println();

        List<DestinatarioSim> cacadores = List.of(
                new CacadorSim("Geralt de Rívia",     "Escola do Lobo",  "Kaer Morhen"),
                new CacadorSim("Yennefer de Vengerberg", "Aretuza",       "Vengerberg"),
                new CacadorSim("Ciri",                "Escola do Lobo",  "Toussaint")
        );

        List<MeioDeComunicacaoSim> meiosBasicos = List.of(
                new CorvoMensageiroSim()
        );

        List<MeioDeComunicacaoSim> meiosDeEmergencia = List.of(
                new PergaminhoMagicoSim(),
                new CristalDeComunicacaoSim("CONCLAVE-EMERGÊNCIA")
        );

        System.out.println("─── Alerta rotineiro (corvo mensageiro) ───────────────────");
        ServicoDeInteligenciaSim servicoBasico = new ServicoDeInteligenciaSim(meiosBasicos);
        servicoBasico.alertarTodos(cacadores, "Reunião mensal da Guilda em Novigrad.");
        System.out.println();

        System.out.println("─── Alerta de emergência (pergaminho + cristal) ────────────");
        ServicoDeInteligenciaSim servicoEmergencia = new ServicoDeInteligenciaSim(meiosDeEmergencia);
        servicoEmergencia.alertarTodos(cacadores,
                "URGENTE: Caçada Selvagem avistada ao norte de Oxenfurt!");
        System.out.println();

        System.out.println("Novo canal (Sonho de Bruxa) adicionado no futuro?");
        System.out.println("    → Cria SonhoDeBruxaSim implements MeioDeComunicacaoSim");
        System.out.println("    → Passa para ServicoDeInteligenciaSim");
        System.out.println("    → Zero mudança no ServicoDeInteligenciaSim. Zero.");
        System.out.println();
    }

    // ============================================================
    // Resumo final
    // ============================================================
    static void imprimirResumo() {
        System.out.println("=".repeat(62));
        System.out.println("  RESUMO DO CAPÍTULO 7");
        System.out.println("=".repeat(62));
        System.out.println("  Interface gorda    → força gambiarras (throw / return null)");
        System.out.println("  ISP                → uma interface, uma responsabilidade");
        System.out.println("  Interface mínima   → acopla-se ao mínimo necessário");
        System.out.println("  Semântica          → Tributavel é mais claro que List<Item>");
        System.out.println("  Repositório DDD    → abstrai infraestrutura do domínio");
        System.out.println("  Fábrica            → monta dependências em um lugar só");
        System.out.println("  Interface magra    → testa com double/lambda, sem esforço");
        System.out.println("=".repeat(62));
    }

    // ============================================================
    // Classes internas — Simulador auto-contido
    // ============================================================

    // ── V1: Interface gorda ──────────────────────────────────────

    interface ImpostoGordo {
        String geraNota();
        double imposto(double valor);
    }

    static class ImpostoGordoISS implements ImpostoGordo {
        @Override public double imposto(double valor) { return valor * 0.10; }
        @Override public String geraNota() { return "NF-ISS [10% sobre serviços]"; }
    }

    static class ImpostoGordoIXMX implements ImpostoGordo {
        @Override public double imposto(double valor) { return valor * 0.20; }
        @Override public String geraNota() {
            // ❌ Forçado pela interface gorda a implementar o que não faz
            throw new RuntimeException("Este imposto não emite nota fiscal");
        }
    }

    // ── V2: Interfaces coesas ────────────────────────────────────

    interface CalculadorDeImpostoMagro {
        double imposto(double valor);
    }

    interface GeradorDeNotaMagro {
        String geraNota();
    }

    static class ISSMagro implements CalculadorDeImpostoMagro, GeradorDeNotaMagro {
        @Override public double imposto(double valor) { return valor * 0.10; }
        @Override public String geraNota() { return "NF-ISS [10% sobre serviços]"; }
    }

    static class IXMXMagro implements CalculadorDeImpostoMagro {
        // ✅ Só implementa o que faz sentido — sem geraNota(), sem gambiarra
        @Override public double imposto(double valor) { return valor * 0.20; }
    }

    // ── V3: Tributavel ───────────────────────────────────────────

    interface Tributavel {
        List<ItemTributavel> itensASeremTributados();
    }

    static class ItemTributavel {
        private final String descricao;
        private final double valor;
        ItemTributavel(String descricao, double valor) {
            this.descricao = descricao;
            this.valor = valor;
        }
        String getDescricao() { return descricao; }
        double getValor() { return valor; }
    }

    static class NotaFiscalContinente implements Tributavel {
        private final String cliente;
        private final String destino;
        private final List<ItemTributavel> itens = new ArrayList<>();

        NotaFiscalContinente(String cliente, String destino) {
            this.cliente = cliente;
            this.destino = destino;
        }

        void adicionaItem(ItemTributavel item) { itens.add(item); }

        @Override
        public List<ItemTributavel> itensASeremTributados() {
            return Collections.unmodifiableList(itens);
        }

        String getCliente() { return cliente; }
    }

    static class CalculadorDeImpostoTributavel {
        // ✅ Recebe Tributavel — não precisa saber nada além dos itens
        double calcula(Tributavel t) {
            double total = 0;
            for (ItemTributavel item : t.itensASeremTributados()) {
                total += item.getValor() > 1000
                        ? item.getValor() * 0.02
                        : item.getValor() * 0.01;
            }
            return total;
        }
    }

    // ── V4: Repositório e Fábrica ────────────────────────────────

    static class Contrato {
        private final String cacador;
        private final String monstro;
        private final double recompensa;
        Contrato(String cacador, String monstro, double recompensa) {
            this.cacador = cacador;
            this.monstro = monstro;
            this.recompensa = recompensa;
        }
        String getCacador()    { return cacador; }
        String getMonstro()    { return monstro; }
        double getRecompensa() { return recompensa; }
    }

    interface RepositorioDeContratos {
        List<Contrato> todos();
        void salva(Contrato contrato);
        Contrato buscaPorCacador(String nome);
    }

    static class ContratoRepositorioEmMemoria implements RepositorioDeContratos {
        private final List<Contrato> dados = new ArrayList<>();

        @Override public List<Contrato> todos() { return Collections.unmodifiableList(dados); }
        @Override public void salva(Contrato c) { dados.add(c); }

        @Override
        public Contrato buscaPorCacador(String nome) {
            return dados.stream()
                    .filter(c -> c.getCacador().equals(nome))
                    .findFirst()
                    .orElse(null);
        }
    }

    // ── V5: Serviço de Inteligência ──────────────────────────────

    interface DestinatarioSim {
        String identificacao();
        String nomeCompleto();
    }

    interface MeioDeComunicacaoSim {
        void envia(DestinatarioSim dest, String mensagem);
    }

    static class CacadorSim implements DestinatarioSim {
        private final String nome;
        private final String escola;
        private final String localizacao;
        CacadorSim(String nome, String escola, String localizacao) {
            this.nome = nome;
            this.escola = escola;
            this.localizacao = localizacao;
        }
        @Override public String identificacao() { return localizacao; }
        @Override public String nomeCompleto()  { return nome + " (" + escola + ")"; }
    }

    static class CorvoMensageiroSim implements MeioDeComunicacaoSim {
        @Override
        public void envia(DestinatarioSim dest, String mensagem) {
            String msgCurta = mensagem.length() > 60
                    ? mensagem.substring(0, 57) + "..."
                    : mensagem;
            System.out.printf("    [CORVO] → %-30s | \"%s\"%n",
                    dest.nomeCompleto(), msgCurta);
        }
    }

    static class PergaminhoMagicoSim implements MeioDeComunicacaoSim {
        @Override
        public void envia(DestinatarioSim dest, String mensagem) {
            System.out.printf("    [PERGAMINHO ✦] → %-28s | %s%n",
                    dest.nomeCompleto(), mensagem);
        }
    }

    static class CristalDeComunicacaoSim implements MeioDeComunicacaoSim {
        private final String frequencia;
        CristalDeComunicacaoSim(String frequencia) { this.frequencia = frequencia; }
        @Override
        public void envia(DestinatarioSim dest, String mensagem) {
            System.out.printf("    [CRISTAL/%s] 🔮 → %s%n", frequencia, dest.identificacao());
            System.out.printf("      \"%s\"%n", mensagem);
        }
    }

    static class ServicoDeInteligenciaSim {
        private final List<MeioDeComunicacaoSim> meios;
        ServicoDeInteligenciaSim(List<MeioDeComunicacaoSim> meios) { this.meios = meios; }

        void comunicar(DestinatarioSim dest, String mensagem) {
            for (MeioDeComunicacaoSim meio : meios) {
                meio.envia(dest, mensagem);
            }
        }

        void alertarTodos(List<DestinatarioSim> destinatarios, String mensagem) {
            for (DestinatarioSim dest : destinatarios) {
                comunicar(dest, mensagem);
            }
        }
    }
}