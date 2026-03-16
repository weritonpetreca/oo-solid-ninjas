package capitulo8_consistencia.v2_validacao;

/**
 * ABORDAGEM 1: Validação no próprio construtor.
 *
 * Aniche, Cap. 8, seção 8.2:
 * "A própria classe CPF poderia ser responsável por esse tipo de validação.
 *  A vantagem é que você garante que o objeto nunca terá um valor inválido."
 *
 * Aqui: uma Credencial de Bruxo deve ter formato XNN-ESCOLA
 * (ex: G01-LOBO, Y22-ARETUZA)
 *
 * ✅ Nunca instanciada com credencial inválida
 * ⚠️  Alguns não gostam de exceção disparada pelo construtor
 */
public class CredencialDeBruxo {

    private final String codigo;

    public CredencialDeBruxo(String codigoPossivel) {
        if (codigoPossivel == null || !codigoPossivel.matches("[A-Z]\\d{2}-[A-Z]+")) {
            throw new IllegalArgumentException(
                    "Credencial inválida '" + codigoPossivel + "'. Formato esperado: X99-ESCOLA");
        }
        this.codigo = codigoPossivel;
    }

    public String getCodigo() { return codigo; }

    @Override
    public String toString() { return "Credencial[" + codigo + "]"; }
}
