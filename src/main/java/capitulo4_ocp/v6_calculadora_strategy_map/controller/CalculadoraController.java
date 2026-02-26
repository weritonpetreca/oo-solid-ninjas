package capitulo4_ocp.v6_calculadora_strategy_map.controller;

import capitulo4_ocp.v6_calculadora_strategy_map.domain.Compra;
import capitulo4_ocp.v6_calculadora_strategy_map.service.CalculadoraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 🚪 O PORTÃO DE ENTRADA (CONTROLLER)
 *
 * Esta classe é a camada mais externa da aplicação.
 * Ela recebe as requisições HTTP e delega o trabalho para o Service.
 *
 * 🛡️ RESPONSABILIDADES:
 * - Expor um endpoint (/api/calculadora/calcular).
 * - Converter o JSON da requisição (DTO) para objetos de domínio.
 * - Chamar o serviço de aplicação.
 * - Retornar uma resposta HTTP.
 */
@RestController
@RequestMapping("/api/calculadora")
public class CalculadoraController {

    private final CalculadoraService calculadoraService;

    public CalculadoraController(CalculadoraService calculadoraService) {
        this.calculadoraService = calculadoraService;
    }

    @PostMapping("/calcular")
    public ResponseEntity<String> calcular(@RequestBody CalculoRequest request) {

        Compra compra = new Compra(request.produto(), request.valor(), request.cidade());

        double total = calculadoraService.calcularTotal(compra, request.tipoCliente(), request.tipoFrete());

        return ResponseEntity.ok("O valor total da compra é: R$" + total);
    }
}
