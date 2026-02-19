package capitulo3_acoplamento.v3_dip_completo.gerador_nf.adapters;

import capitulo3_acoplamento.v3_dip_completo.gerador_nf.domain.NotaFiscal;
import capitulo3_acoplamento.v3_dip_completo.gerador_nf.ports.AcaoAposGerarNota;


/**
 * 🔧 ADAPTER: INTEGRAÇÃO COM ERP EXTERNO
 *
 * Implementação concreta da integração com SAP.
 *
 * ⚔️ NA VIDA REAL:
 * Aqui você faria chamadas HTTP/SOAP para o sistema SAP da empresa.
 * Poderia usar RestTemplate, Feign, ou qualquer client HTTP.
 *
 * 🛡️ OCP (OPEN/CLOSED PRINCIPLE):
 * Este adapter foi adicionado SEM tocar no código do Gerador.
 * Apenas implementamos a interface e plugamos na lista de ações.
 * O sistema está "aberto para extensão, fechado para modificação".
 */

public class SapERP implements AcaoAposGerarNota {

    @Override
    public void executa(NotaFiscal nf) {
        System.out.println("🏢 SapERP: Enviando NF para o sistema SAP...");
    }
}
