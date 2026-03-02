package capitulo5_encapsulamento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ============================================================
 * CAPÍTULO 5 — Encapsulamento e Propagação de Mudanças
 * Simulador didático — Tema: A Guilda dos Caçadores de Monstros
 * ============================================================
 *
 * Geralt acaba de aceitar uma nova missão: modelar o sistema de
 * pagamento de recompensas da guilda. A missão parece simples.
 * O perigo está escondido — como sempre.
 *
 * Cada seção demonstra um problema real de encapsulamento,
 * fielmente baseado nos exemplos de Maurício Aniche (Cap. 5).
 */
public class SimuladorDeEncapsulamento {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();
        System.out.println("=".repeat(60));
        System.out.println("  CAP 5 — ENCAPSULAMENTO E PROPAGAÇÃO DE MUDANÇAS");
        System.out.println("  A Guilda dos Caçadores de Monstros");
        System.out.println("=".repeat(60));
        System.out.println();

        demonstrarProblema1_GetterExpondoLista();
        demonstrarProblema2_RegraForaDoDono();
        demonstrarProblema3_IntimidadeInapropriada();
        demonstrarProblema4_LeiDeDemeter();
        demonstrarSolucao_FaturaEncapsulada();
        demonstrarAntipAdrao_ModeloAnemico();
    }

    // ============================================================
    // PROBLEMA 1: getter que expõe lista interna
    // ============================================================
    static void demonstrarProblema1_GetterExpondoLista() {
        System.out.println("[ PROBLEMA 1 ] Getter que expõe a lista interna");
        System.out.println();
        System.out.println("A guilda precisa registrar pagamentos de recompensas.");
        System.out.println("Alguém escreveu assim:");
        System.out.println();
        System.out.println("    fatura.getPagamentos().add(pagamento);");
        System.out.println();

        FaturaProblematica fatura = new FaturaProblematica(500.0);

        // Simulando o problema: qualquer um adiciona pagamentos sem controle
        fatura.getPagamentos().add(new Pagamento(100.0, "BOLETO"));
        fatura.getPagamentos().add(new Pagamento(999.0, "BOLETO")); // pagamento indevido!

        System.out.println("    Pagamentos na fatura: " + fatura.getPagamentos().size());
        System.out.println("    Incluindo um de R$999 que ninguém deveria ter adicionado.");
        System.out.println("    Sem validação. Sem controle. A porta dos fundos está aberta.");
        System.out.println();
    }

    // ============================================================
    // PROBLEMA 2: regra de negócio fora do dono
    // ============================================================
    static void demonstrarProblema2_RegraForaDoDono() {
        System.out.println("[ PROBLEMA 2 ] Regra de negócio fora do dono (propagação de mudanças)");
        System.out.println();
        System.out.println("Quem decide quando uma fatura está paga? O ProcessadorDeBoletos.");
        System.out.println("E o ProcessadorDeCartaoDeCredito? Ele copia o mesmo if.");
        System.out.println("E o ProcessadorDePix? Mais uma cópia.");
        System.out.println();
        System.out.println("    // Em ProcessadorDeBoletos:");
        System.out.println("    if (total >= fatura.getValor()) { fatura.setPago(true); }");
        System.out.println();
        System.out.println("    // Em ProcessadorDeCartaoDeCredito:");
        System.out.println("    if (total >= fatura.getValor()) { fatura.setPago(true); }");
        System.out.println();
        System.out.println("    // Em ProcessadorDePix:");
        System.out.println("    if (total >= fatura.getValor()) { fatura.setPago(true); }");
        System.out.println();
        System.out.println("A regra mudou? Boa sorte encontrando todos os lugares.");
        System.out.println("Isso é propagação de mudanças. O pesadelo de sistemas legados.");
        System.out.println();
    }

    // ============================================================
    // PROBLEMA 3: Intimidade Inapropriada / Tell Don't Ask
    // ============================================================
    static void demonstrarProblema3_IntimidadeInapropriada() {
        System.out.println("[ PROBLEMA 3 ] Intimidade Inapropriada — Tell, Don't Ask");
        System.out.println();
        System.out.println("O contador da guilda calcula impostos das notas fiscais:");
        System.out.println();
        System.out.println("    if (nf.getValorSemImposto() > 10000) {");
        System.out.println("        valor = 0.06 * nf.getValor();  // 6%");
        System.out.println("    } else {");
        System.out.println("        valor = 0.12 * nf.getValor(); // 12%");
        System.out.println("    }");
        System.out.println();
        System.out.println("Essa regra fiscal não pertence ao contador. Pertence à NotaFiscal.");
        System.out.println("Tell Don't Ask: não pergunte para decidir. Diga ao objeto o que fazer.");
        System.out.println();
        System.out.println("    // ✅ Correto:");
        System.out.println("    double imposto = nf.calculaValorImposto();");
        System.out.println();

        // Demonstrando a solução em código real
        NotaFiscal nf1 = new NotaFiscal(15000.0, 12000.0);
        NotaFiscal nf2 = new NotaFiscal(5000.0, 8000.0);

        System.out.println("    NF de R$15000 (sem imposto R$12000): imposto = R$"
                + nf1.calculaValorImposto() + " (6%)");
        System.out.println("    NF de R$5000 (sem imposto R$8000):  imposto = R$"
                + nf2.calculaValorImposto() + " (12%)");
        System.out.println();
        System.out.println("    A regra mudou? Muda em UM lugar: dentro de NotaFiscal.");
        System.out.println();
    }

    // ============================================================
    // PROBLEMA 4: Lei de Demeter
    // ============================================================
    static void demonstrarProblema4_LeiDeDemeter() {
        System.out.println("[ PROBLEMA 4 ] Lei de Demeter — getter encadeado");
        System.out.println();
        System.out.println("O serviço de cobrança precisa marcar clientes inadimplentes:");
        System.out.println();
        System.out.println("    // ❌ Viola a Lei de Demeter");
        System.out.println("    fatura.getCliente().marcaComoInadimplente();");
        System.out.println();
        System.out.println("Parece inofensivo. Mas ServicoDeCobranca agora depende de:");
        System.out.println("    → Fatura  (dependência declarada)");
        System.out.println("    → Cliente (dependência OCULTA via getCliente())");
        System.out.println();
        System.out.println("Se Cliente mudar: quebra aqui. E em todo lugar com .getCliente().");
        System.out.println();
        System.out.println("Lei de Demeter: fale só com seus amigos imediatos.");
        System.out.println();
        System.out.println("    // ✅ Correto: Fatura encapsula o acesso ao Cliente");
        System.out.println("    fatura.marcaComoInadimplente();");
        System.out.println();
        System.out.println("    Se Cliente mudar: só Fatura é afetada. Um ponto. Não dez.");
        System.out.println();
    }

    // ============================================================
    // SOLUÇÃO: Fatura encapsulada — modelo rico
    // ============================================================
    static void demonstrarSolucao_FaturaEncapsulada() {
        System.out.println("[ SOLUÇÃO ] Fatura encapsulada — modelo rico");
        System.out.println();
        System.out.println("Geralt completou três caças. Três boletos chegam à guilda.");

        FaturaEncapsulada fatura = new FaturaEncapsulada(600.0);

        fatura.adicionaPagamento(new Pagamento(200.0, "BOLETO"));
        System.out.println("    Boleto 1 adicionado. Fatura paga? " + fatura.isPago());

        fatura.adicionaPagamento(new Pagamento(200.0, "BOLETO"));
        System.out.println("    Boleto 2 adicionado. Fatura paga? " + fatura.isPago());

        fatura.adicionaPagamento(new Pagamento(200.0, "BOLETO"));
        System.out.println("    Boleto 3 adicionado. Fatura paga? " + fatura.isPago());

        System.out.println();
        System.out.println("    Pagamentos registrados: " + fatura.getPagamentos().size());
        System.out.println();
        System.out.println("    O ProcessadorDeBoletos não sabe quando a fatura está paga.");
        System.out.println("    A Fatura sabe. E só ela decide. Encapsulado.");
        System.out.println();

        // Demonstrar lista imutável
        System.out.println("    Tentativa de modificar a lista externamente...");
        try {
            fatura.getPagamentos().add(new Pagamento(9999.0, "FRAUDE"));
            System.out.println("    ❌ Adicionou! (isso é um bug — não deveria acontecer)");
        } catch (UnsupportedOperationException e) {
            System.out.println("    ✅ Bloqueado! Lista imutável. A porta dos fundos foi fechada.");
        }
        System.out.println();
    }

    // ============================================================
    // ANTIPADRÃO: Modelo anêmico
    // ============================================================
    static void demonstrarAntipAdrao_ModeloAnemico() {
        System.out.println("[ ANTIPADRÃO ] Modelo Anêmico");
        System.out.println();
        System.out.println("O novo desenvolvedor chegou empolgado com 'separação de responsabilidades':");
        System.out.println();
        System.out.println("    class Fatura { /* só atributos + getters/setters. zero lógica. */ }");
        System.out.println("    class FaturaBLL { /* toda a lógica aqui */ }");
        System.out.println("    class FaturaDAO { /* acesso a dados */ }");
        System.out.println("    class FaturaDelegate { /* mais lógica aqui também */ }");
        System.out.println();
        System.out.println("Resultado: dados separados de comportamento.");
        System.out.println("Parece OO. É código C com sintaxe Java.");
        System.out.println();
        System.out.println("Aniche: 'É, na maioria dos casos, um grande passo para trás.'");
        System.out.println("OO = dados + comportamento JUNTOS. Não um em cada classe.");
        System.out.println();
        System.out.println("=".repeat(60));
        System.out.println("  RESUMO DO CAPÍTULO 5");
        System.out.println("=".repeat(60));
        System.out.println("  Encapsulamento  → esconda o COMO, exponha só o QUÊ");
        System.out.println("  Tell Don't Ask  → dê ordens aos objetos, não pergunte para decidir");
        System.out.println("  Lei de Demeter  → fale só com amigos imediatos (sem .get().get())");
        System.out.println("  Getters/Setters → crie apenas quando há motivo real");
        System.out.println("  Modelo anêmico  → antipadrão: separa o que deve estar junto");
        System.out.println("=".repeat(60));
    }

    // ============================================================
    // Classes internas para o Simulador ser auto-contido
    // ============================================================

    static class Pagamento {
        private double valor;
        private String meio;

        Pagamento(double valor, String meio) {
            this.valor = valor;
            this.meio = meio;
        }

        double getValor() {
            return valor;
        }
    }

    // v1: Fatura problemática
    static class FaturaProblematica {
        private double valor;
        private boolean pago;
        private List<Pagamento> pagamentos = new ArrayList<>();

        FaturaProblematica(double valor) {
            this.valor = valor;
        }

        List<Pagamento> getPagamentos() {
            return pagamentos;
        } // ❌ expõe lista real

        double getValor() {
            return valor;
        }

        boolean isPago() {
            return pago;
        }

        void setPago(boolean pago) {
            this.pago = pago;
        } // ❌ regra fora do dono
    }

    // v2: NotaFiscal encapsulada — Tell Don't Ask
    static class NotaFiscal {
        private double valor;
        private double valorSemImposto;

        NotaFiscal(double valor, double valorSemImposto) {
            this.valor = valor;
            this.valorSemImposto = valorSemImposto;
        }

        double calculaValorImposto() { // ✅ regra mora aqui
            return valorSemImposto > 10000 ? 0.06 * valor : 0.12 * valor;
        }

        double getValor() {
            return valor;
        }
    }

    // v4: Fatura encapsulada — solução completa
    static class FaturaEncapsulada {
        private double valor;
        private boolean pago;
        private List<Pagamento> pagamentos = new ArrayList<>();

        FaturaEncapsulada(double valor) {
            this.valor = valor;
        }

        void adicionaPagamento(Pagamento pagamento) { // ✅ única porta de entrada
            pagamentos.add(pagamento);
            if (valorTotal() >= valor) pago = true;
        }

        List<Pagamento> getPagamentos() { // ✅ lista imutável
            return Collections.unmodifiableList(pagamentos);
        }

        boolean isPago() {
            return pago;
        }

        double getValor() {
            return valor;
        }

        private double valorTotal() { // ✅ privado — implementação escondida
            return pagamentos.stream().mapToDouble(Pagamento::getValor).sum();
        }
    }
}