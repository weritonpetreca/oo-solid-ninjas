package capitulo8_consistencia.livro_original;

import java.util.Calendar;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 8, seção 8.5: DTOs do Bem
// ══════════════════════════════════════════════════════════════════════════════

/**
 * DadosDoUsuarioDTO — exemplo direto do livro.
 *
 * Aniche, p.112:
 * "Por que não criar uma classe que represente o que seu usuário realmente
 *  precisa ver naquela tela, criando por exemplo a classe DadosDoUsuarioDTO,
 *  em vez de ficar passando conjuntos de inteiros e strings separados e deixar
 *  a JSP mais complicada e menos semântica?"
 *
 * "Não tenha medo de criar DTOs que representem pedaços do seu sistema.
 *  Facilite a transferência de dados entre suas camadas; aumente a semântica
 *  deles. Lembre-se que o problema não é ter DTOs, mas sim só ter DTOs."
 */
class DadosDoUsuarioDTO {

    private final String nome;
    private final Calendar ultimoAcesso;
    private final int qtdDeTentativas;

    public DadosDoUsuarioDTO(String nome, Calendar ultimoAcesso, int qtdDeTentativas) {
        this.nome = nome;
        this.ultimoAcesso = ultimoAcesso;
        this.qtdDeTentativas = qtdDeTentativas;
    }

    public String getNome() { return nome; }
    public Calendar getUltimoAcesso() { return ultimoAcesso; }
    public int getQtdDeTentativas() { return qtdDeTentativas; }
}
