package capitulo6_heranca_composicao;

import capitulo6_heranca_composicao.v1_lsp_violacao.ContaComum;
import capitulo6_heranca_composicao.v1_lsp_violacao.ContaDeEstudante;
import capitulo6_heranca_composicao.v1_lsp_violacao.ProcessadorDeInvestimentos;
import capitulo6_heranca_composicao.v2_lsp_quadrado_retangulo.Quadrado;
import capitulo6_heranca_composicao.v2_lsp_quadrado_retangulo.Retangulo;
import capitulo6_heranca_composicao.v4_composicao.ManipuladorDeSaldo;
import capitulo6_heranca_composicao.v6_mundo_real.*;

import java.util.Arrays;
import java.util.List;

/**
 * ⚔️ CAMPO DE TREINAMENTO: HERANÇA vs COMPOSIÇÃO
 *
 * Aqui simulamos os perigos da herança mal utilizada e a glória da composição.
 */
public class SimuladorDeHeranca {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("==================================================");
        System.out.println("🧬 CAPÍTULO 6: O DILEMA DO SANGUE (LSP)");
        System.out.println("==================================================\n");

        executarCenarioV1_A_Maldicao();
        System.out.println("\n--------------------------------------------------\n");

        executarCenarioV2_O_Quadrado_Traidor();
        System.out.println("\n--------------------------------------------------\n");

        executarCenarioV4_A_Cura_Pela_Composicao();
        System.out.println("\n--------------------------------------------------\n");

        executarCenarioV6_A_Guilda_Do_Leao();
    }

    private static void executarCenarioV1_A_Maldicao() {
        System.out.println(">>> CENÁRIO 1: A Maldição da Herança (LSP Violado)");
        System.out.println("Tentando processar rendimentos de uma lista mista...");

        ContaComum contaNormal = new ContaComum();
        contaNormal.deposita(1000);

        ContaDeEstudante contaEstudante = new ContaDeEstudante();
        contaEstudante.deposita(500);

        List<ContaComum> contas = Arrays.asList(contaNormal, contaEstudante);
        ProcessadorDeInvestimentos processador = new ProcessadorDeInvestimentos();

        try {
            processador.processaRendimento(contas);
        } catch (Exception e) {
            System.out.println("💥 ERRO FATAL: " + e.getClass().getSimpleName());
            System.out.println("   Motivo: A ContaDeEstudante quebrou o contrato do pai!");
        }
    }

    private static void executarCenarioV2_O_Quadrado_Traidor() {
        System.out.println(">>> CENÁRIO 2: O Dilema Geométrico");

        Retangulo r = new Retangulo(10, 20);
        r.setX(30);
        System.out.println("Retângulo (esperado 30x20): " + r.getX() + "x" + r.getY() + " - Área: " + r.area());

        Retangulo q = new Quadrado(10); // Polimorfismo
        q.setX(30);
        System.out.println("Quadrado (esperado 30x10?): " + q.getX() + "x" + q.getY() + " - Área: " + q.area());
        System.out.println("⚠️ O Quadrado alterou a altura sozinho! Comportamento inesperado para quem vê um Retângulo.");
    }

    private static void executarCenarioV4_A_Cura_Pela_Composicao() {
        System.out.println(">>> CENÁRIO 4: A Cura pela Composição");

        ManipuladorDeSaldo manipulador = new ManipuladorDeSaldo();
        manipulador.adiciona(1000);
        manipulador.aplicaJuros(0.10);

        System.out.println("Manipulador isolado funcionou. Saldo: " + manipulador.getSaldo());
        System.out.println("Agora as contas usam esse manipulador internamente, sem herança tóxica.");
    }

    private static void executarCenarioV6_A_Guilda_Do_Leao() {
        System.out.println(">>> CENÁRIO 6: O Mundo Real (A Guilda do Leão)");

        // 1. Criando as estratégias de taxa (Composição)
        CalculadorDeTaxa taxaPadrao = new TaxaPadraoDaGuilda();
        CalculadorDeTaxa taxaLobo = new TaxaNegociadaEscolaDoLobo();

        // 2. Criando os caçadores (Herança segura + Composição)
        Cacador geralt = new Bruxo("Geralt de Rívia", EscolaDeWitcher.LOBO, 100, 1.5, taxaLobo);
        Cacador yennefer = new Mago("Yennefer", 95, taxaPadrao);
        Cacador legolas = new Arqueiro("Legolas", 200, taxaPadrao);

        // 3. O Contrato
        ContratoDeCaca contrato = new ContratoDeCaca("Grifo Real", 2000.0, 5); // Nível 5 (Alto perigo)

        // 4. A Guilda processa tudo (Polimorfismo)
        GuildaDoLeao guilda = new GuildaDoLeao();
        List<Cacador> equipe = Arrays.asList(geralt, yennefer, legolas);

        System.out.println("📜 Relatório da Caçada ao Grifo Real:");
        guilda.imprimirRelatorio(equipe, contrato);

        // 5. Registrando contratos cumpridos (Novo Exemplo)
        System.out.println("\n--- Atualização de Carreira ---");
        
        // Geralt cumpriu o contrato do Grifo
        geralt.registrarContrato(contrato);
        
        // Yennefer cumpriu outro contrato
        ContratoDeCaca contratoDjinn = new ContratoDeCaca("Djinn", 5000.0, 3);
        yennefer.registrarContrato(contratoDjinn);

        System.out.println(geralt.getNome() + " completou " + geralt.getTotalContratos() + " contratos.");
        System.out.println(yennefer.getNome() + " completou " + yennefer.getTotalContratos() + " contratos.");
        System.out.println(legolas.getNome() + " completou " + legolas.getTotalContratos() + " contratos.");
    }
}
