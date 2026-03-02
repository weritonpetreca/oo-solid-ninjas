package capitulo5_encapsulamento.v3_lei_de_demeter;
/**
 * SOLUÇÃO v3: Fatura que encapsula a operação de inadimplência.
 *
 * Aniche, Cap. 5, seção 5.6 — aplicando a Lei de Demeter:
 *
 * Antes: fatura.getCliente().marcaComoInadimplente()
 * Depois: fatura.marcaComoInadimplente()
 *
 * A Fatura agora age como um "guardião" do Cliente.
 * Quem quer marcar inadimplência fala COM a Fatura.
 * A Fatura repassa para o Cliente internamente.
 *
 * Benefício: Se Cliente mudar, apenas Fatura precisa ser ajustada.
 * O ServicoDeCobranca não percebe nada.
 */
public class FaturaEncapsulada {

    private double valor;
    private Cliente cliente;

    public FaturaEncapsulada(double valor, Cliente cliente) {
        this.valor = valor;
        this.cliente = cliente;
    }

    /**
     * ✅ A operação é delegada internamente.
     * Consumidores de Fatura não precisam saber que existe um Cliente lá dentro.
     */
    public void marcaComoInadimplente() {
        cliente.marcaComoInadimplente();
    }

    public double getValor() {
        return valor;
    }

    // getCliente() removido — não há razão para expor o Cliente diretamente
    // (exceção: camada de visualização, como discutido por Aniche na seção 5.6)
}
