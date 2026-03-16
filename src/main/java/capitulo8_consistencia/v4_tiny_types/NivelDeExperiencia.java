package capitulo8_consistencia.v4_tiny_types;

/** Tiny type: nível de experiência (1–100). Garante o intervalo válido. */
public class NivelDeExperiencia {
    private final int valor;

    public NivelDeExperiencia(int valor) {
        if (valor < 1 || valor > 100)
            throw new IllegalArgumentException("Nível de experiência deve estar entre 1 e 100");
        this.valor = valor;
    }

    public int get() { return valor; }
    public boolean isVeterano() { return valor >= 70; }
    public boolean isLendario() { return valor >= 90; }

    @Override public String toString() { return String.valueOf(valor); }
}
