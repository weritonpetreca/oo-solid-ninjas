package capitulo4_ocp.v4_exemplo_real;

public abstract class Exercise {

    private Long id;
    private String question;

    /**
     * 🎭 A PORTA PARA OS DETALHES DE VISUALIZAÇÃO.
     *
     * Este método abstrato OBRIGA cada subclasse a fornecer
     * seus próprios detalhes de visualização.
     *
     * ✅ VANTAGEM 1 — OCP:
     * Novos tipos de exercício nunca exigirão modificar ShowAnswerHelper.
     * Basta criar a nova classe e implementar este método.
     *
     * ✅ VANTAGEM 2 — O compilador trabalha por você:
     * Se alguém criar um novo tipo de exercício e esquecer de implementar
     * getViewDetails(), o código NÃO COMPILA. Erro em tempo de compilação,
     * não em produção.
     *
     * ✅ VANTAGEM 3 — SRP melhorado:
     * A lógica de VISUALIZAÇÃO fica em {@link ExerciseViewDetails},
     * separada da lógica de DOMÍNIO do Exercise.
     */

    public abstract ExerciseViewDetails viewDetails();

    public Long getId() { return id; }
    public String getQuestion() { return question; }
}
