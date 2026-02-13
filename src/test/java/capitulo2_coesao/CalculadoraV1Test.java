package capitulo2_coesao;

import capitulo2_coesao.v1_a_maldicao_god_class.CalculadoraDeSalario;
import capitulo2_coesao.v1_a_maldicao_god_class.Cargo;
import capitulo2_coesao.v1_a_maldicao_god_class.Funcionario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("⚔️ Testes v1: A Maldição (God Class)")
class CalculadoraV1Test {

    @Test
    @DisplayName("Deve calcular 20% de desconto para DEV ganhando acima de 3000")
    void deveCalcularSalarioParaDesenvolvedorRico() {
        CalculadoraDeSalario calc = new CalculadoraDeSalario();
        Funcionario f = new Funcionario(Cargo.DESENVOLVEDOR, 4000.0);

        double salarioLiquido = calc.calcula(f);

        assertEquals(3200.0, salarioLiquido, 0.001);
    }

    @Test
    @DisplayName("Deve calcular 10% de desconto para DEV ganhando abaixo de 3000")
    void deveCalcularSalarioParaDesenvolvedorJunior() {
        CalculadoraDeSalario calc = new CalculadoraDeSalario();
        Funcionario f = new Funcionario(Cargo.DESENVOLVEDOR, 2500.0);

        double salarioLiquido = calc.calcula(f);

        assertEquals(2250.0, salarioLiquido, 0.001);
    }
}