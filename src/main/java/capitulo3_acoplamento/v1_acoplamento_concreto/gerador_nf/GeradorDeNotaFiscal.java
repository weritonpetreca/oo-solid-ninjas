package capitulo3_acoplamento.v1_acoplamento_concreto.gerador_nf;

/**
 * Versão fiel ao livro "OO e SOLID para Ninjas".
 * O Problema Sutil:
 * Observe o construtor: ele exige um {@link EnviadorDeEmail} e um {@link NotaFiscalDao}.
 * Isso é Acoplamento a Classes Concretas.
 * - Eu não posso passar qualquer coisa que "envie", só posso passar O EnviadorDeEmail.
 * - Eu não posso passar qualquer coisa que "salve", só posso passar O NotaFiscalDao.
 * Se a classe concreta mudar, o Gerador corre risco. A estabilidade do Gerador depende
 * da estabilidade dessas duas classes voláteis.
 *
 * @author Weriton L. Petreca
 */

public class GeradorDeNotaFiscal {

    private final EnviadorDeEmail email;
    private final NotaFiscalDao dao;

    // AQUI ESTÁ O ERRO DO BRUXO APRENDIZ:
    // Ele pede as ferramentas EXATAS (Concretas), tirando a flexibilidade.
    public GeradorDeNotaFiscal(EnviadorDeEmail email,
                               NotaFiscalDao dao) {
        this.email = email;
        this.dao = dao;
    }

    public NotaFiscal gera(Fatura fatura) {

        double valor = fatura.getValorMensal();

        NotaFiscal nf = new NotaFiscal(
                valor,
                impostoSimplesSobreO(valor)
        );

        // A classe sabe "quem" faz o trabalho, em vez de saber "o que" precisa ser feito.
        email.enviaEmail(nf);
        dao.persiste(nf);

        return nf;
    }

    private double impostoSimplesSobreO(double valor) {
        return valor * 0.06;
    }
}
