package capitulo3_acoplamento.v1_acoplamento_concreto.gerador_nf;

/**
 * Outra ferramenta concreta: O Escriba do Banco de Dados.
 * Lida com a persistência (salvar o progresso).
 *
 * @author Weriton L. Petreca
 */

public class NotaFiscalDao {
    public void persiste(NotaFiscal nf) {
        System.out.println("Salvando NF no banco de dados...");
    }
}
