package capitulo3_acoplamento;

import java.util.Arrays;
import java.util.List;

/**
 * Campo de Treinamento para testar o Acoplamento.
 * Aqui vamos comparar dois bruxos realizando a mesma tarefa:
 * 1. O Bruxo V1 (Acoplado/Rígido)
 * 2. O Bruxo V2 (Ninja/SOLID/Flexível)
 *
 * @author Weriton L. Petreca
 */
public class SimuladorDeAcoplamento {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("⚔️ INICIANDO A SIMULAÇÃO DE ACOPLAMENTO ⚔️\n");

        executarCenarioV1_O_Bruxo_Rigido();

        System.out.println("\n--------------------------------------------------\n");

        executarCenarioV2_O_Bruxo_Mestre();
    }

    /**
     * CENÁRIO 1: O Caminho da Dor (Acoplamento Concreto).
     *
     * O Gerador exige instâncias exatas de EnviadorDeEmail e NotaFiscalDao.
     * Não há espaço para improviso ou novas ferramentas (como SAP).
     */
    private static void executarCenarioV1_O_Bruxo_Rigido() {
        System.out.println(">>> CENÁRIO 1: O Bruxo Rígido (Acoplamento Concreto)");

        // 1. Preparando as ferramentas fixas (Concretas)
        var email = new capitulo3_acoplamento.v1_acoplamento_concreto.EnviadorDeEmail();
        var dao = new capitulo3_acoplamento.v1_acoplamento_concreto.NotaFiscalDao();

        // 2. Criando o Gerador (Perceba que ele pede as classes CONCRETAS no construtor)
        var geradorV1 = new capitulo3_acoplamento.v1_acoplamento_concreto.GeradorDeNotaFiscal(email, dao);

        // 3. Executando a ação
        System.out.println("Gerando nota de 1000 moedas...");
        capitulo3_acoplamento.v1_acoplamento_concreto.NotaFiscal nf = geradorV1.gera(1000.0);

        System.out.println("Resultado: Nota gerada com valor " + nf.getValor() + " e imposto " + nf.getImposto());
        System.out.println("(Nota: Se quiséssemos adicionar o SAP aqui, teríamos que REESCREVER a classe Gerador)");
    }

    /**
     * CENÁRIO 2: O Caminho da Sabedoria (Inversão de Dependência).
     *
     * O Gerador aceita qualquer "AcaoAposGerarNota".
     * Podemos montar nosso arsenal (Lista) da forma que quisermos.
     */

    private static void executarCenarioV2_O_Bruxo_Mestre() {
        System.out.println(">>> CENÁRIO 2: O Bruxo Mestre (DIP & Polimorfismo)");

        // 1. Convocando os Aliados (Todos assinam a interface AcaoAposGerarNota)
        // Note que agora usamos a INTERFACE para referenciar as instâncias, se quiséssemos.
        var acaoEmail = new capitulo3_acoplamento.v2_inversao_dependencia.EnviadorDeEmail();
        var acaoDao = new capitulo3_acoplamento.v2_inversao_dependencia.NotaFiscalDao();
        var acaoSap = new capitulo3_acoplamento.v2_inversao_dependencia.SapERP(); // <--- Novidade!
        var acaoAuditoria = new capitulo3_acoplamento.v2_inversao_dependencia.LogDeAuditoria();
        var acaoSms = new capitulo3_acoplamento.v2_inversao_dependencia.EnviadorDeSMS();

        // 2. Montando o Arsenal (Lista de Interfaces)
        // O Gerador não sabe quem está nesta lista, apenas que eles obedecem ao contrato.
        List<capitulo3_acoplamento.v2_inversao_dependencia.AcaoAposGerarNota> acoes = Arrays.asList(
                acaoEmail,
                acaoDao,
                acaoSap,
                acaoAuditoria,
                acaoSms

        );

        // 3. Criando o Gerador (Ele pede apenas a LISTA de interfaces)
        var geradorV2 = new capitulo3_acoplamento.v2_inversao_dependencia.GeradorDeNotaFiscal(acoes);

        // 4. Executando a ação
        System.out.println("Gerando nota de 2000 moedas com poder total...");
        capitulo3_acoplamento.v2_inversao_dependencia.NotaFiscal nf = geradorV2.gera(2000.0);

        System.out.println("\n--------------------------------------------------\n");

        // 5. Testando com valor abaixo de 1000
        System.out.println("Testando com valor abaixo de 1000... (não deve disparar SMS)");
        capitulo3_acoplamento.v2_inversao_dependencia.NotaFiscal nf2 = geradorV2.gera(500.00);

        System.out.println("Resultado: Notas V2 geradas com sucesso!");
    }
}