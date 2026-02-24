package capitulo4_ocp.v4_exemplo_real;

/**
 * 🎯 MÚLTIPLA ESCOLHA (EXERCISE CONCRETO)
 *
 * ✅ Implementa {@link Exercise} e é obrigado pelo compilador
 * a fornecer seus detalhes de visualização.
 *
 * ✅ Qualquer nova feature de visualização para múltipla escolha
 * fica encapsulada aqui, isolada das outras.
 */

public class MultipleChoiceExercise extends Exercise {

    private final String courseCode;
    private final int sectionNumber;
    private final int exerciseNumber;

    public MultipleChoiceExercise(String courseCode, int sectionNumber, int exerciseNumber) {
        this.courseCode = courseCode;
        this.sectionNumber = sectionNumber;
        this.exerciseNumber = exerciseNumber;
    }

    @Override
    public ExerciseViewDetails viewDetails() {
        // Retorna a implementação específica para múltipa escolha
        return new MultipleChoiceViewDetails(courseCode, sectionNumber, exerciseNumber);
    }
}
