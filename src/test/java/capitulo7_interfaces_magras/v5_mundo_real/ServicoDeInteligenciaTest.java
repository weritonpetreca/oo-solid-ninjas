package capitulo7_interfaces_magras.v5_mundo_real;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicoDeInteligenciaTest {

    @Test
    void comunicaParaTodosOsMeios() {
        List<String> enviados = new ArrayList<>();

        MeioDeComunicacao meioDeTeste = (d, msg) -> enviados.add(d.nomeCompleto() + ":" + msg);

        ServicoDeInteligencia servico = new ServicoDeInteligencia(
                List.of(meioDeTeste, meioDeTeste) // dois meios fakes
        );

        Cacador geralt = new Cacador("Geralt", "Lobo", "Kaer Morhen");
        servico.comunicar(geralt, "Contrato urgente disponível");

        assertEquals(2, enviados.size()); // dois meios receberam
    }

    @Test
    void alertaTodosOsCacadores() {
        List<String> nomes = new ArrayList<>();
        MeioDeComunicacao fake = (dest, msg) -> nomes.add(dest.nomeCompleto());

        ServicoDeInteligencia servico = new ServicoDeInteligencia(List.of(fake));

        List<Destinatario> equipe = List.of(
                new Cacador("Geralt", "Lobo", "Novigrad"),
                new Cacador("Lambert", "Lobo", "Vizima"),
                new Cacador("Ciri", "Lobo", "Toussaint")
        );

        servico.alertaTodos(equipe, "Reunião em Kaer Morhen");
        assertEquals(3, nomes.size());
    }

    @Test
    void corvoCortaMensagemLongas() {
        CorvoMensageiro corvo = new CorvoMensageiro();
        Cacador c = new Cacador("Eskel", "Lobo", "Kaer Morhen");

        // Mensagem com 200 chars — corvo limita a 80
        String mensagemLonga = "A".repeat(200);
        assertDoesNotThrow(() -> corvo.envia(c, mensagemLonga));
    }

    @Test
    void fabricaCriaServicosCorretos() {
        FabricaDoServicoDeInteligencia fabrica = new FabricaDoServicoDeInteligencia();

        assertNotNull(fabrica.criaServicoCompleto());
        assertNotNull(fabrica.criaServicoBasico());
        assertNotNull(fabrica.criaServicoDeEmergencia());
    }

    @Test
    void interfaceMagraPermiteDoubleDeTesteFacil() {
        // ✅ ISP: interface com apenas 1 método → fácil criar duble de teste
        // Se a interface fosse gorda (10 métodos), este lambda seria impossível
        int[] contador = {0};
        MeioDeComunicacao meioBurro = (dest, msg) -> contador[0]++;

        ServicoDeInteligencia servico = new ServicoDeInteligencia(List.of(meioBurro));
        Cacador c = new Cacador("Vesemir", "Lobo", "Kaer Morhen");

        servico.comunicar(c, "Teste ISP");
        assertEquals(1, contador[0]);
    }
}
