package capitulo3_acoplamento.v2_inversao_dependencia.despachador_nf;

/**
 * 💾 O ESCRIVÃO (DAO)
 *
 * Responsável pela persistência.
 * É uma dependência estável (ou deveria ser uma interface em passos futuros).
 */
public class NFDao {
    public void persiste(NotaFiscal nf) {
        System.out.println("NFDao: Salvando nota fiscal no banco de dados...");
    }
}
