package capitulo3_acoplamento;

import capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * 🧪 O TESTE DA GLÓRIA (DIP - DEPENDENCY INVERSION)
 *
 * Este teste demonstra a facilidade de testar classes desacopladas.
 *
 * 🛡️ VANTAGENS:
 * 1. Mockamos INTERFACES (AcaoAposGerarNota), o que é seguro e fácil.
 * 2. O teste não sabe se estamos enviando Email, SMS ou salvando no banco.
 *    Ele apenas verifica se a "Ação" foi executada.
 * 3. Podemos testar regras de negócio isoladas (como o SMS > 1000) sem instanciar o Gerador inteiro.
 */
@ExtendWith(MockitoExtension.class) // Habilita os poderes do Mockito
public class GeradorDeNotaFiscalV2Test {

    // Criamos "dublês" da interface. Eles não fazem nada de verdade.
    @Mock
    private AcaoAposGerarNota acao1;
    @Mock
    private AcaoAposGerarNota acao2;

    @Test
    void deveInvocarTodasAsAcoesAposGerarNota() {
        // 1. CENÁRIO (Arrange)
        // Montamos uma lista com nossos espiões (mocks)
        List<AcaoAposGerarNota> acoes = Arrays.asList(acao1, acao2);

        // O Gerador aceita nossa lista falsa feliz da vida (DIP funcionando!)
        GeradorDeNotaFiscal gerador = new GeradorDeNotaFiscal(acoes);

        // 2. AÇÃO (Act)
        Fatura fatura = new Fatura("Yennefer", 1000.0);
        NotaFiscal nf = gerador.gera(fatura);

        // 3. VERIFICAÇÃO (Assert)

        // Verifica se o valor e imposto estão corretos
        assertEquals(1000.0, nf.getValor());
        assertEquals(60, nf.getImposto());

        // A MÁGICA: Verifica se o método 'executa' foi chamado em cada mock.
        // Isso prova que o loop do gerador funcionou, sem precisar enviar email real.
        verify(acao1, times(1)).executa(nf);
        verify(acao2, times(1)).executa(nf);
    }

    @Test
    void deveEnviarSMSQuandoValorForMaiorQue1000() {
        // 1. Cenário: Nota cara
        EnviadorDeSMS enviador = new EnviadorDeSMS();
        NotaFiscal nf = new NotaFiscal(1500.0,90.0);

        // Como o método é void e usa System.out, visualmente no teste veremos a mensagem aparecer no console.
        // Num cenário real com injeção de dependência, faríamos verify().
        System.out.println("--- Teste 1: Esperado aparecer mensagem de envio ---");
        enviador.executa(nf);
    }

    @Test
    void naoDeveEnviarSMSQuandoValorForMenorQue1000() {
        // 1. Cenário: Nota barata.
        EnviadorDeSMS enviador = new EnviadorDeSMS();
        NotaFiscal nf = new NotaFiscal(800.0,48.0);

        System.out.println("--- Teste 2: Esperado SILÊNCIO (nenhuma mensagem de envio) ---");
        enviador.executa(nf);
    }
}
