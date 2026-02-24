package capitulo4_ocp.v4_exemplo_real;

// =============================================================================
// 🟢 PARTE 2: A SOLUÇÃO REFATORADA (OCP APLICADO)
// =============================================================================

/**
     * 🎯 DETALHES DE VISUALIZAÇÃO — MÚLTIPLA ESCOLHA
     *
     * Contém APENAS o conhecimento de como renderizar um exercício de múltipla escolha.
     * Isolado e coeso.
     */

class MultipleChoiceViewDetails implements ExerciseViewDetails {

    private final String courseCode;
    private final int sectionNumber;
    private final int exerciseNumber;

    public MultipleChoiceViewDetails(String courseCode, int sectionNumber, int exerciseNumber) {
        this.courseCode = courseCode;
        this.sectionNumber = sectionNumber;
        this.exerciseNumber = exerciseNumber;
    }

    @Override
    public String getUrl() {
        return "/courses/" + courseCode
                + "/sections/" + sectionNumber
                + "/exercises/" + exerciseNumber
                + "/multipleChoiceAnswer";
    }

    @Override
    public boolean isMultipleChoice() { return true; }

    @Override
    public boolean isOpenAnswer() { return false; }
}

/**
 * 🎯 DETALHES DE VISUALIZAÇÃO — RESPOSTA ABERTA
 */

class OpenAnswerViewDetails implements ExerciseViewDetails {

    private final String courseCode;
    private final int sectionNumber;
    private final int exerciseNumber;

    public OpenAnswerViewDetails(String courseCode, int sectionNumber, int exerciseNumber) {
        this.courseCode = courseCode;
        this.sectionNumber = sectionNumber;
        this.exerciseNumber = exerciseNumber;
    }

    @Override
    public String getUrl() {
        return "/courses/" + courseCode
                + "/sections/" + sectionNumber
                + "/exercises/" + exerciseNumber
                + "/openAnswer";
    }

    @Override
    public boolean isMultipleChoice() { return false; }

    @Override
    public boolean isOpenAnswer() { return true; }
}

/**
 * 🎯 DETALHES DE VISUALIZAÇÃO — SEM RESPOSTA
 */

class NoAnswerViewDetails implements ExerciseViewDetails {

    private final String courseCode;
    private final int sectionNumber;
    private final int exerciseNumber;

    public NoAnswerViewDetails(String courseCode, int sectionNumber, int exerciseNumber) {
        this.courseCode = courseCode;
        this.sectionNumber = sectionNumber;
        this.exerciseNumber = exerciseNumber;
    }

    @Override
    public String getUrl() {
        return "/courses/" + courseCode
                + "/sections/" + sectionNumber
                + "/exercises/" + exerciseNumber
                + "/noAnswer";
    }

    @Override
    public boolean isMultipleChoice() { return false; }

    @Override
    public boolean isOpenAnswer() { return false; }
}

// =============================================================================
// 🏗️ INFRAESTRUTURA DE SUPORTE (Classes auxiliares para o exemplo legado)
// =============================================================================

class CourseInfo {
    private String code;
    public CourseInfo(String code) { this.code = code; }
    public String getCode() { return code; }
}

class SectionInfo {
    private int number;
    public SectionInfo(int number) { this.number = number; }
    public int getNumber() { return number; }
}