package capitulo3_acoplamento;

import capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

/**
 * 🧪 O TESTE DO DELEGADOR (ENCAPSULAMENTO)
 *
 * Este teste mostra como o encapsulamento simplifica a vida do cliente.
 *
 * 🛡️ VANTAGENS:
 * 1. Setup Limpo: Mockamos apenas o "EntregadorDeNFs".
 *    Não precisamos saber se ele usa Correios, Pombos ou Drones.
 * 2. Foco: O teste valida apenas se o Despachador "mandou entregar".
 *    A lógica de "como entregar" é testada em outro lugar (EntregadorDeNFsTest).
 */
@ExtendWith(MockitoExtension.class)
public class DespachadorV2Test {

    @Mock
    private NFDao dao;
    @Mock
    private CalculadorDeImposto impostos;
    @Mock
    private EntregadorDeNFs entregador; // Mockamos o especialista

    @Test
    void deveProcessarNotaDelegandoEntrega() {
        // 1. ARRANGE
        DespachadorDeNotasFiscais despachador = new DespachadorDeNotasFiscais(impostos, entregador, dao);
        NotaFiscal nf = new NotaFiscal(1000.0);

        when(impostos.para(nf)).thenReturn(60.0);

        // 2. ACT
        despachador.processa(nf);

        // 3. ASSERT
        // Não precisamos testar "Sedex10 vs Comum" aqui.
        // Só verificamos se o Despachador passou a bola para o Entregador.
        verify(entregador).entrega(nf);
        verify(dao).persiste(nf);
    }
}
