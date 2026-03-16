package capitulo8_consistencia.v4_tiny_types;

// ══════════════════════════════════════════════════════════════════════════════
// TINY TYPES — Aniche, Cap. 8, seção 8.4
//
// "Por que não criamos um tipo particular para representar cada uma dessas
//  pequenas responsabilidades que aparecem em nossos sistemas?"
//
// Sem tiny types, o construtor de Cacador aceita qualquer String em qualquer
// posição — o compilador não ajuda:
//
//   ❌ new Cacador("Escola do Lobo", "Geralt", "geralt@lobo.com", 25)
//      ↑ nome e escola invertidos — sem aviso do compilador
//
// Com tiny types, cada parâmetro tem um tipo próprio:
//
//   ✅ new Cacador(new NomeDeCacador("Geralt"), new EscolaDeCaca("Lobo"),
//                 new EmailDeCacador("geralt@lobo.com"), new NivelDeExperiencia(25))
//      ↑ trocar NomeDeCacador por EscolaDeCaca não compila
// ══════════════════════════════════════════════════════════════════════════════


/** Tiny type: nome do caçador. Garante que não é vazio. */
public class NomeDeGuerra {
    private final String valor;

    public NomeDeGuerra(String valor) {
        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException("Nome do caçador não pode ser nulo nem vazio");
        this.valor = valor.trim();
    }

    public String get() { return  valor; }

    @Override public String toString() { return valor; }
}
