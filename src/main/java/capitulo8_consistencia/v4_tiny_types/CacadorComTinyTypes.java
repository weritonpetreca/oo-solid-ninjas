package capitulo8_consistencia.v4_tiny_types;

/**
 * SOLUÇÃO: Cacador com tiny types.
 *
 * Aniche, Cap. 8, seção 8.4:
 * "Com tiny types, o próprio tipo deixa claro o que aquele método recebe.
 *  Não há a possibilidade de passarmos informações em lugares errados."
 *
 * ✅ Trocar NomeDeCacador por EscolaDeCaca não compila
 * ✅ Cada tipo carrega sua própria validação
 * ✅ O construtor é documentação viva do que o objeto precisa
 */
public class CacadorComTinyTypes {
    private final NomeDeGuerra nome;
    private final EscolaDeCaca escola;
    private final EmailDeCacador email;
    private final NivelDeExperiencia nivel;

    // ✅ Impossível passar email onde espera nome — tipos diferentes
    public CacadorComTinyTypes(NomeDeGuerra nome, EscolaDeCaca escola, EmailDeCacador email, NivelDeExperiencia nivel) {
        this.nome = nome;
        this.escola = escola;
        this.email = email;
        this.nivel = nivel;
    }

    public boolean isVeterano() { return nivel.isVeterano(); }
    public boolean isLendario() { return nivel.isLendario(); }

    public NomeDeGuerra getNome() { return nome; }
    public EscolaDeCaca getEscola() { return escola; }
    public EmailDeCacador getEmail() { return email; }
    public NivelDeExperiencia getNivel() { return nivel; }

    @Override
    public String toString() {
        return String.format("%s | Escola: %s | Nível: %s%s",
                nome, escola, nivel,
                isLendario() ? " ⭐ LENDÁRIO" : isVeterano() ? " ✦ Veterano" : "");
    }
}
