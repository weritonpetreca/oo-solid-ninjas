package capitulo6_heranca_composicao.v2_lsp_quadrado_retangulo;

/**
 * Retângulo com dois lados independentes.
 *
 * Pré-condição de Retangulo(x, y): x e y podem ser diferentes.
 * Isso é o contrato que qualquer código que usa Retangulo vai assumir.
 */
public class Retangulo {

    private int x;
    private int y;

    public Retangulo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int area() {
        return x * y;
    }
}
