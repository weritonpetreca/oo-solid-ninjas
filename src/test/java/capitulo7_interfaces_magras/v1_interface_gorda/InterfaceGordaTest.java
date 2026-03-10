package capitulo7_interfaces_magras.v1_interface_gorda;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InterfaceGordaTest {

    @Test
    void issCalculaImpostoEGeraNota() {
        Imposto iss = new ISS();
        assertEquals(100.0, iss.imposto(1000.0), 0.001);
        assertNotNull(iss.geraNota());
    }

    @Test
    void imxmCalculaImpostoMasExplodeAoGerarNota() {
        Imposto imxm = new IXMX();
        assertEquals(200.0, imxm.imposto(1000.0), 0.001);

        // ❌ Interface gorda força implementação inválida
        assertThrows(NaoGeraNotaException.class, imxm::geraNota);
    }

    @Test
    void processadorExplodeComIXMXNaLista() {
        List<Imposto> impostos = List.of(new ISS(), new IXMX());

        // Qualquer processador que chame geraNota() em todos vai explodir
        assertThrows(NaoGeraNotaException.class, () -> {
            for (Imposto i : impostos) {
                i.geraNota(); // ❌ IXMX vai lançar exceção aqui
            }
        });
    }
}
