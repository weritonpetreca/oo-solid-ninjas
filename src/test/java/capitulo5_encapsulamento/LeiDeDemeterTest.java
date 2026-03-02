package capitulo5_encapsulamento;

import capitulo5_encapsulamento.v3_lei_de_demeter.Cliente;
import capitulo5_encapsulamento.v3_lei_de_demeter.FaturaEncapsulada;
import capitulo5_encapsulamento.v3_lei_de_demeter.ServicoDeCobranca;
import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LeiDeDemeterTest {

    @Test
    void deveMarcarClienteComoInadimplente_atravesDaFatura() {
        // ServicoDeCobranca não recebe Cliente, não cria Cliente, não chama Cliente
        // Ele só conhece FaturaEncapsulada — e isso é suficiente
        Cliente cliente = new Cliente("Geralt de Rivia");
        FaturaEncapsulada fatura = new FaturaEncapsulada(1000.0, cliente);
        ServicoDeCobranca servico = new ServicoDeCobranca();

        servico.processaInadimplencia(fatura);

        assertTrue(cliente.isInadimplente(),
                "Cliente marcado como inadimplente sem o ServicoDeCobranca conhecê-lo diretamente");
    }
}
