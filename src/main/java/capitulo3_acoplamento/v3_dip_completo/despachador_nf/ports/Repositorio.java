package capitulo3_acoplamento.v3_dip_completo.despachador_nf.ports;

/**
 * 📜 PORTA: O ESCRIVÃO
 *
 * Contrato para qualquer mecanismo de persistência.
 *
 * ⚔️ NA VIDA REAL:
 * Esta interface permite que hoje usemos PostgreSQL via NFDao,
 * e amanhã possamos trocar por MongoDB, um arquivo CSV ou
 * até um serviço de nuvem — sem tocar em uma linha do Despachador.
 *
 * 🛡️ ARQUITETURA HEXAGONAL:
 * Esta é uma "porta de saída" (driven port).
 * O domínio define o contrato, a infraestrutura se adapta a ele.
 * Nunca o contrário.
 */

import capitulo3_acoplamento.v3_dip_completo.despachador_nf.domain.NotaFiscal;

public interface Repositorio {
    void persiste(NotaFiscal nf);
}
