package capitulo8_consistencia.v4_tiny_types;

/** Tiny type: e-mail de contato do caçador. Valida o formato. */
public class EmailDeCacador {
    private final String endereco;

    public EmailDeCacador(String endereco) {
        if (endereco == null || !endereco.contains("@"))
            throw new IllegalArgumentException("E-mail inválido: " + endereco);
        this.endereco = endereco.trim().toLowerCase();
    }

    public String get() { return endereco; }

    @Override public String toString() { return endereco; }
}
