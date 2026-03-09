package capitulo6_heranca_composicao.v2_lsp_quadrado_retangulo;

/**
 * Usa Retangulo com a suposição de que x e y podem ser diferentes.
 * Com um Quadrado, isso pode gerar resultados inesperados.
 */
public class CalculadorDeArea {

    public int calcularArea(Retangulo r) {
        // Assume que qualquer Retangulo pode ter lados diferentes
        // Um Quadrado sempre vai ter área = lado^2, mesmo que o caller
        // esperasse poder representar um retângulo com lados distintos
        return r.area();
    }
}
