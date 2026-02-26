package capitulo4_ocp.v6_calculadora_strategy_map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 🚀 O PONTO DE PARTIDA (SPRING BOOT)
 *
 * Esta classe inicia o servidor web.
 *
 * 🛡️ O QUE ESTAMOS VENDO AQUI?
 * A v6 é uma aplicação web completa (API REST).
 * Ela utiliza o Spring Boot para gerenciar a injeção de dependência e os endpoints.
 *
 * ⚔️ OBJETIVO DA V6:
 * Demonstrar como resolver o problema do OCP na Factory (v5) usando um MAPA DE ESTRATÉGIAS.
 * Isso elimina os IFs e torna o sistema plugável.
 */
@SpringBootApplication
public class CalculadoraApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculadoraApplication.class, args);
        System.out.println("\n🚀 Servidor rodando! Faça suas requisições no Postman em: " +
                "http://localhost:8080/api/calculadora/calcular");
    }

}
