package capitulo4_ocp.v4_exemplo_real;

/**
 * 🎯 RESPOSTA ABERTA (EXERCISE CONCRETO)
 *
 * ✅ Código ou texto aberto — o aluno digita a resposta.
 */

public class OpenAnswerExercise extends Exercise {

    private final String courseCode;
    private final int sectionNumber;
    private final int exerciseNumber;

    public OpenAnswerExercise(String courseCode, int sectionNumber, int exerciseNumber) {
        this.courseCode = courseCode;
        this.sectionNumber = sectionNumber;
        this.exerciseNumber = exerciseNumber;

    }

    @Override
    public ExerciseViewDetails viewDetails() {
        return new OpenAnswerViewDetails(courseCode, sectionNumber, exerciseNumber);
    }
}
