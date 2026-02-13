package capitulo2_coesao;

import capitulo2_coesao.v3_o_elixir_enum.CalculadoraDeSalario;
import capitulo2_coesao.v3_o_elixir_enum.Cargo;
import capitulo2_coesao.v3_o_elixir_enum.Funcionario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("🧪 Testes v3: O Elixir (Enum Inteligente)")
class CalculadoraV3Test {

    @Test
    @DisplayName("DBA com salario alto deve ter 25% de desconto")
    void deveCalcularParaDBARico() {
        CalculadoraDeSalario calc = new CalculadoraDeSalario();
        // Na v3, o Cargo já possui a regra embutida!
        Funcionario dba = new Funcionario(Cargo.DBA, 4500.0);

        double salario = calc.calcula(dba);

        // 4500 * 0.75 = 3375
        assertEquals(3375.0, salario, 0.001);
    }

    @Test
    @DisplayName("Novo cargo TESTER deve usar regra similar ao DBA")
    void deveCalcularParaTester() {
        CalculadoraDeSalario calc = new CalculadoraDeSalario();
        Funcionario tester = new Funcionario(Cargo.TESTER, 4500.0);

        double salario = calc.calcula(tester);

        assertEquals(3375.0, salario, 0.001);
    }
}