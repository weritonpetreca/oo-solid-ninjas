package capitulo4_ocp.v4_exemplo_real;

/**
 * Representa um exercício no sistema legado (antes da refatoração).
 * Usa um campo String "type" ao invés de polimorfismo.
 */
public class ExercicioLegado {
    private String type;
    private CourseInfo course;
    private SectionInfo section;
    private int number;

    public ExercicioLegado(String type, String courseCode, int sectionNumber, int number) {
        this.type = type;
        this.course = new CourseInfo(courseCode);
        this.section = new SectionInfo(sectionNumber);
        this.number = number;
    }

    public String getType() { return type; }
    public CourseInfo getCourse() { return course; }
    public SectionInfo getSection() { return section; }
    public int getNumber() { return number; }
}
