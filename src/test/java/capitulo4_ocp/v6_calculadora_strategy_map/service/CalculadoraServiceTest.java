package capitulo4_ocp.v6_calculadora_strategy_map.service;

import capitulo4_ocp.v6_calculadora_strategy_map.domain.Compra;
import capitulo4_ocp.v6_calculadora_strategy_map.ports.ServicoDeEntrega;
import capitulo4_ocp.v6_calculadora_strategy_map.ports.TabelaDePreco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🧪 O TESTE DO CÉREBRO (SERVICE)
 *
 * Este teste valida a lógica do {@link CalculadoraService} de forma isolada.
 *
 * 🛡️ A BELEZA DO TESTE UNITÁRIO PURO:
 * Repare que NÃO usamos Spring, Mockito ou qualquer framework.
 * Como o Service depende apenas de interfaces (Mapas de interfaces), podemos
 * criar nossas próprias implementações "falsas" na mão (com lambdas).
 *
 * Isso torna o teste extremamente rápido e focado.
 */
public class CalculadoraServiceTest {

    private CalculadoraService service;

    @BeforeEach
    void setUp() {
        // 1. Criamos os nossos Maps simulados (Mocking manual)
        Map<String, TabelaDePreco> tabelas = new HashMap<>();
        // Simulamos uma tabela VIP que dá 10% de desconto
        tabelas.put("VIP", valor -> 0.10);

        Map<String, ServicoDeEntrega> fretes = new HashMap<>();
        // Simulamos um frete GRÁTIS que custa 0
        fretes.put("GRATIS", cidade -> 0.0);

        // 2. Instanciamos o serviço injetando nossos Maps falsos.
        // Veja como o código Clean brilha aqui: testamos sem o Spring!
        service = new CalculadoraService(tabelas, fretes);
    }

    @Test
    @DisplayName("Deve calcular corretamente com cliente VIP e Frete Grátis")
    void deveCalcularCorretamenteVIPGratis() {

        Compra compra = new Compra("Poção", 100.0, "Novigrad");

        double total = service.calcularTotal(compra, "VIP", "GRATIS");

        assertEquals(90.0, total, 0.001);
    }

    @Test
    @DisplayName("Deve lançar exceção se faltar a chave no Map (Estratégia Inválida)")
    void deveLancarExcecaoQuandoEstrategiaNaoExistir() {
        Compra compra = new Compra("Poção", 100.0, "Novigrad");

        // AssertThrows verifica se a nossa Programação Defensiva funcionou!
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->{
            service.calcularTotal(compra, "FANTASMA", "INVISIVEL"); // Chaves que não colocamos no Map
        });

        assertTrue(exception.getMessage().contains("Estratégia de preço ou frete inválido!"));
    }
}
