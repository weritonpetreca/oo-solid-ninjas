package capitulo8_consistencia.v2_validacao;

import java.util.ArrayList;
import java.util.List;

/**
 * ABORDAGEM 2: Builder para criação segura de CredencialDeBruxo.
 *
 * Aniche, Cap. 8, seção 8.2:
 * "A vantagem do Builder é que você garante que a classe não será criada
 *  com valor inválido, e ao mesmo tempo não coloca o código de validação
 *  dentro da própria classe de domínio."
 *
 * ✅ Separa lógica de validação da classe de domínio
 * ✅ Permite retornar objeto de erro rico (lista de erros)
 * ✅ Garante que CredencialDeBruxo só nasce válida
 */
public class CredencialBuilder {

    private String letra;
    private String numero;
    private String escola;

    public CredencialBuilder comLetra(String letra) {
        this.letra = letra;
        return this;
    }

    public CredencialBuilder comNumero(String numero) {
        this.numero = numero;
        return this;
    }

    public CredencialBuilder comEscola(String escola) {
        this.escola = escola;
        return this;
    }

    /**
     * Constrói a credencial validada.
     * ✅ Erros são coletados e retornados de forma descritiva.
     */
    public ResultadoDeCriacao build() {
        List<String> erros = new ArrayList<>();

        if (letra == null || !letra.matches("[A-Z]"))
            erros.add("Letra deve ser uma única letra maiúscula (ex: G, Y, C)");

        if (numero == null || !numero.matches("\\d{2}"))
            erros.add("Número deve ter exatamente dois dígitos (ex: 01, 22)");

        if (escola == null || escola.isBlank())
            erros.add("Escola não pode ser vazia");

        else if (!escola.matches("[A-Z]+"))
            erros.add("Escola deve conter apenas letras maiúsculas");

        if (!erros.isEmpty()) {
            return ResultadoDeCriacao.falha(erros);
        }

        String codigo = letra + numero + "-" + escola;
        return ResultadoDeCriacao.sucesso(new CredencialDeBruxo(codigo));
    }
}
