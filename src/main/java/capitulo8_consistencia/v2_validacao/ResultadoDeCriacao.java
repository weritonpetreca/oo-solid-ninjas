package capitulo8_consistencia.v2_validacao;

import java.util.List;

/**
 * Objeto de resultado rico — encapsula sucesso ou falha na criação.
 *
 * ✅ Alternativa a lançar exceção no construtor ou no builder
 * ✅ O chamador pode inspecionar os erros sem try/catch
 */
public class ResultadoDeCriacao {

    private final CredencialDeBruxo credencial;
    private final List<String> erros;
    private final boolean sucesso;

    private ResultadoDeCriacao(CredencialDeBruxo credencial, List<String> erros, boolean sucesso) {
        this.credencial = credencial;
        this.erros = erros;
        this.sucesso = sucesso;
    }

    public static ResultadoDeCriacao sucesso(CredencialDeBruxo credencial) {
        return new ResultadoDeCriacao(credencial, List.of(), true);
    }

    public static ResultadoDeCriacao falha(List<String> erros) {
        return new ResultadoDeCriacao(null, erros, false);
    }

    public boolean isSucesso() { return sucesso; }
    public CredencialDeBruxo getCredencial() { return credencial; }
    public List<String> getErros() { return erros; }
}
