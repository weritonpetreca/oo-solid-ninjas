package capitulo4_ocp.v1_o_problema;
/**
 * 🔒 A CALCULADORA RÍGIDA (O PROBLEMA)
 *
 * Esta é a versão inicial, extraída diretamente do livro de Maurício Aniche.
 * Ela parece inocente: bem coesa, baixo acoplamento (só depende de 2 classes).
 *
 * ⚔️ MAS EXISTE UM VENENO ESCONDIDO:
 * Ela INSTANCIA suas dependências diretamente dentro do método {@code calcula()}.
 *
 *
 *     TabelaDePrecoPadrao tabela = new TabelaDePrecoPadrao(); // 🚨 RÍGIDO
 *     Frete correios = new Frete();                           // 🚨 RÍGIDO
 *
 *
 * 🧙 POR QUE ISSO É PERIGOSO?
 *
 * 1. Inflexibilidade: Se o sistema precisar de uma nova tabela de preços
 *    (para clientes VIP, por exemplo), teremos que MODIFICAR esta classe.
 *    Isso viola o OCP: ela não deveria ser modificada para evoluir.
 *
 * 2. Testabilidade: Como testamos o comportamento da calculadora
 *    sem dependender da lógica real de desconto e frete?
 *    Não dá — estamos amarrados às implementações concretas.
 *
 * 3. Violação do DIP: A Calculadora depende de classes concretas
 *    ({@link TabelaDePrecoPadrao} e {@link Frete}), não de abstrações.
 *
 * ⚔️ ANALOGIA WITCHER:
 * É como um Bruxo que carrega apenas a Espada de Prata.
 * Funciona bem para monstros. Mas quando aparecer um humano,
 * ele terá que voltar ao baú e buscar a Espada de Aço.
 * O "contrato" do Bruxo (lutar) precisará ser reescrito a cada novo inimigo.
 */

public class CalculadoraDePrecos {

    public double calcula(Compra produto) {
        // 🚨 INSTANCIAÇÃO DIRETA — O VENENO OCULTO
        // Qualquer mudança nas regras de negócio exige abrir esta classe.
        TabelaDePrecoPadrao tabela = new TabelaDePrecoPadrao();
        Frete correios = new Frete();

        double desconto = tabela.descontoPara(produto.getValor());
        double frete = correios.para(produto.getCidade());

        return produto.getValor() * (1 - desconto) + frete;
    }
}
