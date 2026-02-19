package capitulo3_acoplamento.v3_dip_completo.despachador_nf.adapters;

/**
 * 🔧 ADAPTER: DAO DE NOTA FISCAL
 *
 * Implementação concreta do contrato {@link Repositorio}.
 * Responsável por salvar a Nota Fiscal no banco de dados.
 *
 * ⚔️ ARQUITETURA HEXAGONAL:
 * Esta é uma "porta de saída" adaptada.
 * O domínio define o contrato (Repositorio), e este adapter
 * implementa usando a tecnologia que quiser (JDBC, Hibernate, JPA...).
 *
 * 🛡️ NA PRÁTICA:
 * Para trocar de PostgreSQL para MongoDB, criamos MongoNFRepository
 * e trocamos apenas na configuração. Zero impacto no Despachador.
 */

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports.Repositorio;

public class NFDao implements Repositorio {
    @Override
    public void persiste(NotaFiscal nf) {
        System.out.println("NFDao: Salvando nota fiscal no banco de dados...");
    }
}
