package capitulo6_heranca_composicao.v3_acoplamento_pai_filho;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("🔗 v3: O Acoplamento Tóxico (Pai e Filho)")
class AcoplamentoPaiFilhoTest {

    @Test
    @DisplayName("Funcionário base ganha 20% de bônus")
    void funcionarioGanha20Porcento() {
        Funcionario f = new Funcionario(10000.0);
        assertEquals(2000.0, f.bonus(), 0.001);
    }

    @Test
    @DisplayName("Gerente Acoplado depende do cálculo do pai (super.bonus)")
    void gerenteComAcoplamentoGanha30Porcento() {
        GerenteComAcoplamento g = new GerenteComAcoplamento(1000.0, "ABC-1234");
        assertEquals(300.0, g.bonus(), 0.001); // 20% + 10%
    }

    @Test
    @DisplayName("Gerente Desacoplado tem sua própria regra (30% direto)")
    void gerenteSemAcoplamentoGanha30Porcento() {
        GerenteSemAcoplamento g = new GerenteSemAcoplamento(1000.0,"ABC-1234");
        assertEquals(300.0, g.bonus(), 0.001); // 30% direto
    }
}
