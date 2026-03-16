package capitulo8_consistencia.v7_classes_feias;

import capitulo8_consistencia.v1_construtor_rico.Missao;
import capitulo8_consistencia.v1_construtor_rico.NivelDePerigo;

/**
 * CLASSES FEIAS POR NATUREZA — Aniche, Cap. 8, seção 8.7.
 *
 * "Algumas classes nasceram para serem feias. Controladores são um bom exemplo.
 *  Fábricas também tendem a ter péssimos códigos, cheios de ifs e configurações."
 *
 * Esta fábrica é altamente acoplada — conhece todos os tipos concretos.
 * É procedural, cheia de ifs, e isso é NORMAL e ESPERADO.
 *
 * A moral: mantenha o código feio ESTÁVEL e nas PONTAS da aplicação.
 * As classes de domínio (Missao, Cacador) devem ser limpas.
 * Esta fábrica é alterada raramente — não gera bugs com frequência.
 *
 * ✅ Código feio, mas estável e isolado
 * ✅ O feio não vaza para o domínio
 * ✅ Ponto único de criação — se a lógica muda, muda aqui
 */
public class FabricaDeMissao {

    /**
     * Cria uma Missao a partir de dados brutos — valida, transforma, instancia.
     * ❌ Este método é feio de propósito — é o que fábricas fazem.
     */
    public Missao criar(String monstro, String cliente, String valorStr, String perigoStr) {
        if (monstro == null || monstro.isBlank())
            throw new IllegalArgumentException("Monstro é obrigatório");
        if (cliente == null || cliente.isBlank())
            throw new IllegalArgumentException("Cliente é obrigatório");

        double valor;

        try {
            valor = Double.parseDouble(valorStr.replace(",","."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor de recompensa inválido: " + valorStr);
        }

        NivelDePerigo perigo;

        try {
            perigo = NivelDePerigo.valueOf(perigoStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            perigo = NivelDePerigo.MEDIO; // padrão se não conhecido
        }

        // ✅ O objeto Missao nasce limpo — a sujeira ficou aqui na fábrica
        return new Missao(monstro, cliente, valor, perigo);

    }
}
