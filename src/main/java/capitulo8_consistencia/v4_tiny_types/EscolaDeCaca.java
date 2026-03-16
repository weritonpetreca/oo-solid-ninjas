package capitulo8_consistencia.v4_tiny_types;

/** Tiny type: escola de combate do caçador. */
public class EscolaDeCaca {
    private final String nome;

    public EscolaDeCaca(String nome) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Escola de caça não pode ser nula nem vazia");
        this.nome = nome;
    }

    public String get() { return nome; }

    @Override public String toString() { return nome; }
}
