package capitulo4_ocp.v3_calculadora_aberta;
/**
 * 🐺 A CALCULADORA ABERTA (A SOLUÇÃO CORRETA — OCP)
 *
 * Esta é a versão que aplica o Open/Closed Principle.
 *
 * ⚔️ COMPARE COM AS VERSÕES ANTERIORES:
 *
 * v1 (Rígida):
 *
 *     TabelaDePrecoPadrao tabela = new TabelaDePrecoPadrao(); // instancia direto
 *     Frete correios = new Frete();                           // instancia direto
 *
 *
 * v3 (Aberta):
 *
 *     public CalculadoraDePrecos(TabelaDePreco tabela, ServicoDeEntrega entrega) { ... }
 *
 *
 * 🛡️ A MUDANÇA É PODEROSA DEMAIS PARA SUA SIMPLICIDADE:
 *
 * Ao receber as dependências pelo construtor em vez de instanciá-las,
 * esta classe se torna capaz de lidar com QUALQUER regra de desconto
 * e QUALQUER serviço de entrega — sem precisar ser modificada.
 *
 * 📐 ANALISANDO AS PROPRIEDADES DO OCP:
 *
 * ✅ Aberta para extensão:
 *    Passamos {@code new TabelaDePrecoVip()} e ela funciona diferente.
 *    Passamos {@code new FreteGratis()} e o frete some.
 *    Nenhuma linha desta classe foi alterada.
 *
 * ✅ Fechada para modificação:
 *    Esta classe NÃO precisa mudar quando uma nova tabela surgir.
 *    Esta classe NÃO precisa mudar quando uma nova forma de entrega surgir.
 *
 * 🧪 TESTABILIDADE COMO BÔNUS:
 *    Agora conseguimos testar o comportamento da Calculadora
 *    usando mocks (dublês) para a tabela e o frete.
 *    Isso é essencial para garantir que A CALCULADORA está correta,
 *    independentemente das implementações concretas.
 *
 * ⚔️ ANALOGIA WITCHER:
 * Geralt não muda quando encontra um novo monstro.
 * Ele recebe uma poção, troca a espada, e está pronto.
 * O Geralt (esta classe) está FECHADO para modificação,
 * mas o "equipamento" (as dependências) pode ser trocado livremente.
 *
 * 🔑 A REGRA DE OURO DO OCP:
 * "Sempre que instanciamos classes diretamente dentro de outras classes,
 * perdemos a oportunidade de trocar essa implementação em tempo de execução."
 *                                              — Maurício Aniche
 */

public class CalculadoraDePrecos {

    private final TabelaDePreco tabela;
    private final ServicoDeEntrega entrega;

    /**
     * ✅ INJEÇÃO DE DEPENDÊNCIA PELO CONSTRUTOR.
     *
     * Por que construtor e não setter?
     * O construtor GARANTE que a dependência sempre existirá.
     * Um objeto nunca será criado sem a tabela e o serviço.
     * O compilador avisa se alguém tentar criar sem as dependências.
     *
     * Setters não dão essa garantia — o programador pode esquecer de chamá-los.
     */

    public CalculadoraDePrecos(TabelaDePreco tabela, ServicoDeEntrega entrega) {
        this.tabela = tabela;
        this.entrega = entrega;
    }

    /**
     * ✅ ZERO IFS. ZERO INSTANCIAÇÕES DIRETAS.
     *
     * Este método é exatamente o que deveria ser:
     * coordenar o fluxo do cálculo, delegando cada parte para quem sabe fazer.
     *
     * Ele não sabe o que é {@link TabelaDePrecoVip} ou {@link FreteGratis}.
     * Ele apenas confia que as dependências farão seu trabalho.
     */

    public double calcula(Compra produto) {
        double desconto = tabela.descontoPara(produto.getValor());
        double frete = entrega.para(produto.getCidade());
        return produto.getValor() * (1 - desconto) + frete;
    }
}
