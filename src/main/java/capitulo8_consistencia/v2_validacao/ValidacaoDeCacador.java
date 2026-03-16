package capitulo8_consistencia.v2_validacao;

import java.util.List;

/**
 * ABORDAGEM 3: Validador externo com composição (Chain of Responsibility).
 *
 * Aniche, Cap. 8, seção 8.2:
 * "Se sua regra de validação for complexa, você pode compor regras de validação
 *  usando algo similar a um Decorator ou Chain of Responsibility."
 *
 * Útil quando o mesmo objeto tem regras diferentes em contextos diferentes.
 * Ex: Caçador novato exige menos que Caçador de alto nível.
 *
 * ✅ Regras compostas e intercambiáveis
 * ✅ Domínio não carrega lógica de validação contextual
 */
public interface ValidacaoDeCacador {
    List<String> validar(CacadorParaRegistro cacador);
}
