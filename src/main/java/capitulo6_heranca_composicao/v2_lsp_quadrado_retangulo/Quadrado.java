package capitulo6_heranca_composicao.v2_lsp_quadrado_retangulo;

/**
 * PROBLEMA v2: Quadrado extends Retangulo — exemplo clássico de LSP.
 *
 * Aniche, Cap. 6, seção 6.3:
 * Do ponto de vista matemático, um quadrado É um retângulo.
 * "Parece o cenário perfeito para herança."
 *
 * Mas observe a pré-condição:
 *   Retangulo(x, y): x e y podem ser qualquer valor → pré-condição fraca
 *   Quadrado(x):     x e y DEVEM ser iguais → pré-condição MAIS FORTE
 *
 * ❌ LSP diz: a classe filha só pode AFROUXAR pré-condições, nunca apertar.
 *    Quadrado APERTA a pré-condição (obriga lados iguais).
 *    Logo, herança aqui viola o LSP.
 *
 * Consequência prática:
 *   Um código que recebe Retangulo e tenta criar um com lados diferentes
 *   vai se surpreender com um Quadrado "malformado" ou com exceção.
 */
public class Quadrado extends Retangulo {

    // ❌ Força x == y — pré-condição mais forte que a do pai
    public Quadrado(int x) {
        super(x,x);
    }
}
