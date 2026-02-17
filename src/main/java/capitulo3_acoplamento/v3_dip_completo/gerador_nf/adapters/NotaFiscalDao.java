package capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters;

import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.AcaoAposGerarNota;

/**
 * 🔧 ADAPTER: ESCRIVÃO DO BANCO DE DADOS
 *
 * Implementação concreta da persistência.
 *
 * ⚔️ NA VIDA REAL:
 * Aqui você usaria JPA, Hibernate, JDBC, ou qualquer framework de persistência.
 * O domínio continua sem saber qual tecnologia está salvando os dados.
 *
 * 🛡️ TROCA DE TECNOLOGIA SEM DOR:
 * Quer trocar de PostgreSQL pra MongoDB?
 * Cria MongoNotaFiscalRepository implements AcaoAposGerarNota.
 * Muda apenas a configuração. Zero impacto no Gerador.
 */

public class NotaFiscalDao implements AcaoAposGerarNota {

    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("💾 NotaFiscalDao: Salvando NF no banco de dados...");
    }
}
