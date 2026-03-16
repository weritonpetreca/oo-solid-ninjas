package capitulo8_consistencia.v1_construtor_rico;

import java.util.ArrayList;
import java.util.List;

/**
 * PROBLEMA v1: Missão sem construtor rico — estado inválido desde o nascimento.
 *
 * Aniche, Cap. 8, seção 8.1:
 * "Objetos em estado inválido são bastante problemáticos. Você não sabe o que
 *  esperar daquele objeto."
 *
 * Uma missão no Continente precisa obrigatoriamente de:
 *   - Um monstro alvo (você não caça o quê?)
 *   - Um valor de recompensa (trabalho gratuito não existe)
 *   - Um cliente pagador (a quem cobrar?)
 *
 * Com construtor padrão, qualquer um desses pode estar nulo.
 * E o sistema só explode quando tentar calcular, persistir ou exibir.
 *
 * ❌ Problema: objeto nasce em estado potencialmente inválido.
 */
public class MissaoSemConstrutorRico {

    private String monstroAlvo;         // pode ficar nulo — missão sem alvo!
    private double recompensa;          // pode ficar 0.0 — trabalho de graça!
    private String clientePagador;      // pode ficar nulo — quem paga?
    private List<String> requisitos;

    // ❌ Construtor padrão permite criar missão incompleta
    public MissaoSemConstrutorRico() { this.requisitos = new ArrayList<>(); }

    public void adicionarRequisitos(String req) { requisitos.add(req); }

    public double calcularTaxaGuilda() {
        // ❌ Se recompensa == 0.0 (esqueceram de setar), resultado é 0
        // ❌ Se monstroAlvo == null, operações de log explodem
        return recompensa * 0.15;
    }

    public String gerarDescricao() {
        // ❌ NullPointerException se monstroAlvo ou clientePagador forem nulos
        return "Caça ao " + monstroAlvo.toUpperCase() + " - pago por " + clientePagador;
    }

    public void setMonstroAlvo(String mosntroAlvo) { this.monstroAlvo = mosntroAlvo; }
    public void setRecompensa(double recompensa) { this.recompensa = recompensa; }
    public void setClientePagador(String clientePagador) { this.clientePagador = clientePagador; }

    public String getMonstroAlvo() { return monstroAlvo; }
    public double getRecompensa() { return recompensa; }
    public String getClientePagador() { return clientePagador; }

}
