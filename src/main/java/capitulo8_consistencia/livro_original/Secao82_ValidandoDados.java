package capitulo8_consistencia.livro_original;

// ══════════════════════════════════════════════════════════════════════════════
// LIVRO ORIGINAL — Aniche, Cap. 8, seção 8.2: Validando Dados
// ══════════════════════════════════════════════════════════════════════════════

// ─── Abordagem 1: Validação no construtor ────────────────────────────────────

import java.util.ArrayList;
import java.util.List;

/**
 * ✅ ABORDAGEM 1 — CPF valida no construtor.
 *
 * Aniche, p.105:
 * "Podemos, por exemplo, tratar os dados dentro da própria entidade.
 *  A própria classe CPF poderia ser então responsável por esse tipo de
 *  validação. Veja onde o construtor da classe se responsabiliza por isso."
 *
 * Vantagem: garante que o objeto nunca terá valor inválido.
 * Desvantagem: muitas pessoas não gostam de exceção disparada no construtor.
 */
class CPF {
    private final String cpf;

    public CPF(String possivelCpf) {
        if (possivelCpf == null || !possivelCpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido: " + possivelCpf);
        }
        this.cpf = possivelCpf;
    }

    public String get() { return cpf; }

    @Override
    public String toString() { return cpf; }
}

// ─── Abordagem 2: Método valida() ────────────────────────────────────────────

/**
 * ABORDAGEM 2 — CPF com método valida() separado.
 *
 * Aniche, p.106:
 * "Outra abordagem é você prover um método que diz se o objeto é válido ou
 *  não. Nesse caso, você, como projetista da classe, permite que o usuário
 *  instancie uma classe com um CPF inválido."
 *
 * Permite instanciar com valor inválido e verificar depois.
 */
class CPFComValidacao {
    private final String cpf;

    public CPFComValidacao(String cpf) { this.cpf = cpf; }

    /**
     * Aniche, p.106:
     * "O método pode optar por lançar exceção ou mesmo retornar uma lista de
     *  erros de validação."
     */
    public boolean valida() {
        if (cpf == null) return false;
        return cpf.matches("\\d{11}");
    }

    public String get() { return cpf; }
}

// ─── Abordagem 3: Builder ────────────────────────────────────────────────────

/**
 * ✅ ABORDAGEM 3 — CPFBuilder valida antes de criar.
 *
 * Aniche, p.106-107:
 * "Uma implementação intermediária seria a implementação de um builder para a
 *  classe CPF. Esse builder, por sua vez, seria responsável por validar o CPF
 *  passado e retornar o erro para o usuário. A vantagem do builder é que você
 *  garante que a classe CPF não será criada com um valor inválido, e ao mesmo
 *  tempo, não coloca o código de validação dentro da própria classe de domínio."
 */
class CPFBuilder {
    public CPF build(String cpf) {
        // Aniche escreve "if(regrasOk) return new CPF(cpf);"
        if (cpf != null && cpf.matches("\\d{11}")) {
            return new CPF(cpf);
        }
        throw new IllegalArgumentException("CPF Inválido: " + cpf);
    }
}

// ─── Validação de domínio: ValidadorDeCliente ─────────────────────────────────

/**
 * ABORDAGEM 4 — Validador externo para regras complexas.
 *
 * Aniche, p.107:
 * "Dependendo da complexidade e da necessidade de reúso dessa regra de
 *  validação, ela pode ser colocada em uma classe específica. Por exemplo,
 *  uma classe chamada ValidadorDeCliente pode fazer as diversas validações
 *  que uma classe Cliente contém, por exemplo, clientes menores de idade
 *  devem ter obrigatoriamente nome do pai e mãe, clientes com mais de 60 anos
 *  precisam do número de convênio etc."
 */
class ValidadorDeCliente {
    public boolean ehValido(ClienteParaValidar cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) return false;
        if (cliente.getIdade() < 18 && cliente.getNomeDoPai() == null) return false;
        return true;
    }
}

class ClienteParaValidar {
    private final String nome;
    private final int idade;
    private final String nomeDoPai;
    private final String telefone;
    private final String cpf;

    public ClienteParaValidar(String nome, int idade, String nomeDoPai, String telefone, String cpf) {
        this.nome = nome;
        this.idade = idade;
        this.nomeDoPai = nomeDoPai;
        this.telefone = telefone;
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getNomeDoPai() { return nomeDoPai; }
    public String getTelefone() { return telefone; }
    public String getCpf() { return cpf; }
}

// ─── Composição de validações (Chain of Responsibility) ──────────────────────

/**
 * Aniche, p.108:
 * "Você pode passar a compor regras de validação. Uma implementação para isso
 *  seria usar algo similar a um decorator ou um chain of responsibility."
 *
 * Uso do livro:
 *   Validacao validador = new NomeRequerido(
 *       new TelefoneRequerido(
 *           new CPFRequerido()
 *       )
 *   );
 *   if(validador.ehValido(cliente)) { ... }
 */
interface Validacao {
    boolean ehValido(ClienteParaValidar cliente);
}

class NomeRequeridoOriginal implements Validacao {

    private final Validacao proximo;

    public NomeRequeridoOriginal(Validacao proximo) { this.proximo = proximo; }
    public NomeRequeridoOriginal() { this(null); }

    @Override
    public boolean ehValido(ClienteParaValidar cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) return false;
        return proximo == null || proximo.ehValido(cliente);
    }
}

class TelefoneRequerido implements Validacao {

    private final Validacao proximo;

    public TelefoneRequerido(Validacao proximo) { this.proximo = proximo; }
    public TelefoneRequerido() { this(null); }

    @Override
    public boolean ehValido(ClienteParaValidar cliente) {
        if (cliente.getTelefone() == null || cliente.getTelefone().isBlank()) return false;
        return proximo == null || proximo.ehValido(cliente);
    }
}

class CPFRequerido implements Validacao {

    private final Validacao proximo;

    public CPFRequerido(Validacao proximo) { this.proximo = proximo; }
    public CPFRequerido() { this(null); }

    @Override
    public boolean ehValido(ClienteParaValidar cliente) {
        if (cliente.getCpf() == null
                || cliente.getCpf().isBlank()
                || !cliente.getCpf().matches("\\d{11}"))
            return false;
        return proximo == null || proximo.ehValido(cliente);
    }
}

// ─── Controlador: validação de formato na camada certa ───────────────────────

/**
 * VALIDAÇÃO NO CONTROLADOR — Aniche, p.104:
 * "Para o primeiro caso [validação de formato], a boa ideia é mais natural.
 *  Sempre que você recebe dados do usuário, isso é feito em alguma camada
 *  intermediária. Em uma aplicação web, por exemplo, você os recebe no
 *  Controller. Idealmente, esse tipo de validação deve ser feito lá."
 *
 * Exemplo do livro (adaptado sem @Path):
 */
class ControladorDePedido {

    private final List<String> validacoes = new ArrayList<>();

    public String salva(String carrinhoId, String codigoItem) {

        if (carrinhoId == null) {
            validacoes.add("Você deve estar em um carrinho válido");
        }
        if (codigoItem == null || codigoItem.isEmpty()) {
            validacoes.add("Você deve escolher um item válido");
        }
        if (!validacoes.isEmpty()) {
            return "erro: " + validacoes;
        }
        // ... processamento normal
        return "sucesso";
    }

    public List<String> getValidacoes() { return validacoes; }
}