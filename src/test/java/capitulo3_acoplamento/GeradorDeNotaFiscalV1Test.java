package capitulo3_acoplamento;

import capitulo3_acoplamento.v1_acoplamento_concreto.gerador_nf.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * 🧪 O TESTE DA DOR (ACOPLAMENTO CONCRETO)
 *
 * Este teste demonstra a dificuldade de testar classes acopladas.
 *
 * ⚠️ PROBLEMAS:
 * 1. Mockar classes concretas (EnviadorDeEmail, NotaFiscalDao) é arriscado.
 *    Se elas tiverem lógica no construtor, o teste quebra.
 *    Se elas forem 'final', o Mockito não consegue criar o dublê.
 * 2. O teste conhece detalhes de implementação (sabe que existe um DAO e um Email).
 */
public class GeradorDeNotaFiscalV1Test {

    @Test
    void deveGerarNotaMasComDIficuldadeDeMockar() {
        // PROBLEMA: Para testar a V1, precisamos mockar classes CONCRETAS.
        // Isso é frágil. Se a classe "EnviadorDeEmail" for 'final' ou tiver lógica complexa
        // no construtor, esse teste falha ou fica impossível.
        EnviadorDeEmail emailMock = Mockito.mock(EnviadorDeEmail.class);
        NotaFiscalDao daoMock = Mockito.mock(NotaFiscalDao.class);


        // O acoplamento nos força a lidar com essas classes específicas
        GeradorDeNotaFiscal gerador = new GeradorDeNotaFiscal(emailMock, daoMock);

        Fatura fatura = new Fatura("Geralt", 1000.0);

        NotaFiscal nf = gerador.gera(fatura);

        // Verifica se chamou os métodos das classes concretas
        verify(emailMock).enviaEmail(nf);
        verify(daoMock).persiste(nf);
    }
}
