package capitulo4_ocp;

import capitulo4_ocp.v1_o_problema.Compra;
import capitulo4_ocp.v1_o_problema.TabelaDePrecoPadrao;
import capitulo4_ocp.v3_calculadora_aberta.*;
import capitulo4_ocp.v4_exemplo_real.*;

/**
 * ⚔️ SIMULADOR DO OCP — O CAMPO DE BATALHA DO CAPÍTULO 4
 *
 * Aqui demonstramos as três evoluções do código:
 *
 * BATALHA A: CalculadoraDePrecos — A jornada da rigidez para a abertura
 * BATALHA B: Sistema de Exercícios — O exemplo real da Caelum
 *
 * @author Weriton L. Petreca
 */

public class SimuladorDeOCP {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("==================================================");
        System.out.println("🔓  CAPÍTULO 4: O PRINCÍPIO ABERTO-FECHADO (OCP)  🔓");
        System.out.println("==================================================\n");

        executarBatalhaA();
        System.out.println("\n==================================================\n");
        executarBatalhaB();
    }

    // ==================================================================================
    // BATALHA A: A CALCULADORA DE PREÇOS
    // ==================================================================================

    private static void executarBatalhaA() {
        System.out.println("--- [BATALHA A] A CALCULADORA DE PREÇOS ---\n");

        Compra compraPadrao = new Compra("Espada de Prata Aerondight", 3000.0, "São Paulo");

        // -----------------------------------------------------------------------
        System.out.println(">>> CENÁRIO A1: A Calculadora RÍGIDA (v1 - O Problema)");
        System.out.println("    Instancia TabelaDePrecoPadrao e Frete direto no código.");
        System.out.println();

        // A versão rígida - sem jeito de trocar a tabela ou o frete
        capitulo4_ocp.v1_o_problema.CalculadoraDePrecos calculadoraRigida =
                new capitulo4_ocp.v1_o_problema.CalculadoraDePrecos();

        double resultadoRigido = calculadoraRigida.calcula(compraPadrao);
        System.out.println("Compra: " + compraPadrao.getProduto() + " | Valor: R$" + compraPadrao.getValor());
        System.out.println("Resultado (v1 rígida): R$" + resultadoRigido);
        System.out.println("⚠️  Para trocar a tabela ou o frete, preciso abrir a classe!");
        System.out.println();

        // -----------------------------------------------------------------------
        System.out.println(">>> CENÁRIO A2: A Calculadora ABERTA (v3 - OCP Aplicado)");
        System.out.println("    Recebe as dependências pelo construtor — podemos trocar!");
        System.out.println();

        capitulo4_ocp.v3_calculadora_aberta.Compra compraPadraoV3 = new capitulo4_ocp.v3_calculadora_aberta.Compra("Espada de Prata Aerondight", 3000.0, "São Paulo");
        capitulo4_ocp.v3_calculadora_aberta.Compra compraVipV3 = new capitulo4_ocp.v3_calculadora_aberta.Compra("Armadura de Kaer Morhen", 7000.0, "Rio de Janeiro");
        capitulo4_ocp.v3_calculadora_aberta.Compra compraSimplesV3 = new capitulo4_ocp.v3_calculadora_aberta.Compra("Poção de Golondryn", 500.0, "Curitiba");

        // Combinação 1: Cliente padrão em São Paulo
        capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos calcPadrao =
                new capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos(
                        new TabelaDePrecoPadrao(), // interface
                        new FreteCorreios()         // interface
                );
        System.out.println("-- Combinação Padrão (TabelaPadrao + Correios):");
        System.out.println("   Compra: " + compraPadraoV3.getProduto() + " | Valor: R$" + compraPadraoV3.getValor() + " | Cidade: " + compraPadraoV3.getCidade());
        System.out.println("   Total: R$" + calcPadrao.calcula(compraPadraoV3));

        // Combinação 2: Cliente VIP recebe tabela VIP e frete grátis
        capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos calcVip =
                new capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos(
                        new TabelaDePrecoVip(),  // nova tabela — SEM MUDAR a calculadora!
                        new FreteGratis()         // novo frete — SEM MUDAR a calculadora!
                );
        System.out.println("-- Combinação VIP (TabelaVip + FreteGrátis):");
        System.out.println("   Compra: " + compraVipV3.getProduto() + " | Valor: R$" + compraVipV3.getValor() + " | Cidade: " + compraVipV3.getCidade());
        System.out.println("   Total: R$" + calcVip.calcula(compraVipV3));

        // Combinação 3: Parceiro comercial com retirada em loja
        capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos calcParceiro =
                new capitulo4_ocp.v3_calculadora_aberta.CalculadoraDePrecos(
                        new TabelaDePrecoDiferenciada(), // tabela diferenciada
                        new RetiradaEmLoja()              // sem frete
                );
        System.out.println("-- Combinação Parceiro (TabelaDiferenciada + RetiradaEmLoja):");
        System.out.println("   Compra: " + compraSimplesV3.getProduto() + " | Valor: R$" + compraSimplesV3.getValor() + " | Cidade: " + compraSimplesV3.getCidade());
        System.out.println("   Total: R$" + calcParceiro.calcula(compraSimplesV3));

        System.out.println();
        System.out.println("✅ OCP: A MESMA classe CalculadoraDePrecos lidou com 3 cenários completamente diferentes.");
        System.out.println("✅ Nenhuma linha da classe foi alterada para suportar os novos casos.");
    }

    // ==================================================================================
    // BATALHA B: O SISTEMA DE EXERCÍCIOS (EXEMPLO REAL DA CAELUM)
    // ==================================================================================

    private static void executarBatalhaB() {
        System.out.println("--- [BATALHA B] O SISTEMA DE EXERCÍCIOS (CAELUM — EXEMPLO REAL) ---\n");

        System.out.println(">>> CENÁRIO B1: ShowAnswerHelper COM IFs (O Problema)");
        ShowAnswerHelperComIfs helperAntigo = new ShowAnswerHelperComIfs();

        ExercicioLegado multipla = new ExercicioLegado(
                ShowAnswerHelperComIfs.MULTIPLE_TYPE, "java-oo", 3, 7
        );
        ExercicioLegado aberto = new ExercicioLegado(
                ShowAnswerHelperComIfs.OPEN_TYPE, "java-oo", 5, 2
        );

        System.out.println("URL para múltipla escolha: " + helperAntigo.getUrlFor(multipla));
        System.out.println("URL para resposta aberta:  " + helperAntigo.getUrlFor(aberto));
        System.out.println("⚠️  Novo tipo de exercício? Adicionar IF aqui E na JSP.");
        System.out.println();

        System.out.println(">>> CENÁRIO B2: ShowAnswerHelper SEM IFs (OCP Aplicado)");
        ShowAnswerHelper helperNovo = new ShowAnswerHelper();

        Exercise exercicioMultipla = new MultipleChoiceExercise("java-oo", 3, 7);
        Exercise exercicioAberto = new OpenAnswerExercise("java-oo", 5, 2);
        Exercise exercicioSemResposta = new NoAnswerExercise("java-oo", 6, 1);

        System.out.println("URL para múltipla escolha: " + helperNovo.getUrlFor(exercicioMultipla));
        System.out.println("URL para resposta aberta:  " + helperNovo.getUrlFor(exercicioAberto));
        System.out.println("URL para sem resposta:     " + helperNovo.getUrlFor(exercicioSemResposta));
        System.out.println();
        System.out.println("isMultipleChoice: " + helperNovo.isMultipleChoice(exercicioMultipla));
        System.out.println("isOpenAnswer:     " + helperNovo.isOpenAnswer(exercicioAberto));
        System.out.println("isNoAnswer:       " + helperNovo.isNoAnswer(exercicioSemResposta));
        System.out.println();
        System.out.println("✅ OCP: Para adicionar 'ExercícioDeVídeo', basta criar a classe.");
        System.out.println("✅ ShowAnswerHelper NUNCA precisará ser modificado.");
        System.out.println("✅ O compilador GARANTE que o novo tipo implementa viewDetails().");
    }
}
