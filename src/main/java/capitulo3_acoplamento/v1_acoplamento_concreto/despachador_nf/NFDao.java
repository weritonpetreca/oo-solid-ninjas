package capitulo3_acoplamento.v1_acoplamento_concreto.despachador_nf;

/**
 * 💾 O ESCRIVÃO (DAO)
 *
 * Responsável pela persistência.
 *
 * ⚠️ PROBLEMA DE ACOPLAMENTO:
 * O Despachador depende diretamente desta classe concreta.
 * Se quisermos trocar o banco de dados (ex: MongoDao),
 * teremos que mudar o construtor do Despachador.
 */
public class NFDao {
    public void persiste(NotaFiscal nf) {
        System.out.println("NFDao: Salvando nota fiscal no banco de dados...");
    }
}
