package capitulo3_acoplamento.v2_inversao_dependencia.gerador_nf;

/**
 * 💾 O ESCRIVÃO (DAO - Data Access Object)
 *
 * Esta classe é responsável por persistir a Nota Fiscal.
 * No contexto do DIP (Dependency Inversion Principle), ela é apenas mais uma
 * implementação da interface {@link AcaoAposGerarNota}.
 *
 * O Gerador não sabe que esta classe existe. Ele apenas chama "executa()".
 * Isso permite que troquemos o banco de dados (SQL, Mongo, Arquivo) sem tocar no Gerador.
 */

public class NotaFiscalDao implements AcaoAposGerarNota {
    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("Salvando NF no banco de dados (via interface)...");
    }
}
