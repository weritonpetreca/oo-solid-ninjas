package capitulo8_consistencia.v4_tiny_types;

/**
 * PROBLEMA: Cacador sem tiny types.
 *
 * ❌ O compilador não impede de passar os argumentos na ordem errada.
 * ❌ "Geralt" pode ir para o campo escola — sem aviso nenhum.
 */
public class CacadorSemTinyTypes {
    private final String nome;
    private final String escola;
    private final String email;
    private final int nivel;

    // ❌ Três parâmetros do mesmo tipo — fácil de trocar sem perceber
    public CacadorSemTinyTypes(String nome, String escola, String email, int nivel) {
        this.nome = nome;
        this.escola = escola;
        this.email = email;
        this.nivel = nivel;
    }

    public String getNome() { return nome; }
    public String getEscola() { return escola; }
    public String getEmail() { return email; }
    public int getNivel() { return nivel; }
}
