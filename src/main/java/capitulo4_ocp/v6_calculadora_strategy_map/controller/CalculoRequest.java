package capitulo4_ocp.v6_calculadora_strategy_map.controller;

/**
 * 📦 O PACOTE (DTO - Data Transfer Object)
 *
 * Representa os dados que chegam na requisição HTTP.
 * É um Record (Java 14+), imutável e simples.
 */
public record CalculoRequest(
        String produto,
        double valor,
        String cidade,
        String tipoCliente,
        String tipoFrete
) {
}
