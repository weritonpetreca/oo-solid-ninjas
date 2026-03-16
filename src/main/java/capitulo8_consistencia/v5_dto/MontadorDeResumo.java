package capitulo8_consistencia.v5_dto;

import java.util.List;

/**
 * Responsável por montar o ResumoDeMissaoDTO a partir das classes de domínio.
 *
 * ✅ Converte domínio → DTO em um lugar só
 * ✅ As classes de domínio não carregam lógica de apresentação
 * ✅ A view recebe um objeto pronto — sem lógica espalhada na camada de apresentação
 *
 * Em Spring Boot, essa classe seria um @Component ou um método de @Service.
 */
public class MontadorDeResumo {

    /**
     * Monta o DTO a partir de dados brutos de domínio.
     * Simula a montagem que um Service faria numa aplicação real.
     */
    public ResumoDeMissaoDTO montar(
        String nomeCacador, String escola, int nivel,
        String nomeMissao, String monstro, double recompensa,
        String status, int totalContratos) {

        String nivelDeAmeaca = nivel >= 90 ? "CRÍTICO"
                : nivel >= 70 ? "ALTO"
                : nivel >= 40 ? "MÉDIO" : "BAIXO";

        double taxaGuilda = recompensa * 0.15;
        double liquida = recompensa - taxaGuilda;

        List<String> requisitos = nivel >= 70
                ? List.of("Poção de Swallow", "Óleo de Prata", "Bomba Igni")
                : List.of("Espada de Prata");

        return new ResumoDeMissaoDTO(
                nomeCacador, escola, nivel,
                nomeMissao, monstro, recompensa, status,
                totalContratos, nivelDeAmeaca, liquida, requisitos
        );
    }
}
