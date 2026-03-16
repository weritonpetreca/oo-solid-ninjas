package capitulo8_consistencia.v2_validacao;

import java.util.List;

/**
 * Fachada pública para demonstração do v2 — acessa as classes package-private do pacote.
 * Chamada pelo SimuladorDeConsistencia sem precisar expor os tipos internos.
 */
public class DemoV2 {

    public static void executar() {
        System.out.println("Abordagem 3: Validador composto (Chain of Responsibility)");
        ValidadorComposto validador = new ValidadorComposto(List.of(
                new NomeRequerido(),
                new EscolaRequerida(),
                new NivelMinimo(10),
                new EmailValido()
        ));

        var cacadorValido   = new CacadorParaRegistro("Geralt", "Lobo", 50, "geralt@lobo.com", "G01-LOBO");
        var cacadorInvalido = new CacadorParaRegistro("", "", 3, "email-sem-arroba", "");

        System.out.println("    Caçador válido — erros: " + validador.validar(cacadorValido));
        System.out.println("    Caçador inválido — erros: " + validador.validar(cacadorInvalido));
    }
}
