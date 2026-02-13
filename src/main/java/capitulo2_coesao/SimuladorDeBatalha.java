package capitulo2_coesao;

import java.util.List;

/**
 * ⚔️ SIMULADOR DE BATALHA (MAIN CLASS)
 *
 * Este é o campo de treinamento de Kaer Morhen.
 * Aqui invocamos as versões antigas e novas do código para garantir
 * que a refatoração não alterou o resultado final (Comportamento).
 *
 * O objetivo é provar que:
 * v1 (Espaguete) == v2 (Strategy) == v3 (Enum) == v4 (ISP)
 *
 * "A espada muda, mas o corte deve ser o mesmo."
 */

public class SimuladorDeBatalha {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("🐺 INICIANDO O TREINAMENTO DE REFATORAÇÃO...\n");

        testarV1_AStriga();
        testarV2_Estrategia();
        testarV3_OElixir();
        testarV4_AMascara();

        System.out.println("\n🐺 TREINAMENTO CONCLUÍDO. O CÓDIGO ESTÁ LIMPO.");
    }

    /**
     * CENÁRIO V1: A Classe Deus (God Class)
     * Testamos a calculadora cheia de IFs.
     */

    private static void testarV1_AStriga() {
        System.out.println("--- [v1] Testando a 'God Class' ---");

        // Instanciando os objetos da pasta v1
        var cargo = capitulo2_coesao.v1_a_maldicao_god_class.Cargo.DESENVOLVEDOR;
        var funcionario = new capitulo2_coesao.v1_a_maldicao_god_class.Funcionario(cargo, 4000.0);
        var calculadora = new capitulo2_coesao.v1_a_maldicao_god_class.CalculadoraDeSalario();

        double salario = calculadora.calcula(funcionario);
        System.out.println("Salário Base: 4000.0 | Cargo: DEV");
        System.out.println("Resultado v1: " + salario); // Deve ser 3200.0 (4000 * 0.8)
        System.out.println("----------------------------------\n");
    }

    /**
     * CENÁRIO V2: Strategy Pattern
     * Testamos a calculadora que delega para classes separadas, mas ainda tem IFs.
     */

    private static void testarV2_Estrategia() {
        System.out.println("--- [v2] Testando o Pattern Strategy ---");

        // Instanciando os objetos da pasta v2
        var cargo = capitulo2_coesao.v1_a_maldicao_god_class.Cargo.DESENVOLVEDOR; // Reusa o Enum v1
        var funcionario = new capitulo2_coesao.v1_a_maldicao_god_class.Funcionario(cargo, 4000.0); // Reusa Funcionario v1
        var calculadora = new capitulo2_coesao.v2_o_sinal_strategy.CalculadoraDeSalario();

        double salario = calculadora.calcula(funcionario);
        System.out.println("Resultado v2: " + salario);
        System.out.println("----------------------------------\n");
    }

    /**
     * CENÁRIO V3: Enum com Polimorfismo
     * Testamos a versão elegante onde o Cargo decide a regra.
     */

    private static void testarV3_OElixir() {
        System.out.println("--- [v3] Testando Enum Inteligente ---");

        // Aqui usamos os objetos da pasta v3, pois o Cargo mudou!
        var cargo = capitulo2_coesao.v3_o_elixir_enum.Cargo.DESENVOLVEDOR;
        var funcionario = new capitulo2_coesao.v3_o_elixir_enum.Funcionario(cargo, 4000.0);
        var calculadora = new capitulo2_coesao.v3_o_elixir_enum.CalculadoraDeSalario();

        double salario = calculadora.calcula(funcionario);
        System.out.println("Resultado v3: " + salario);
        System.out.println("----------------------------------\n");
    }

    /**
     * CENÁRIO V4: Interface Segregation (ISP)
     * Testamos a versão segura que impede acesso a métodos perigosos.
     */

    private static void testarV4_AMascara() {
        System.out.println("--- [v4] Testando a Máscara ISP ---");

        // Objetos da pasta v4
        var cargo = capitulo2_coesao.v4_a_mascara_isp.Cargo.DESENVOLVEDOR;
        var funcionario = new capitulo2_coesao.v4_a_mascara_isp.Funcionario("Geralt", cargo, 4000.0);
        var calculadora = new capitulo2_coesao.v4_a_mascara_isp.CalculadoraDeSalario();

        double salario = calculadora.calcula(funcionario);
        System.out.println("Resultado v4: " + salario);

        // TENTATIVA DE ERRO (Didático)
        try {
            // Se tentarmos acessar o método perigoso diretamente pelo objeto concreto:
            List<String> pedidos = funcionario.getPedidos();
        } catch (RuntimeException e) {
            System.out.println("🛡️ BLINDAGEM ATIVA: O sistema impediu acesso ao banco! -> " + e.getMessage());
        }
        System.out.println("----------------------------------\n");
    }
}