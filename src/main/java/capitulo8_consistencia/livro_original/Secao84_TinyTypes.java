package capitulo8_consistencia.livro_original;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 8, seção 8.4: Tiny Types
// ══════════════════════════════════════════════════════════════════════════════

// ─── Tiny type: Telefone ──────────────────────────────────────────────────────

/**
 * Aniche, p.109-110:
 * "Quantas vezes você já viu CPF ser representado por uma String ou mesmo um
 *  Endereço ser representado por 4 ou 5 strings? Por que não criamos um tipo
 *  particular para representar cada uma dessas pequenas responsabilidades?"
 *
 * "Classes como essa, que representam uma pequena parte do nosso sistema e
 *  praticamente não têm comportamentos, são conhecidas por tiny types."
 */
class Telefone {
    private final String telefone;

    public Telefone(String telefone) {
        // Aniche: "alguma possível validação"
        if (telefone == null || telefone.isBlank())
            throw new IllegalArgumentException("Telefone não pode ser vazio");
        this.telefone = telefone;
    }

    public String get() { return telefone; }

    @Override public String toString() { return telefone; }
}

// ─── Demais tiny types do exemplo do livro ───────────────────────────────────

class Nome {
    private final String valor;
    public Nome(String valor) {
        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException("Nome não pode ser vazio");
        this.valor = valor;
    }
    public String get() { return valor; }
    @Override public String toString() { return valor; }
}

class Email {
    private final String mail;
    public Email(String mail) {
        if (mail == null || mail.isBlank() || !mail.contains("@"))
            throw new IllegalArgumentException("Email inválido: " + mail);
        this.mail = mail.trim().toLowerCase();
    }
    public String get() { return mail; }
    @Override public String toString() { return mail; }
}

class Endereco {
    private final String rua;
    private final String cidade;
    public Endereco(String rua, String cidade) {
        this.rua = rua;
        this.cidade = cidade;
    }

    public String getRua() { return rua; }
    public String getCidade() { return cidade; }
    @Override public String toString() { return rua + ", " + cidade; }
}

// CPF aqui reutiliza o da seção 8.2 (mesmo pacote)

// ─── Aluno SEM tiny types ────────────────────────────────────────────────────

/**
 * ❌ SEM tiny types — Aniche, p.110:
 * "Sem tiny types, você precisa inferir o que significa aquela string.
 *  Além disso, não há possibilidade de passarmos informações em lugares errados
 *  — isso é bastante comum quando temos um método cuja assinatura é um conjunto
 *  de Strings."
 *
 * Uso do livro:
 *   Aluno aluno = new Aluno(
 *       "Mauricio Aniche",
 *       "mauricioaniche@gmail.com",
 *       "Rua Vergueiro",
 *       "São Paulo",
 *       "11 1234-5678",
 *       "12345678901"
 *   );
 */
class AlunoSemTinyTypes {
    private final String nome;
    private final String email;
    private final String rua;
    private final String cidade;
    private final String telefone;
    private final String cpf;

    // ❌ 6 Strings — qualquer uma pode ir para o campo errado sem aviso
    public AlunoSemTinyTypes(String nome, String email, String rua, String cidade, String telefone, String cpf) {
        this.nome = nome;
        this.email = email;
        this.rua = rua;
        this.cidade = cidade;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getRua() { return rua; }
    public String getCidade() { return cidade; }
    public String getTelefone() { return telefone; }
    public String getCpf() { return cpf; }
}

// ─── Aluno COM tiny types ────────────────────────────────────────────────────

/**
 * ✅ COM tiny types — Aniche, p.110:
 * "Com tiny types, o próprio tipo deixa isso claro. Não há a possibilidade
 *  de passarmos informações em lugares errados; isso é bastante comum quando
 *  temos um método cuja assinatura é um conjunto de Strings. Aqui, o primeiro
 *  sistema de tipos documenta o que aquele método recebe."
 *
 * Uso do livro:
 *   Aluno aluno = new Aluno(
 *       new Nome("Mauricio Aniche"),
 *       new Email("mauricioaniche@gmail.com"),
 *       new Endereco("Rua Vergueiro", "São Paulo"),
 *       new Telefone("11 1234-5678"),
 *       new CPF("12345678901")
 *   );
 */
class AlunoComTinyTypes {
    private final Nome nome;
    private final Email email;
    private final Endereco endereco;
    private final Telefone telefone;
    private final CPF cpf;

    // ✅ Cada parâmetro tem tipo específico — inversão não compila
    public AlunoComTinyTypes(Nome nome, Email email, Endereco endereco, Telefone telefone, CPF cpf) {
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public Nome getNome() { return nome; }
    public Email getEmail() { return email; }
    public Endereco getEndereco() { return endereco; }
    public Telefone getTelefone() { return telefone; }
    public CPF getCpf() { return cpf; }

    @Override public String toString() { return nome + " | " + email + " | " + endereco + " | " + telefone; }
}