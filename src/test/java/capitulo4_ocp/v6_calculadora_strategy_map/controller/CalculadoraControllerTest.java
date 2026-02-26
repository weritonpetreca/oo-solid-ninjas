package capitulo4_ocp.v6_calculadora_strategy_map.controller;

import capitulo4_ocp.v6_calculadora_strategy_map.CalculadoraApplication;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

/**
 * 🌐 O TESTE DE INTEGRAÇÃO (REST ASSURED)
 *
 * Este teste sobe o servidor Spring Boot DE VERDADE em uma porta aleatória.
 * Ele simula um cliente HTTP (como o Postman) fazendo requisições reais.
 *
 * 🛡️ O QUE ESTAMOS TESTANDO?
 * 1. Se o Controller recebe o JSON corretamente.
 * 2. Se o Service injeta os Mapas de Estratégia corretamente.
 * 3. Se o GlobalExceptionHandler captura os erros.
 *
 * ⚔️ FERRAMENTA: REST Assured (biblioteca fluente para testes de API).
 */
@SpringBootTest(classes = CalculadoraApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculadoraControllerTest {

    @LocalServerPort
    private int port; // O Spring injeta a porta aleatória que ele escolheu aqui

    @BeforeEach
    void setUp() {
        // Configuramos o REST Assured para apontar para a porta do nosso servidor de teste
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Deve retornar Status 200 e o cálculo exato ao enviar JSON válido")
    void deveCalcularRetornando200() {

        // Payload (corpo da requisição) igual ao do Postman
        String jsonBody = """
                {
                  "produto": "Espada de Prata",
                  "valor": 1000.0,
                  "cidade": "SÃO PAULO",
                  "tipoCliente": "VIP",
                  "tipoFrete": "GRATIS"
                }
                """;

        // A sintaxe do REST Assured segue o BDD (Given, When, Then)
        given()
            .contentType(ContentType.JSON)
            .body(jsonBody)
        .when()
            .post("/api/calculadora/calcular")
        .then()
            .statusCode(200) // Verifica se deu sucesso
            .body(containsString("R$850.0")); // Verifica se o cálculo de 15% desc. e Frete 0 foi feito
    }

    @Test
    @DisplayName("Deve ser capturado pelo GlobalHandler e retornar 400 Bad Request")
    void deveRetornar400AoEnviarFreteInvalido() {

        String jsonBodyInvalido = """
                {
                  "produto": "Espada",
                  "valor": 1000.0,
                  "cidade": "SAO PAULO",
                  "tipoCliente": "VIP",
                  "tipoFrete": "FOGUETE"
                }
                """;

        given()
            .contentType(ContentType.JSON)
            .body(jsonBodyInvalido)
        .when()
            .post("/api/calculadora/calcular")
        .then()
            .statusCode(400) // Nosso Para-raios (Global Handler) tem que devolver 400!
            .body(containsString("⚠️ Erro de Validação")); // A mensagem personalizada
    }
}
