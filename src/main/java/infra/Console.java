package infra;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

/**
 * Utilitário para garantir que os encantamentos (logs) sejam lidos corretamente
 * no terminal do Windows, forçando UTF-8.
 */

public class Console {

    public static void consertarAcentuacao() {
        try {
            // Redefine a saída padrão (System.out) para usar UTF-8 explicitamente
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // Se falhar, apenas segue o jogo (ou loga o erro em stderr)
            System.err.println("Falha ao configurar encoding UTF-8: " + e.getMessage());
        }
    }
}