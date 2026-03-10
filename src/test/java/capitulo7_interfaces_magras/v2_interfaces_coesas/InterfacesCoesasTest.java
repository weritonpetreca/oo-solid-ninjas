package capitulo7_interfaces_magras.v2_interfaces_coesas;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterfacesCoesasTest {

    @Test
    void issImplementaAsDuasInterfaces() {
        ISS iss = new ISS();
        assertTrue(iss instanceof CalculadorDeImposto);
        assertTrue(iss instanceof GeradorDeNota);
    }

    @Test
    void ixmxImplementaSoCalculadorDeImposto() {
        IXMX ixmx = new IXMX();
        assertTrue(ixmx instanceof CalculadorDeImposto);
        assertFalse(ixmx instanceof GeradorDeNota);
    }

    @Test
    void processadorCalculaTodosOsImpostos() {
        ProcessadorFiscal p = new ProcessadorFiscal();
        List<CalculadorDeImposto> calculadores = List.of(new ISS(), new IXMX());
        double total = p.somaTodosImpostos(calculadores, 1000.0);
        assertEquals(300.0, total, 0.001); // 100 (ISS 10%) + 200 (IXMX 20%)
    }

    @Test
    void processadorGeraNotasApenasDosQueEmitem() {
        ProcessadorFiscal p = new ProcessadorFiscal();
        List<GeradorDeNota> geradores = List.of(new ISS());
        List<NotaFiscal> notas = p.geraTodasAsNotas(geradores);
        assertEquals(1, notas.size());
    }
}
