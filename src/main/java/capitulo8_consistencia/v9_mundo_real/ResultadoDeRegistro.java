package capitulo8_consistencia.v9_mundo_real;

import java.util.List;

/** Resultado rico de um registro — sucesso ou falha com erros detalhados. */
public class ResultadoDeRegistro {
    private final Cacador cacador;
    private final List<String> erros;
    private final boolean sucesso;

    private ResultadoDeRegistro(Cacador cacador, List<String> erros, boolean sucesso) {
        this.cacador = cacador;
        this.erros = erros;
        this.sucesso = sucesso;
    }

    public static ResultadoDeRegistro sucesso(Cacador cacador) {
        return new ResultadoDeRegistro(cacador, List.of(), true);
    }

    public static ResultadoDeRegistro falha(List<String> erros) {
        return new ResultadoDeRegistro(null, erros, false);
    }

    public Cacador getCacador() { return cacador; }
    public List<String> getErros() { return erros; }
    public boolean isSucesso() { return sucesso; }
}
