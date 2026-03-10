package capitulo7_interfaces_magras.v4_repositorio_fabrica;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação de produção do repositório.
 * Em um sistema real: usaria Hibernate, JDBC ou JPA.
 * Aqui: em memória para fins didáticos.
 */
public class FaturaDao implements RepositorioDeFaturas {
    // Simulando banco de dados em memória
    private final List<Fatura> banco = new ArrayList<>();

    @Override
    public List<Fatura> todas() {
        return List.copyOf(banco);
    }

    @Override
    public void salva(Fatura fatura) {
        banco.add(fatura);
    }

    @Override
    public Fatura buscaPorDescricao(String descricao) {
        return banco.stream()
                .filter(f -> f.getDescricao().equals(descricao))
                .findFirst()
                .orElse(null);
    }
}
