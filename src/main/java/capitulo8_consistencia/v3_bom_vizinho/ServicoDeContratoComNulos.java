package capitulo8_consistencia.v3_bom_vizinho;

/**
 * PROBLEMA v3: Passagem de nulos entre classes — o anti-vizinho.
 *
 * Aniche, Cap. 8, seção 8.3:
 * "Quantas vezes você já fez algum tipo de if para garantir que o objeto
 *  recebido não era nulo? O que seria do mundo se ninguém nunca passasse
 *  nulo para sua classe?"
 *
 * Cenário: ServicoDeContrato recebe um Cacador para processar uma missão.
 * O chamador passa null quando o caçador é desconhecido — e o serviço
 * precisa de ifs defensivos em todo lugar.
 *
 * ❌ Problema: o serviço fica poluído de null checks.
 */
public class ServicoDeContratoComNulos {

    public void processarMissao(String nomeMissao, CacadorNullable cacador) {
        String nomeCacador = (cacador != null) ? cacador.getNome() : "DESCONHECIDO";
        String escola = (cacador != null) ? cacador.getEscola() : "N/A";
        double bonus = (cacador != null) ? cacador.calcularBonus() : 0.0;

        System.out.printf("[CONTRATO] %s - Caçador: %s (%s) - Bônus: R$%.2f%n",
                nomeMissao, nomeCacador, escola, bonus);
    }
}
