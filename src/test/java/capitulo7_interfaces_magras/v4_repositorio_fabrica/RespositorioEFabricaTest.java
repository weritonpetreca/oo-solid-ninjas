package capitulo7_interfaces_magras.v4_repositorio_fabrica;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RespositorioEFabricaTest {

    @Test
    void repositorioSalvaEBuscaFaturas() {
        RepositorioDeFaturas repo = new FaturaDao();
        repo.salva(new Fatura("Fatura de serviços", 1500.0));
        repo.salva(new Fatura("Fatura de produtos", 3000.0));

        List<Fatura> todas = repo.todas();
        assertEquals(2, todas.size());
    }

    @Test
    void fabricaCriaCalculadoraCorretamenteMontada() {
        FabricaDeCalculadoraDePrecos fabrica = new FabricaDeCalculadoraDePrecos();
        CalculadoraDePrecos calc = fabrica.constroi();

        double preco = calc.calcula("São Paulo");
        assertEquals(115,preco, 0.001); // 100 (tabela) + 15 (correios)
    }

    @Test
    void testeUsaImplementacaoFalsaDoRepositorio() {
        // ✅ Vantagem do repositório com interface:
        // No teste, usamos implementação em memória — sem banco de dados real
        RepositorioDeFaturas repoTeste = new FaturaDao(); // mesma coisa aqui, didaticamente
        repoTeste.salva(new Fatura("Teste", 100.0));

        assertNotNull(repoTeste.buscaPorDescricao("Teste"));
        assertNull(repoTeste.buscaPorDescricao("Não Existe"));

    }
}
