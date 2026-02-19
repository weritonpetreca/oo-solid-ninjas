package capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf;

/**
 * 🐺 O COMANDANTE (DESPACHADOR)
 *
 * Comparado à v1, este Despachador é muito mais sábio.
 *
 * 🛡️ O QUE MUDOU?
 * Antes, ele sabia demais: "Se for urgente, mande por Sedex 10".
 * Agora, ele pratica o "Tell, Don't Ask" (Diga, não pergunte).
 * Ele apenas ordena ao {@link EntregadorDeNFs}: "Entregue esta nota".
 *
 * ⚔️ BENEFÍCIO DO ACOPLAMENTO REDUZIDO:
 * Ele não depende mais da {@link LeiDeEntrega} nem dos {@link Correios} diretamente.
 * Se a regra de entrega mudar (ex: usar Drone em vez de Correios),
 * esta classe NÃO precisará ser tocada.
 */
public class DespachadorDeNotasFiscais {

    private CalculadorDeImposto impostos;
    private EntregadorDeNFs entregador;
    private NFDao dao;

    public DespachadorDeNotasFiscais(
            CalculadorDeImposto impostos,
            EntregadorDeNFs entregador,
            NFDao dao
    ) {
        this.impostos = impostos;
        this.entregador = entregador;
        this.dao = dao;
    }

    public void processa(NotaFiscal nf) {

        double imposto = impostos.para(nf);
        nf.setImposto(imposto);

        // Delegação: O problema da entrega não é mais meu.
        entregador.entrega(nf);

        dao.persiste(nf);
    }
}
