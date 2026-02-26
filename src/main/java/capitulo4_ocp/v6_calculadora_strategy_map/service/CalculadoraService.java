package capitulo4_ocp.v6_calculadora_strategy_map.service;

import capitulo4_ocp.v6_calculadora_strategy_map.domain.Compra;
import capitulo4_ocp.v6_calculadora_strategy_map.ports.ServicoDeEntrega;
import capitulo4_ocp.v6_calculadora_strategy_map.ports.TabelaDePreco;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 🧠 O CÉREBRO (SERVICE) - A SOLUÇÃO DO OCP
 *
 * Aqui está a mágica do Spring + Strategy Pattern.
 *
 * 🛡️ COMO FUNCIONA?
 * Em vez de termos IFs ("if VIP use TabelaVip"), nós injetamos um MAPA.
 * O Spring procura todas as classes que implementam {@link TabelaDePreco} e as coloca
 * dentro do mapa `tabelasDisponiveis`.
 *
 * A chave do mapa é o nome do componente ("VIP", "PADRAO").
 * O valor é a instância da classe.
 *
 * ⚔️ OCP (OPEN/CLOSED PRINCIPLE):
 * Se criarmos uma nova classe `TabelaBlackFriday` anotada com `@Component("BLACK_FRIDAY")`,
 * ela aparecerá automaticamente neste mapa.
 * NÃO precisamos mudar uma linha de código nesta classe Service.
 */
@Service
public class CalculadoraService {

    private final Map<String, TabelaDePreco> tabelasDisponiveis;
    private final Map<String, ServicoDeEntrega> fretesDisponiveis;

    // O Spring injeta automaticamente todos os beans encontrados nos mapas
    public CalculadoraService(Map<String, TabelaDePreco> tabelasDisponiveis,
                              Map<String, ServicoDeEntrega> fretesDisponiveis) {
        this.tabelasDisponiveis = tabelasDisponiveis;
        this.fretesDisponiveis = fretesDisponiveis;
    }

    public double calcularTotal(Compra compra, String tipoCliente, String tipoFrete) {
        // Busca a estratégia no mapa usando a chave (ex: "VIP")
        TabelaDePreco tabela = tabelasDisponiveis.get(tipoCliente.toUpperCase());
        ServicoDeEntrega entrega = fretesDisponiveis.get(tipoFrete.toUpperCase());

        if (tabela == null || entrega == null) {
            throw new IllegalArgumentException("Estratégia de preço ou frete inválido!");
        }

        double desconto = tabela.descontoPara(compra.getValor());
        double frete = entrega.para(compra.getCidade());

        return compra.getValor() * (1 - desconto) + frete;
    }
}
