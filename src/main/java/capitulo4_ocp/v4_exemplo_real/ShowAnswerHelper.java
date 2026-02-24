package capitulo4_ocp.v4_exemplo_real;

/**
 * 🐺 SHOW ANSWER HELPER (VERSÃO REFATORADA — OCP APLICADO)
 *
 * Compare esta versão com {@link ShowAnswerHelperComIfs}.
 *
 * ⚔️ A TRANSFORMAÇÃO:
 *
 * ANTES (com IFs):
 *
 *     if (exercise.getType().equals(Exercise.MULTIPLE_TYPE))
 *         return "/multipleChoiceAnswer";
 *     if (exercise.getType().equals(Exercise.CODE_TYPE) || ...)
 *         return "/openAnswer";
 *     // E quando criar um NOVO TIPO? Adicionar mais IFs aqui.
 *
 *
 * DEPOIS (com polimorfismo):
 *
 *     return exercise.viewDetails().getUrl(); // ZERO IFS
 *
 *
 * 🛡️ O QUE MUDOU ARQUITETURALMENTE:
 *
 * 1. OCP garantido: Para adicionar "ExercicioDeVídeo" ou "Quiz Flash",
 *    basta criar a subclasse de {@link Exercise} com seu próprio {@link ExerciseViewDetails}.
 *    Este Helper NUNCA precisará ser modificado.
 *
 * 2. Compilador como aliado: O novo tipo NÃO COMPILA sem implementar
 *    {@code viewDetails()}. Impossível esquecer.
 *
 * 3. Complexidade ciclomática = 1: Zero IFs, zero caminhos alternativos.
 *    Fácil de testar, fácil de ler.
 *
 * ⚔️ CITAÇÃO DO LIVRO:
 * "Um bom projeto de classes é aquele que deixa claro qual o caminho a seguir."
 *
 * Esta classe deixa claro: "Para exibir um exercício, peça ao exercício
 * como ele quer ser exibido." Simples. Definitivo.
 */

public class ShowAnswerHelper {

    /**
     * ✅ ZERO IFS. O polimorfismo faz todo o trabalho.
     *
     * A JSP pode chamar este método para qualquer tipo de exercício.
     * Não importa se é múltipla escolha, código ou vídeo.
     * O resultado correto sempre será retornado.
     */

    public String getUrlFor(Exercise exercise) { return exercise.viewDetails().getUrl(); }

    public boolean isMultipleChoice(Exercise exercise) { return exercise.viewDetails().isMultipleChoice(); }

    public boolean isOpenAnswer(Exercise exercise) { return exercise.viewDetails().isOpenAnswer(); }

    /**
     * 🧙 PONTO DE REFLEXÃO:
     * Repare que não há um método "isNoAnswer" aqui.
     * A JSP pode simplesmente verificar: "Se não é múltipla escolha
     * e não é resposta aberta, então é sem resposta."
     * Ou podemos adicionar isNoAnswer() sem problema — a questão é:
     * este método NÃO PRECISARÁ de IF para funcionar.
     */

    public boolean isNoAnswer(Exercise exercise) {
        return !exercise.viewDetails().isMultipleChoice()
                && !exercise.viewDetails().isOpenAnswer();
    }
}
