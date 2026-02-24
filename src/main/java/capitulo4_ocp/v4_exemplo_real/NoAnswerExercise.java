package capitulo4_ocp.v4_exemplo_real;

/**
 * 🎯 SEM RESPOSTA (EXERCISE CONCRETO)
 *
 * ✅ Exercícios que são apenas para leitura, sem interação do aluno.
 */

public class NoAnswerExercise extends Exercise {

    private final String courseCode;
    private final int sectionNumber;
    private final int exerciseNumber;

    public NoAnswerExercise(String courseCode, int sectionNumber, int exerciseNumber) {
        this.courseCode = courseCode;
        this.sectionNumber = sectionNumber;
        this.exerciseNumber = exerciseNumber;
    }

    @Override
    public ExerciseViewDetails viewDetails() {
        return new NoAnswerViewDetails(courseCode, sectionNumber, exerciseNumber);
    }
}
