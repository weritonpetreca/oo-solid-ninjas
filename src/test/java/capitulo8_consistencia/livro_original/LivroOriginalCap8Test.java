package capitulo8_consistencia.livro_original;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cap8 — Testes das Classes Originais do Livro")
class LivroOriginalCap8Test {

    // ─── 8.1 Construtores Ricos ───────────────────────────────────────────────

    @Nested
    @DisplayName("8.1 — Pedido com construtor rico")
    class PedidoTest {

        @Test
        @DisplayName("deve criar Pedido com Cliente obrigatório")
        void deveCriarPedidoComCliente() {
            Cliente cliente = new Cliente("Mauricio", "1234-5678");
            Pedido pedido  = new Pedido(cliente);
            assertEquals("Mauricio", pedido.getCliente().getNome());
        }

        @Test
        @DisplayName("valorTotal deve acumular itens adicionados")
        void valorTotalDeveAcumularItens() {
            Pedido pedido = new Pedido(new Cliente("Mauricio", "1234-5678"));
            pedido.adicionaItem(new Item("SALGADO",      50.0));
            pedido.adicionaItem(new Item("REFRIGERANTE", 50.0));
            assertEquals(100.0, pedido.getValorTotal(), 0.001);
        }

        @Test
        @DisplayName("PedidoSemConstrutorRico deve permitir criação sem cliente — estado inválido")
        void pedidoSemConstrutorRicoPermiteCriacaoSemCliente() {
            PedidoSemConstrutorRico pedido = new PedidoSemConstrutorRico();
            assertNull(pedido.getCliente()); // ❌ estado inválido é possível
        }
    }

    @Nested
    @DisplayName("8.1 — Carro com dois construtores")
    class CarroTest {

        @Test
        @DisplayName("construtor padrão deve usar Pneu e Motor padrão")
        void construtorPadraoDeveUsarComponentesPadrao() {
            Carro carro = new Carro();
            assertEquals("Pneu Padrão",  carro.getPneu().getTipo());
            assertEquals("Motor Padrão", carro.getMotor().getTipo());
        }

        @Test
        @DisplayName("construtor completo deve usar componentes fornecidos")
        void construtorCompletoDeveUsarComponentesFornecidos() {
            Carro carro = new Carro(new PneuEsportivo(), new MotorPadrao());
            assertEquals("Pneu Esportivo", carro.getPneu().getTipo());
        }
    }

    // ─── 8.2 Validando Dados ─────────────────────────────────────────────────

    @Nested
    @DisplayName("8.2 — CPF: abordagem 1 (construtor)")
    class CpfConstrutorTest {

        @Test
        @DisplayName("deve criar CPF válido")
        void deveCriarCpfValido() {
            CPF cpf = new CPF("12345678901");
            assertEquals("12345678901", cpf.get());
        }

        @Test
        @DisplayName("deve rejeitar CPF com menos de 11 dígitos")
        void deveRejeitarCpfCurto() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("1234567"));
        }

        @Test
        @DisplayName("deve rejeitar CPF com letras")
        void deveRejeitarCpfComLetras() {
            assertThrows(IllegalArgumentException.class, () -> new CPF("1234567890a"));
        }

        @Test
        @DisplayName("deve rejeitar CPF nulo")
        void deveRejeitarCpfNulo() {
            assertThrows(IllegalArgumentException.class, () -> new CPF(null));
        }
    }

    @Nested
    @DisplayName("8.2 — CPF: abordagem 2 (valida())")
    class CpfValidaTest {

        @Test
        @DisplayName("valida() deve retornar true para CPF com 11 dígitos")
        void validaDeveRetornarTrueParaCpfValido() {
            assertTrue(new CPFComValidacao("12345678901").valida());
        }

        @Test
        @DisplayName("valida() deve retornar false para CPF curto")
        void validaDeveRetornarFalseParaCpfCurto() {
            assertFalse(new CPFComValidacao("123").valida());
        }

        @Test
        @DisplayName("valida() deve retornar false para nulo")
        void validaDeveRetornarFalseParaNulo() {
            assertFalse(new CPFComValidacao(null).valida());
        }

        @Test
        @DisplayName("deve permitir instanciação com CPF inválido (diferença da abordagem 1)")
        void devePermitirInstanciacaoComCpfInvalido() {
            // ✅ este é o ponto: abordagem 2 PERMITE isso, valida depois
            assertDoesNotThrow(() -> new CPFComValidacao("invalido"));
        }
    }

    @Nested
    @DisplayName("8.2 — CPFBuilder: abordagem 3")
    class CpfBuilderTest {

        @Test
        @DisplayName("deve construir CPF válido")
        void deveConstruirCpfValido() {
            CPF cpf = new CPFBuilder().build("12345678901");
            assertEquals("12345678901", cpf.get());
        }

        @Test
        @DisplayName("deve lançar exceção para CPF inválido")
        void deveLancarExcecaoParaCpfInvalido() {
            assertThrows(IllegalArgumentException.class,
                    () -> new CPFBuilder().build("invalido"));
        }
    }

    @Nested
    @DisplayName("8.2 — ValidadorDeCliente e chain of responsibility")
    class ValidadorDeClienteTest {

        @Test
        @DisplayName("deve validar cliente com dados completos")
        void deveValidarClienteCompleto() {
            ValidadorDeCliente validador = new ValidadorDeCliente();
            ClienteParaValidar cliente   = new ClienteParaValidar(
                    "Mauricio", 30, null, "1234-5678", "12345678901");
            assertTrue(validador.ehValido(cliente));
        }

        @Test
        @DisplayName("deve rejeitar cliente sem nome")
        void deveRejeitarClienteSemNome() {
            ValidadorDeCliente validador = new ValidadorDeCliente();
            ClienteParaValidar cliente   = new ClienteParaValidar(
                    "", 30, null, "1234-5678", "12345678901");
            assertFalse(validador.ehValido(cliente));
        }

        @Test
        @DisplayName("deve rejeitar cliente menor de idade sem nome do pai")
        void deveRejeitarMenorSemNomeDoPai() {
            ValidadorDeCliente validador = new ValidadorDeCliente();
            ClienteParaValidar cliente   = new ClienteParaValidar(
                    "João", 16, null, "1234-5678", "12345678901");
            assertFalse(validador.ehValido(cliente));
        }

        @Test
        @DisplayName("chain: NomeRequerido deve bloquear cliente sem nome")
        void chainNomeRequeridoDeveBloquear() {
            NomeRequeridoOriginal chain   = new NomeRequeridoOriginal(new TelefoneRequerido());
            ClienteParaValidar invalido = new ClienteParaValidar(
                    "", 30, null, "1234-5678", "12345678901");
            assertFalse(chain.ehValido(invalido));
        }

        @Test
        @DisplayName("chain: todos válidos deve retornar true")
        void chainTodosValidosDeveRetornarTrue() {
            NomeRequeridoOriginal chain  = new NomeRequeridoOriginal(new TelefoneRequerido(new CPFRequerido()));
            ClienteParaValidar valido = new ClienteParaValidar(
                    "Mauricio", 30, null, "1234-5678", "12345678901");
            assertTrue(chain.ehValido(valido));
        }
    }

    // ─── 8.4 Tiny Types ──────────────────────────────────────────────────────

    @Nested
    @DisplayName("8.4 — Tiny Types originais do livro")
    class TinyTypesOriginaisTest {

        @Test
        @DisplayName("Telefone deve armazenar valor corretamente")
        void telefoneDeveArmazenarValor() {
            Telefone tel = new Telefone("11 1234-5678");
            assertEquals("11 1234-5678", tel.get());
        }

        @Test
        @DisplayName("Email deve normalizar para minúsculas")
        void emailDeveNormalizarMinusculas() {
            Email email = new Email("MAURICIO@GMAIL.COM");
            assertEquals("mauricio@gmail.com", email.get());
        }

        @Test
        @DisplayName("Email deve rejeitar endereço sem @")
        void emailDeveRejeitarSemArroba() {
            assertThrows(IllegalArgumentException.class, () -> new Email("semArroba"));
        }

        @Test
        @DisplayName("AlunoComTinyTypes deve criar instância com tipos corretos")
        void alunoComTinyTypesDeveCriarCorretamente() {
            var aluno = new AlunoComTinyTypes(
                    new Nome("Mauricio Aniche"),
                    new Email("mauricioaniche@gmail.com"),
                    new Endereco("Rua Vergueiro", "São Paulo"),
                    new Telefone("11 1234-5678"),
                    new CPF("12345678901")
            );
            assertEquals("Mauricio Aniche",  aluno.getNome().get());
            assertEquals("São Paulo", aluno.getEndereco().getCidade());
        }
    }

    // ─── 8.6 Imutabilidade ───────────────────────────────────────────────────

    @Nested
    @DisplayName("8.6 — EnderecoImutavel original do livro")
    class EnderecoImutavelTest {

        @Test
        @DisplayName("setRua deve retornar nova instância com nova rua")
        void setRuaDeveRetornarNovaInstancia() {
            EnderecoImutavel original = new EnderecoImutavel("Rua Vergueiro", 3185);
            EnderecoImutavel novo     = original.setRua("Rua Augusta");

            assertEquals("Rua Vergueiro", original.getRua()); // original intacta
            assertEquals("Rua Augusta",   novo.getRua());
            assertEquals(3185, novo.getNumero());
            assertNotSame(original, novo);
        }

        @Test
        @DisplayName("setNumero deve retornar nova instância com novo número")
        void setNumeroDeveRetornarNovaInstancia() {
            EnderecoImutavel original = new EnderecoImutavel("Rua Vergueiro", 3185);
            EnderecoImutavel novo     = original.setNumero(100);

            assertEquals(3185, original.getNumero()); // original intacta
            assertEquals(100,  novo.getNumero());
            assertEquals("Rua Vergueiro", novo.getRua());
        }

        @Test
        @DisplayName("encadeamento de operações deve preservar imutabilidade")
        void encadeamentoDevePreservarImutabilidade() {
            EnderecoImutavel base    = new EnderecoImutavel("Rua X", 10);
            EnderecoImutavel passo1  = base.setRua("Rua Y");
            EnderecoImutavel passo2  = passo1.setNumero(20);

            assertEquals("Rua X", base.getRua());   // base intacta
            assertEquals("Rua Y", passo1.getRua()); // passo1 intacto
            assertEquals("Rua Y", passo2.getRua());
            assertEquals(20, passo2.getNumero());
        }
    }
}
