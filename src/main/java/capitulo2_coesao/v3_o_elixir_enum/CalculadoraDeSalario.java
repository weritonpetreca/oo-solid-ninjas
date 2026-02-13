package capitulo2_coesao.v3_o_elixir_enum;

/**
 * 🐺 GERALT (VERSÃO DEFINITIVA)
 *
 * Esta classe atingiu o nirvana da Orientação a Objetos.
 *
 * ⚔️ ANÁLISE TÉCNICA:
 * - Complexidade Ciclomática: 1 (Não há IFs).
 * - Acoplamento: Mínimo (Só depende da interface e do modelo).
 * - Coesão: Máxima (Só faz uma coisa: delega).
 *
 * "O código perfeito não é aquele que não tem mais nada a acrescentar,
 * mas aquele que não tem mais nada a retirar."
 */
public class CalculadoraDeSalario {

    public double calcula(Funcionario funcionario) {
        // A mágica acontece aqui: Polimorfismo no Enum.
        return funcionario.getCargo().getRegra().calcula(funcionario);
    }
}