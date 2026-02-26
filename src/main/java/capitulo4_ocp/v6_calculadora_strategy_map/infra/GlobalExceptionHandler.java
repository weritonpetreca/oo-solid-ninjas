package capitulo4_ocp.v6_calculadora_strategy_map.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarErrosDeValidação(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("⚠️ Erro de Validação: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErrosInesperados(Exception ex) {
        return ResponseEntity.internalServerError().body("🚨 Ocorreu um erro interno no servidor: " + ex.getMessage());
    }
}
