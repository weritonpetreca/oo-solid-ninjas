package capitulo3_acoplamento.v1_acoplamento_concreto;

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
    public GeradorDeNotaFiscal(EnviadorDeEmail email, NotaFiscalDao dao) {
        this.email = email;
        this.dao = dao;
    }

    public NotaFiscal gera(double valor) {
        double imposto = valor * 0.06;
        NotaFiscal nf = new NotaFiscal(valor, imposto);

        // A classe sabe "quem" faz o trabalho, em vez de saber "o que" precisa ser feito.
        this.email.envia(nf);
        this.dao.persiste(nf);

        return nf;
    }
}
