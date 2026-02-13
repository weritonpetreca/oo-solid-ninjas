package capitulo2_coesao;

import capitulo2_coesao.v4_a_mascara_isp.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("🛡️ Testes v4: A Máscara (ISP)")
class CalculadoraV4Test {

    @Test
    @DisplayName("Regra deve aceitar Funcionario pois ele implementa a interface")
    void regraDeveAceitarFuncionarioMasVerApenasDados() {
        RegraDeCalculo regra = new DezOuVintePorCento();
        Funcionario f = new Funcionario("Yennefer", Cargo.DESENVOLVEDOR, 5000.0);

        // O teste passa o objeto 'f', mas internamente a regra só vê 'DadosParaCalculo'
        double resultado = regra.calcula(f);

        assertEquals(4000.0, resultado, 0.001);
    }

    @Test
    @DisplayName("Podemos criar um MOCK (Dublê) que não é funcionário")
    void regraDeveAceitarQualquerCoisaQueTenhaDados() {
        // Cenário: Quero testar a regra sem instanciar um Funcionario pesado
        // Crio uma classe anônima ou Mock que implementa a interface
        DadosParaCalculo robo = new DadosParaCalculo() {
            @Override
            public double getSalarioBase() { return 10000.0; }
            @Override
            public Cargo getCargo() { return Cargo.DESENVOLVEDOR; }
        };

        RegraDeCalculo regra = new DezOuVintePorCento();
        double resultado = regra.calcula(robo);

        assertEquals(8000.0, resultado, 0.001);
    }
}