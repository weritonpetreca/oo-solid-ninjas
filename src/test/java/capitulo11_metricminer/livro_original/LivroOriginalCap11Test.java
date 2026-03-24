package capitulo11_metricminer.livro_original;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap11 — Testes das Classes Originais do MetricMiner")
class LivroOriginalCap11Test {

    // ─── ContadorDeClassesRemovidas ───────────────────────────────────────────

    @Nested
    @DisplayName("ContadorDeClassesRemovidas — visitante do livro")
    class ContadorDeClassesRemovidasTest {

        @Test
        @DisplayName("deve contar zero deleções quando não há arquivos deletados")
        void deveContarZeroSemDelecoes() {
            var csv      = new CSVFile("test.csv");
            var repo     = SCMRepositoryFactory.criarFake("projeto");
            var visitor  = new ContadorDeClassesRemovidas();
            var commit   = new Commit("x1", new Committer("Geralt"), "add",
                    List.of(new Modification("A.java", ModificationType.ADD, "class A {}")));

            visitor.process(repo, commit, csv);
            // Verifica que escreveu 0 deleções
            assertTrue(csv.getConteudo().contains(",0"));
        }

        @Test
        @DisplayName("deve contar corretamente arquivos deletados")
        void deveContarArquivosDeletados() {
            var csv = new CSVFile("test.csv");
            var repo = SCMRepositoryFactory.criarFake("projeto");
            var visitor = new ContadorDeClassesRemovidas();
            var commit = new Commit("x2", new Committer("Geralt"), "refactor",
                    List.of(
                            new Modification("A.java", ModificationType.DELETE, ""),
                            new Modification("B.java", ModificationType.DELETE, ""),
                            new Modification("C.java", ModificationType.MODIFY, "class C {}")
                    ));

            visitor.process(repo, commit, csv);
            assertTrue(csv.getConteudo().contains(",2"));
        }

        @Test
        @DisplayName("nome do visitante deve ser correto")
        void nomeShouldBeCorrect() {
            var visitor = new ContadorDeClassesRemovidas();
            assertEquals("ContadorDeClassesRemovidas", visitor.name());
        }
    }

    // ─── ClassLevelMetricCalculator ──────────────────────────────────────────

    @Nested
    @DisplayName("ClassLevelMetricCalculator — executa métrica por classe")
    class ClassLevelMetricCalculatorTest {

        @Test
        @DisplayName("deve calcular LCOM para arquivos ADD e MODIFY")
        void deveCalcularLcomParaArquivosAddEModify() {
            var csv  = new CSVFile("lcom.csv");
            var repo = SCMRepositoryFactory.criarFake("projeto");
            var calc = new ClassLevelMetricCalculator(new LackOfCohesionFactory());
            var commit = new Commit("x1", new Committer("Geralt"), "add",
                    List.of(new Modification("A.java", ModificationType.ADD,
                            "public class A {\n  private int x;\n  public void m() { x++; }\n}")));

            calc.process(repo, commit, csv);
            assertTrue(csv.getLinhas() >= 1, "Deve ter gravado pelo menos uma linha");
        }

        @Test
        @DisplayName("não deve processar arquivos DELETE")
        void naoDeveProcessarArquivosDelete() {
            var csv  = new CSVFile("lcom.csv");
            var repo = SCMRepositoryFactory.criarFake("projeto");
            var calc = new ClassLevelMetricCalculator(new LackOfCohesionFactory());
            var commit = new Commit("x1", new Committer("Geralt"), "del",
                    List.of(new Modification("A.java", ModificationType.DELETE, "")));

            calc.process(repo, commit, csv);
            assertEquals(0, csv.getLinhas(), "DELETE não deve ser processado");
        }

        @Test
        @DisplayName("nome do calculador deve incluir o nome da fábrica")
        void nomeDeveIncluirFabrica() {
            var calc = new ClassLevelMetricCalculator(new LackOfCohesionFactory());
            assertTrue(calc.name().contains("LCOM"));
        }
    }

    // ─── CSVFile ─────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("CSVFile — mecanismo de persistência")
    class CSVFileTest {

        @Test
        @DisplayName("deve acumular linhas escritas")
        void deveAcumularLinhas() {
            var csv = new CSVFile("test.csv");
            csv.write("projeto", "abc001", 3);
            csv.write("projeto", "abc002", 1);
            assertEquals(2, csv.getConteudo().split("\n").length);
        }

        @Test
        @DisplayName("deve formatar linhas com vírgulas")
        void deveFormatarComVirgulas() {
            var csv = new CSVFile("test.csv");
            csv.write("proj", "hash123", 5);
            assertTrue(csv.getConteudo().contains("proj,hash123,5"));
        }
    }

    // ─── FakeSCM / SCMRepositoryFactory ──────────────────────────────────────

    @Nested
    @DisplayName("FakeSCM — simulação de repositório")
    class FakeSCMTest {

        @Test
        @DisplayName("deve retornar 3 changesets")
        void deveRetornarTresChangesets() {
            var repo = SCMRepositoryFactory.criarFake("selenium-project");
            assertEquals(3, repo.getScm().getChangeSets().size());
        }

        @Test
        @DisplayName("deve retornar commit com modificações para cada changeset")
        void deveRetornarCommitComModificacoes() {
            var repo   = SCMRepositoryFactory.criarFake("selenium-project");
            var commit = repo.getScm().getCommit("abc0001");
            assertFalse(commit.getModifications().isEmpty());
        }

        @Test
        @DisplayName("getLastDir deve retornar o nome do projeto")
        void getLastDirDeveRetornarNomeProjeto() {
            var repo = SCMRepositoryFactory.criarFake("meu-projeto");
            assertEquals("meu-projeto", repo.getLastDir());
        }
    }

    // ─── Métricas ─────────────────────────────────────────────────────────────

    @Nested
    @DisplayName("Métricas de código — LackOfCohesion e CC")
    class MetricasTest {

        @Test
        @DisplayName("LackOfCohesion deve retornar valor entre 0 e 1")
        void lcomDeveRetornarEntre0E1() {
            var metrica = new LackOfCohesionFactory().build();
            double resultado = metrica.calculate(
                    "public class A {\n  private int x;\n  private int y;\n  public void m1() { x++; }\n  public void m2() { y++; }\n}"
            );
            assertTrue(resultado >= 0 && resultado <= 1,
                    "LCOM deve estar entre 0 e 1, mas foi: " + resultado);
        }

        @Test
        @DisplayName("ComplexidadeCiclomatica de classe sem IFs deve retornar 1")
        void ccSemIfsDeveRetornar1() {
            var metrica = new ComplexidadeCiclomaticaFactory().build();
            double resultado = metrica.calculate("public class A { public void m() { return; } }");
            assertEquals(1.0, resultado, 0.001);
        }

        @Test
        @DisplayName("ComplexidadeCiclomatica de classe com IFs deve ser > 1")
        void ccComIfsDeveSerMaiorQue1() {
            var metrica = new ComplexidadeCiclomaticaFactory().build();
            double resultado = metrica.calculate(
                    "public class A { public void m() { if (x > 0) { return; } if (y < 0) { return; } } }");
            assertTrue(resultado > 1.0);
        }
    }
}