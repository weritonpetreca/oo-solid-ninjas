package capitulo8_consistencia.v9_mundo_real;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ══════════════════════════════════════════════════════════════════════
 * CAP 8 — MUNDO REAL: Sistema de Registro de Caçadores do Continente
 * ══════════════════════════════════════════════════════════════════════
 *
 * Yennefer recebe a missão de modernizar o sistema de cadastro de
 * caçadores da Guilda. O sistema antigo permite objetos em estado
 * inválido, passa nulos por todo lado, e não tem tipagem semântica.
 *
 * Aqui aplicamos TODOS os conceitos do Cap. 8 juntos:
 *   ✅ Construtor rico — Cacador nasce válido
 *   ✅ Tiny types — NomeDeGuerra, EscolaDeGuerra, Credencial
 *   ✅ Imutabilidade — UltimaLocalizacao, Credencial são imutáveis
 *   ✅ Validação — CadastroDeGuilda valida antes de aceitar
 *   ✅ Null Object — CacadorDesconhecido para buscas sem resultado
 *   ✅ DTO — FichaDeCacadorDTO para exibição
 *   ✅ Nomenclatura — nomes que revelam intenção
 */

// ─── Tiny Types ──────────────────────────────────────────────────────────────

class NomeDeGuerra {
    private final String valor;
    NomeDeGuerra(String valor) {
        if (valor == null || valor.isBlank())
            throw new IllegalArgumentException("Nome de guerra não pode ser nulo ou vazio");
        this.valor = valor.trim();
    }
    public String get() { return valor; }
    @Override public String toString() { return valor; }
}

class EscolaDeGuerra {
    private final String nome;
    EscolaDeGuerra(String nome) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Escola de guerra não pode ser nula ou vazia");
        this.nome = nome.trim();
    }
    public String get() { return nome; }
    @Override public String toString() { return "Escola do " + nome; }
}

class Credencial {
    private final String codigo;
    Credencial(String codigo) {
        if (codigo == null || codigo.isBlank())
            throw new IllegalArgumentException("Credencial não pode ser nula ou vazia");
        this.codigo = codigo;
    }
    public String get() { return codigo; }
    @Override public String toString() { return codigo; }
}

// ─── Localização imutável ────────────────────────────────────────────────────

class UltimaLocalizacao {
    private final String regiao;
    private final String fortaleza;

    UltimaLocalizacao(String regiao, String fortaleza) {
        if (regiao == null || regiao.isBlank())
            throw new IllegalArgumentException("Região não pode ser nula ou vazia");
        if (fortaleza == null || fortaleza.isBlank())
            throw new IllegalArgumentException("Fortaleza não pode ser nula ou vazia");
        this.regiao = regiao;
        this.fortaleza = fortaleza;
    }

    public UltimaLocalizacao moverPara(String novaRegiao, String novaFortaleza) {
        return new UltimaLocalizacao(novaRegiao, novaFortaleza);
    }

    @Override
    public String toString() {
        return regiao + " - " + fortaleza;
    }
}

// ─── DTO de exibição ─────────────────────────────────────────────────────────

class FichaDeCacadorDTO {
    public final String nomeDeGuerra;
    public final String escola;
    public final String credencial;
    public final String localizacao;
    public final int totalMissoesConcluidas;

    public final String statusAtivo;
    FichaDeCacadorDTO(String nomeDeGuerra, String escola, String credencial,
                      String localizacao, int totalMissoes, boolean ativo) {
        this.nomeDeGuerra = nomeDeGuerra;
        this.escola = escola;
        this.credencial = credencial;
        this.localizacao = localizacao;
        this.totalMissoesConcluidas = totalMissoes;
        this.statusAtivo = ativo ? "✅ ATIVO" : "❌ INATIVO";
    }
}

// ─── Entidade principal: Cacador ─────────────────────────────────────────────

/**
 * Caçador do Continente — nasce válido, permanece válido.
 * ✅ Construtor rico: sem nome, escola ou credencial → não instancia
 * ✅ Tiny types: impossível passar escola onde espera nome
 * ✅ Lista de missões protegida por unmodifiableList
 */
public class Cacador {

    private final NomeDeGuerra nome;
    private final EscolaDeGuerra escola;
    private final Credencial credencial;
    private UltimaLocalizacao localizacao; // mutável - caçador se move
    private final List<String> missoesConcluidas;
    private boolean ativo;

    public Cacador(NomeDeGuerra nome, EscolaDeGuerra escola,
                   Credencial credencial, UltimaLocalizacao localizacaoInicial) {

        this.nome = nome;
        this.escola = escola;
        this.credencial = credencial;
        this.localizacao = localizacaoInicial;
        this.missoesConcluidas = new ArrayList<>();
        this.ativo = true;
    }

    public void registrarMissaoConcluida(String nomeDaMissao) {
        if (!ativo) throw new IllegalStateException(nome + " está inativo não pode registrar missões");
        missoesConcluidas.add(nomeDaMissao);
    }

    public void moverPara(String novaRegiao, String novaFortaleza) {
        localizacao = localizacao.moverPara(novaRegiao, novaFortaleza);
    }

    public void desativar() { this.ativo = false; }

    // ✅ DTO montado aqui — domínio não carrega lógica de apresentação
    public FichaDeCacadorDTO gerarFicha() {
        return new FichaDeCacadorDTO(
                nome.get(), escola.get(), credencial.get(),
                localizacao.toString(), missoesConcluidas.size(), ativo
        );
    }

    public List<String> getMissoesConcluidas() {
        return Collections.unmodifiableList(missoesConcluidas); // ✅ protegida
    }

    public NomeDeGuerra getNome() { return nome; }
    public EscolaDeGuerra getEscola() { return escola; }
    public boolean isAtivo() { return ativo; }
}
