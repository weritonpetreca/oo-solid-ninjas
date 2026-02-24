package capitulo4_ocp.v4_exemplo_real;

// =============================================================================
// 🔴 PARTE 1: O CÓDIGO ORIGINAL COM PROBLEMAS (FIEL AO LIVRO)
//             Aqui documentamos como era ANTES da refatoração.
// =============================================================================

/**
 * 🚨 SHOW ANSWER HELPER (VERSÃO PROBLEMÁTICA — FIEL AO LIVRO)
 *
 * Código extraído de um commit real da Caelum por Maurício Aniche.
 * Representa o PROBLEMA clássico de ausência do OCP.
 *
 * ⚔️ OS PROBLEMAS DESTA CLASSE:
 *
 * 1. TODA VEZ que um novo tipo de exercício for criado,
 *    ESTA CLASSE precisa ser modificada (novo IF). Viola OCP.
 *
 * 2. A JSP também tem os mesmos IFs. Isso significa que há
 *    DOIS LUGARES para alterar a cada novo exercício.
 *    Como saber se não há um terceiro? Ou quarto?
 *
 * 3. Se o desenvolvedor criar um novo tipo e esquecer o IF aqui,
 *    o sistema "quebra silenciosamente" (retorna URL errada).
 *    O compilador não avisa. Só descobrimos em produção.
 *
 * ⚔️ CITAÇÃO DO LIVRO:
 * "Repare que são os dois que apareceram aqui no livro.
 * Como saber se não há mais pontos para mudar?"
 */

public class ShowAnswerHelperComIfs {

    // Constantes representando os tipos de exercício
    public static final String MULTIPLE_TYPE = "MULTIPLE";
    public static final String CODE_TYPE = "CODE";
    public static final String OPEN_TYPE = "OPEN";
    public static final String NO_ANSWER = "NO_ANSWER";

    /**
     * 🚨 MÉTODO PROBLEMÁTICO — CHEIO DE IFS
     * Cada novo tipo de Exercise exige adicionar um IF aqui.
     */
    public String getUrlFor(ExercicioLegado exercise) {
        if (exercise.getType().equals(MULTIPLE_TYPE)) {
            return "/courses/" + exercise.getCourse().getCode()
                    + "/sections/" + exercise.getSection().getNumber()
                    + "/exercises/" + exercise.getNumber()
                    + "/multipleChoiceAnswer";
        }

        if (exercise.getType().equals(CODE_TYPE)
                || exercise.getType().equals(OPEN_TYPE)) {
            return "/courses/" + exercise.getCourse().getCode()
                    + "/sections/" + exercise.getSection().getNumber()
                    + "/exercises/" + exercise.getNumber()
                    + "/openAnswer";
        }

        return "/courses/" + exercise.getCourse().getCode()
                + "/sections/" + exercise.getSection().getNumber()
                + "/exercises/" + exercise.getNumber()
                + "/noAnswer";

    }

    public boolean isMultipleChouce(ExercicioLegado exercise) { return exercise.getType().equals(MULTIPLE_TYPE); }

    public boolean isOpenAnswer(ExercicioLegado exercise) {
        return exercise.getType().equals(OPEN_TYPE)
                || exercise.getType().equals(CODE_TYPE);
    }

    public boolean isNoAnswer(ExercicioLegado exercise) { return exercise.getType().equals(NO_ANSWER); }
}
