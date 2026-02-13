package capitulo3_acoplamento.v2_inversao_dependencia;

public class NotaFiscalDao implements AcaoAposGerarNota {
    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("Salvando NF no banco de dados (via interface)...");
    }
}
