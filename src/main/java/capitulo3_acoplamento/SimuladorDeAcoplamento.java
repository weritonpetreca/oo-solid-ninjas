package capitulo3_acoplamento;

import java.util.Arrays;
import java.util.List;

/**
 * Campo de Treinamento para testar o Acoplamento.
 *
 * AQUI TEMOS DUAS BATALHAS DISTINTAS:
 *
 * BATALHA A: O GERADOR DE NOTAS (Foco em DIP - Dependency Inversion Principle)
 * - V1: Acoplado a classes concretas.
 * - V2: Acoplado a interfaces (Lista de Ações).
 * - V3: Arquitetura Hexagonal (DIP Completo).
 *
 * BATALHA B: O DESPACHADOR DE NOTAS (Foco em Encapsulamento e Coesão)
 * - V1: O Despachador micro-gerencia os Correios e a Lei.
 * - V2: O Despachador delega para um especialista (EntregadorDeNFs).
 * - V3: Arquitetura Hexagonal (DIP Completo).
 *
 * @author Weriton L. Petreca
 */

public class SimuladorDeAcoplamento {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("==================================================");
        System.out.println("⚔️  CAPÍTULO 3: AS CRÔNICAS DO ACOPLAMENTO  ⚔️");
        System.out.println("==================================================\n");

        // --- BATALHA A: GERADOR DE NOTAS ---
        System.out.println("--- [PARTE A] O GERADOR DE NOTAS (DIP) ---\n");
        executarCenarioGeradorV1();
        System.out.println();
        executarCenarioGeradorV2();
        System.out.println();
        executarCenarioGeradorV3();

        System.out.println("\n==================================================\n");

        // --- BATALHA B: DESPACHADOR DE NOTAS ---
        System.out.println("--- [PARTE B] O DESPACHADOR DE NOTAS (ENCAPSULAMENTO) ---\n");
        executarCenarioDespachadorV1();
        System.out.println();
        executarCenarioDespachadorV2();
        System.out.println();
        executarCenarioDespachadorV3();
    }


    // ==================================================================================
    // PARTE A: O GERADOR (DIP)
    // ==================================================================================
    private static void executarCenarioGeradorV1() {
        System.out.println(">>> CENÁRIO A1: O Gerador Rígido (Acoplamento Concreto)");
        System.out.println("Pacote: capitulo3_acoplamento.v1_acoplamento_concreto");

        var email = new capitulo3_acoplamento.v1_acoplamento_concreto.gerador_nf.EnviadorDeEmail();
        var dao = new capitulo3_acoplamento.v1_acoplamento_concreto.gerador_nf.NotaFiscalDao();
        var geradorV1 = new capitulo3_acoplamento.v1_acoplamento_concreto.gerador_nf.GeradorDeNotaFiscal(email, dao);

        System.out.println("Gerando nota...");
        var fatura = new capitulo3_acoplamento.v1_acoplamento_concreto.gerador_nf.Fatura("Geralt", 1000.0);
        var nf = geradorV1.gera(fatura);

        System.out.println("Resultado: Nota de " + nf.getValor() + " gerada para " + fatura.getCliente());
    }

    private static void executarCenarioGeradorV2() {
        System.out.println(">>> CENÁRIO A2: O Gerador Mestre (DIP & Polimorfismo)");
        System.out.println("Pacote: capitulo3_acoplamento.v2_inversao_dependencia");

        var acaoEmail = new capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.EnviadorDeEmail();
        var acaoDao = new capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.NotaFiscalDao();
        var acaoSap = new capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.SapERP();
        var acaoAuditoria = new capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.LogDeAuditoria();
        var acaoSms = new capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.EnviadorDeSMS();

        List<capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.AcaoAposGerarNota> acoes = Arrays.asList(
                acaoEmail, acaoDao, acaoSap, acaoAuditoria, acaoSms
        );

        var geradorV2 = new capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.GeradorDeNotaFiscal(acoes);

        System.out.println("Gerando nota com múltiplos ouvintes...");
        var fatura = new capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.Fatura("Vesemir", 2000.0);
        var nf = geradorV2.gera(fatura);

        System.out.println("Resultado: Nota processada com sucesso.");
    }

    private static void executarCenarioGeradorV3() {
        System.out.println(">>> CENÁRIO A3: O Gerador Hexagonal (V3 — DIP Completo)");
        System.out.println("Pacote: capitulo3_acoplamento.v3_dip_completo.gerador_nf");
        System.out.println("(O Gerador só conhece a interface. Polimorfismo puro.");
        System.out.println();

        // 1. Criando os adapters (camada externa)
        var acaoEmail = new capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters.EnviadorDeEmail();
        var acaoDao = new capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters.NotaFiscalDao();
        var acaoSap = new capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters.SapERP();
        var acaoSms = new capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters.EnviadorDeSMS();
        var acaoLog = new capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters.LogDeAuditoria();

        // 2. O Gerador recebe uma lista de interfaces
        List<capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.AcaoAposGerarNota> acoes = Arrays.asList(
                acaoEmail, acaoDao, acaoSap, acaoSms, acaoLog
        );

        var gerador = new capitulo3_acoplamento.v3_dip_completo.gerador_nf.usecases.GeradorDeNotaFiscal(acoes);

        // 3. Gerando duas notas: uma barata e uma cara
        System.out.println("--- Nota 1: Valor baixo (SMS não será enviado) ---");
        var fatura1 = new capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.Fatura("Triss Merigold", 500.0);
        gerador.gera(fatura1);

        System.out.println();
        System.out.println("--- Nota 2: Valor alto (SMS será enviado) ---");
        var fatura2 = new capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.Fatura("Yennefer de Vengerberg", 2000.0);
        gerador.gera(fatura2);

        System.out.println();
        System.out.println("✅ DIP Completo: O Gerador não conhece Email, DAO, SAP, SMS ou Log.");
        System.out.println("   Todos são tratados apenas como 'AcaoAposGerarNota'.");
    }


    // ==================================================================================
    // PARTE B: O DESPACHADOR (ENCAPSULAMENTO)
    // ==================================================================================
    private static void executarCenarioDespachadorV1() {
        System.out.println(">>> CENÁRIO B1: O Despachador Micro-Gerenciador (V1)");
        System.out.println("Pacote: capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf");
        System.out.println("(O Despachador precisa conhecer Correios, Lei, Dao e Imposto...)");

        // 1. Instanciando o caos de dependências
        var dao = new capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf.NFDao();
        var imposto = new capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf.CalculadorDeImposto();
        var lei = new capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf.LeiDeEntrega();
        var correios = new capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf.Correios();

        // 2. O Despachador recebe tudo isso no construtor
        var despachador = new capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf.DespachadorDeNotasFiscais(
                dao, imposto, lei, correios
        );

        // 3. Processando
        var nf = new capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf.NotaFiscal(2500.0);
        despachador.processa(nf);
    }

    private static void executarCenarioDespachadorV2() {
        System.out.println(">>> CENÁRIO B2: O Despachador Delegador (V2)");
        System.out.println("Pacote: capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf");
        System.out.println("(O Despachador só conhece o Entregador. A complexidade foi encapsulada.)");

        // 1. Preparando as dependências
        var dao = new capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.NFDao();
        var imposto = new capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.CalculadorDeImposto();
        var lei = new capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.LeiDeEntrega();
        var correios = new capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.Correios();

        // 2. A MÁGICA: Criamos o Especialista (Entregador)
        // Agrupamos Lei e Correios dentro dele.
        var entregador = new capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.EntregadorDeNFs(lei, correios);

        // 3. O Despachador agora é mais limpo
        var despachador = new capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.DespachadorDeNotasFiscais(
                imposto, entregador, dao
        );

        // 4. Processando
        var nf = new capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.NotaFiscal(2500.0);
        despachador.processa(nf);
    }

    private static void executarCenarioDespachadorV3() {
        System.out.println(">>> CENÁRIO B3: O Despachador Hexagonal (V3 — DIP Completo)");
        System.out.println("Pacote: capitulo3_acoplamento.v3_dip_completo");
        System.out.println("(O Despachador só conhece interfaces. Zero acoplamento concreto.)");
        System.out.println();

        // 1. Montando os adapters (a camada mais externa)
        var lei = new capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters.LeiDeEntrega();
        var correios = new capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters.Correios();

        // 2. Compondo o Entregador (adapter que implementa a porta)
        var entregador = new capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters.EntregadorDeNFs(lei, correios);

        // 3. Demais adapters
        var imposto = new capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters.ImpostoSimples();
        var repositorio = new capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters.NFDao();

        // 4. O Despachador recebe apenas interfaces — ele não sabe o que está por trás
        var despachador = new capitulo3_acoplamento.v3_dip_completo.despachador_nf.usecases.DespachadorDeNotasFiscais(
                imposto,      // CalculadorDeImposto (interface)
                entregador,   // Entregador (interface)
                repositorio   // Repositorio (interface)
        );

        // 5. Processando
        var nf = new capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal(2500.0);
        despachador.processa(nf);

        System.out.println();
        System.out.println("✅ DIP Completo: O Despachador não conhece NFDao, Correios nem LeiDeEntrega.");
        System.out.println("   Troque qualquer adapter sem tocar no use case.");
    }
}