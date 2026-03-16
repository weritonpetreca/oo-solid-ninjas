package capitulo8_consistencia.v6_imutabilidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Registro de rastro de um monstro — demonstra encapsulamento de lista.
 *
 * Aniche, Cap. 8, seção 8.6:
 * "O problema é que, como o getter devolve a lista original, qualquer
 *  classe pode colocar novos itens nessa lista, sem passar pelas regras."
 *
 * ❌ getLocalizacoes() retornando a lista interna permite que qualquer
 *    código externo adicione ou remova localizações sem controle.
 *
 * ✅ Retornar Collections.unmodifiableList() protege o estado interno.
 * ✅ Quem quiser adicionar usa o método adicionarAvistamento() — com regras.
 */
public class RegistroDeRastro {

    private final String nomeMonstro;
    private final List<LocalizacaoImutavel> avistamentos;

    public RegistroDeRastro(String nomeMonstro) {
        this.nomeMonstro = nomeMonstro;
        this.avistamentos = new ArrayList<>();
    }

    public void adicionarAvistamento(LocalizacaoImutavel local) {
        // ✅ Regra encapsulada: a lista só cresce por aqui
        if (local == null) throw new IllegalArgumentException("Localização não pode ser nula");
        avistamentos.add(local);
    }

    public LocalizacaoImutavel ultimoAvistamento() {
        if (avistamentos.isEmpty())
            throw new IllegalStateException("Nenhum avistamento registrado para: " + nomeMonstro);
        return avistamentos.get(avistamentos.size() - 1);
    }

    // ✅ Lista protegida — código externo não pode modificá-la diretamente
    public List<LocalizacaoImutavel> getAvistamentos() { return Collections.unmodifiableList(avistamentos); }

    public String getNomeMonstro() { return nomeMonstro; }
    public int totalAvistamentos() { return avistamentos.size(); }
}
