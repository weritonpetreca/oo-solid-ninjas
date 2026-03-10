package capitulo7_interfaces_magras.v4_repositorio_fabrica;

import java.util.List;

/**
 * Repositório DDD — interface magra para acesso a dados.
 *
 * Aniche, Cap. 7, seção 7.3:
 * Separa a ABSTRAÇÃO (o que o sistema precisa) da INFRAESTRUTURA (como os dados são armazenados).
 *
 * Vantagens práticas:
 *   ✅ Testes: FaturaRepositorioEmMemoria nos testes, FaturaDao em produção
 *   ✅ Acoplamento: o domínio depende dessa interface estável, não do Hibernate
 *   ✅ Flexibilidade: trocar de banco não impacta as regras de negócio
 */
public interface RepositorioDeFaturas {
    List<Fatura> todas();
    void salva(Fatura fatura);
    Fatura buscaPorDescricao(String descricao);
}
