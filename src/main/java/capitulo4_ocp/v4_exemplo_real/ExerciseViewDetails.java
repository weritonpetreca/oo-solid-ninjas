package capitulo4_ocp.v4_exemplo_real;

/**
 * 🎭 EXERCISE VIEW DETAILS (A ABSTRAÇÃO DE VISUALIZAÇÃO)
 *
 * Interface que encapsula tudo que a camada de visualização
 * precisa saber sobre um exercício.
 *
 * ⚔️ A LIÇÃO DO LIVRO:
 * "Mas será que vale a pena colocar um conceito de visualização dentro
 * de uma entidade de negócio? Isso não seria uma quebra do SRP?"
 *
 * A resposta de Aniche: "Sem dúvida, é. Mas às vezes não temos como
 * fugir 100% disso. Uma boa solução é criar uma interface separada
 * e fazer o Exercise devolvê-la."
 *
 * 🛡️ O COMPROMISSO:
 * - A entidade {@link Exercise} SABE que existe uma interface de visualização.
 * - A entidade NÃO CONHECE os detalhes de implementação dessa visualização.
 * - Isso minimiza o acoplamento entre domínio e apresentação.
 *
 * 💡 BÔNUS — OPEN/CLOSED PRINCIPLE APLICADO:
 * A JSP/Template pode invocar {@code viewDetails().getUrl()} sem saber
 * qual tipo de exercício está sendo renderizado. Zero IFs.
 */

public interface ExerciseViewDetails {

    /**
     * @return A URL da página que deve ser renderizada para este tipo de exercício.
     */

    String getUrl();

    /**
     * @return true se o exercício exibe opções para o aluno escolher.
     */

    boolean isMultipleChoice();

    /**
     * @return true se o exercício espera uma resposta aberta ou código.
     */

    boolean isOpenAnswer();
}
