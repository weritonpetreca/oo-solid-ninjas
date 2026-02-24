package capitulo4_ocp;

import capitulo4_ocp.v4_exemplo_real.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 🧪 TESTES DO SISTEMA DE EXERCÍCIOS — OCP EXEMPLO REAL
 *
 * Demonstra que o ShowAnswerHelper refatorado funciona corretamente
 * para todos os tipos de exercício — e que novos tipos não quebram nada.
 *
 * ⚔️ A LIÇÃO PRINCIPAL:
 * Se um novo tipo de exercício (ex: ExercicioDeVideo) for criado:
 * - ✅ ShowAnswerHelper: NÃO MUDA
 * - ✅ Estes testes: NÃO QUEBRAM
 * - ✅ Compilador avisa se esquecer de implementar viewDetails()
 *
 * Isso é o OCP em ação no mundo real.
 */

@DisplayName("📚 Testes do Sistema de Exercícios (Exemplo Real Caelum)")
class ExerciseOCPTest {

    private final ShowAnswerHelper helper = new ShowAnswerHelper();

    @Test
    @DisplayName("Múltipla escolha deve retornar URL correta")
    void multiplaEscolhaDeveRetornarUrlCorreta() {

        Exercise exercicio = new MultipleChoiceExercise("java-oo", 3, 7);

        String url = helper.getUrlFor(exercicio);

        assertEquals("/courses/java-oo/sections/3/exercises/7/multipleChoiceAnswer", url);
    }

    @Test
    @DisplayName("Resposta aberta deve retornar URL correta")
    void respostaAbertaDeveRetornarUrlCorreta() {

        Exercise exercicio = new OpenAnswerExercise("java-oo", 5, 2);

        String url = helper.getUrlFor(exercicio);

        assertEquals("/courses/java-oo/sections/5/exercises/2/openAnswer", url);
    }

    @Test
    @DisplayName("Sem resposta deve retornar URL correta")
    void semRespostaDeveRetornarUrlCorreta() {

        Exercise exercicio = new NoAnswerExercise("java-oo", 6, 1);

        String url = helper.getUrlFor(exercicio);

        assertEquals("/courses/java-oo/sections/6/exercises/1/noAnswer", url);
    }

    @Test
    @DisplayName("isMultipleChoice deve ser verdadeiro apenas para múltipla escolha")
    void isMultipleChoiceDeveSerCorreto() {

        Exercise multipla = new MultipleChoiceExercise("curso", 1, 1);
        Exercise aberta = new OpenAnswerExercise("curso", 1, 2);
        Exercise semResposta = new NoAnswerExercise("curso", 1, 3);

        assertTrue(helper.isMultipleChoice(multipla));
        assertFalse(helper.isMultipleChoice(aberta));
        assertFalse(helper.isMultipleChoice(semResposta));
    }

    @Test
    @DisplayName("isOpenAnswer deve ser verdadeiro apenas para resposta aberta")
    void isOpenAnswerDeveSerCorreto() {

        Exercise multipla = new MultipleChoiceExercise("curso", 1, 1);
        Exercise aberta = new OpenAnswerExercise("curso", 1, 2);
        Exercise semResposta = new NoAnswerExercise("curso", 1, 3);

        assertFalse(helper.isOpenAnswer(multipla));
        assertTrue(helper.isOpenAnswer(aberta));
        assertFalse(helper.isOpenAnswer(semResposta));
    }

    @Test
    @DisplayName("isNoAnswer deve ser verdadeiro apenas para sem resposta")
    void isNoAnswerDeveSerCorreto() {

        Exercise multipla = new MultipleChoiceExercise("curso", 1, 1);
        Exercise aberta = new OpenAnswerExercise("curso", 1, 2);
        Exercise semResposta = new NoAnswerExercise("curso", 1, 3);

        assertFalse(helper.isNoAnswer(multipla));
        assertFalse(helper.isNoAnswer(aberta));
        assertTrue(helper.isNoAnswer(semResposta));
    }

    /**
     * 🧙 TESTE QUE DEMONSTRA O OCP:
     *
     * Simulamos a criação de um "novo tipo de exercício" DEPOIS
     * que o sistema já foi construído.
     *
     * Sem modificar ShowAnswerHelper, criamos um ExercicioDeVideo
     * e ele funciona perfeitamente. Isso é o OCP.
     */

    @Test
    @DisplayName("OCP: Novo tipo de exercício funciona sem modificar ShowAnswerHelper")
    void novoTipoDeExercicioFuncionaSemModificarHelper() {

        // SIMULANDO: Um novo desenvolvedor cria ExercicioDeVideo
        // Ele implementa Exercise, e é obrigado a criar viewDetails()
        Exercise exercicioDeVideo = new Exercise() {
            @Override
            public ExerciseViewDetails viewDetails() {
                return new ExerciseViewDetails() {
                    @Override
                    public String getUrl() {
                        return "/courses/oo-java/sections/1/exercises/1/videoAnswer"; }

                    @Override
                    public boolean isMultipleChoice() {
                        return false; }

                    @Override
                    public boolean isOpenAnswer() {
                        return false; }
                };
            }
        };

        // ShowAnswerHelper FUNCIONA sem modificações
        String url = helper.getUrlFor(exercicioDeVideo);

        assertEquals("/courses/oo-java/sections/1/exercises/1/videoAnswer", url);
        assertFalse(helper.isMultipleChoice(exercicioDeVideo));
        assertFalse(helper.isOpenAnswer(exercicioDeVideo));
        assertTrue(helper.isNoAnswer(exercicioDeVideo));
    }
}
