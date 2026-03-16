package capitulo8_consistencia.v1_construtor_rico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SOLUÇÃO v1: Missão com construtor rico — nasce válida, permanece válida.
 *
 * Aniche, Cap. 8, seção 8.1:
 * "Se a classe possui atributos sem os quais ela não pode viver,
 *  eles devem ser pedidos no construtor."
 *
 * Regra aplicada:
 *   ✅ Atributos obrigatórios exigidos no construtor
 *   ✅ Sem setters para atributos que não devem mudar
 *   ✅ Estado interno protegido — só a missão decide seu estado
 *   ✅ Construtor adicional com valores padrão para conveniência
 */
public class Missao {

    private final String monstroAlvo;
    private final String clientePagador;
    private double recompensa;
    private NivelDePerigo nivelDePerigo;
    private final List<String> requisitos;
    private StatusDaMissao status;

    /**
     * Construtor rico — atributos obrigatórios exigidos desde o nascimento.
     * ✅ Impossível criar uma Missão sem monstro, cliente e recompensa.
     */
    public Missao(String monstroAlvo, String clientePagador, double recompensa, NivelDePerigo nivelDePerigo) {
        if (monstroAlvo == null || monstroAlvo.isBlank())
            throw new IllegalArgumentException("Missão precisa de um monstro alvo");
        if (clientePagador == null || clientePagador.isBlank())
            throw new IllegalArgumentException("Missão precisa de um cliente pagador");
        if (recompensa <= 0)
            throw new IllegalArgumentException("Recompensa deve ser maior que zero");

        this.monstroAlvo = monstroAlvo;
        this.clientePagador = clientePagador;
        this.recompensa = recompensa;
        this.nivelDePerigo = nivelDePerigo;
        this.requisitos = new ArrayList<>();
        this.status = StatusDaMissao.DISPONIVEL;
    }

    /**
     * Construtor adicional com perigo padrão (MEDIO).
     * ✅ Aniche: "Atributo necessário mas com valor padrão? Use dois construtores."
     */
    public Missao(String monstroAlvo, String clientePagador, double recompensa) {
        this(monstroAlvo, clientePagador, recompensa, NivelDePerigo.MEDIO);
    }

    public void adicionarRequisito(String requisito) {
        if (requisito != null && !requisito.isBlank()) {
            requisitos.add(requisito);
        }
    }

    public double calcularTaxaDaGuilda() {
        // ✅ recompensa nunca é 0 — o construtor garante
        return recompensa * nivelDePerigo.getMultiplicadorDeTaxa();
    }

    public double calcularRecompensaLiquida() { return recompensa - calcularTaxaDaGuilda(); }

    public String gerarDescricao() {
        // ✅ Sem risco de NullPointerException — construtor garantiu os valores
        return String.format("Caça ao %s - Cliente: %s - Recompensa: %.2f (Perigo: %s)",
                monstroAlvo, clientePagador, recompensa, nivelDePerigo);
    }

    public void aceitar() {
        if (status != StatusDaMissao.DISPONIVEL)
            throw new IllegalStateException("Missão não está disponível para aceitar");
        this.status = StatusDaMissao.EM_ANDAMENTO;
    }

    public void concluir() {
        if (status != StatusDaMissao.EM_ANDAMENTO)
            throw new IllegalStateException("Missão não está em andamento");
        this.status = StatusDaMissao.CONCLUIDA;
    }

    public void cancelar() {
        if (status != StatusDaMissao.EM_ANDAMENTO)
            throw new IllegalStateException("Missão não está disponível para cancelar");
        this.status = StatusDaMissao.CANCELADA;
    }

    // ✅ Sem setMonstroAlvo, setClientePagador — não devem mudar após criação
    // ✅ Lista exposta como imutável — encapsulamento preservado
    public List<String> getRequisitos() { return Collections.unmodifiableList(requisitos); }
    public String getMonstroAlvo() { return monstroAlvo; }
    public String getClientePagador() { return clientePagador; }
    public double getRecompensa() { return recompensa; }
    public NivelDePerigo getNivelDePerigo() { return nivelDePerigo; }
    public StatusDaMissao getStatus() { return status; }
}
