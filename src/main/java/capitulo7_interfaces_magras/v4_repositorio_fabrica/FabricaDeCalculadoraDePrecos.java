package capitulo7_interfaces_magras.v4_repositorio_fabrica;

/**
 * Fábrica — Aniche, Cap. 7, seção 7.4.
 *
 * Responsabilidade única: criar CalculadoraDePrecos corretamente montada.
 *
 * Aniche sobre fábricas:
 *   "Ela é altamente acoplada, mas isso é menos problemático:
 *    - Fábricas são classes estáveis
 *    - Não têm regras de negócio
 *    - São decisões claras de design"
 *
 * ✅ Centraliza a montagem das dependências
 * ✅ Qualquer troca de implementação acontece aqui — um lugar só
 * ✅ Sem framework necessário — solução OO pura
 */
public class FabricaDeCalculadoraDePrecos {
    public CalculadoraDePrecos constroi() {
        TabelaDePreco tabela = new TabelaDePrecosPadrao();
        ServicoDeEntrega entrega = new Correios();
        return new CalculadoraDePrecos(tabela, entrega);
    }
}
