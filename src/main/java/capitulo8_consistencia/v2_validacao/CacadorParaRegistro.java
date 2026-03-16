package capitulo8_consistencia.v2_validacao;

import java.util.ArrayList;
import java.util.List;

/** Dados brutos de um caçador vindo do formulário de cadastro (ainda não validado). */
public class CacadorParaRegistro {

    public final String nome;
    public final String escola;
    public final int nivel;
    public final String email;
    public final String credencial;

    public CacadorParaRegistro(String nome, String escola, int nivel, String email, String credencial) {
        this.nome = nome;
        this.escola = escola;
        this.nivel = nivel;
        this.email = email;
        this.credencial = credencial;
    }
}

class NomeRequerido implements ValidacaoDeCacador {
    @Override
    public List<String> validar(CacadorParaRegistro cacador) {
        if (cacador.nome == null || cacador.nome.isBlank())
            return List.of("Nome é obrigatório");
        return List.of();
    }
}

class EscolaRequerida implements ValidacaoDeCacador {
    @Override
    public List<String> validar(CacadorParaRegistro cacador) {
        if (cacador.escola == null || cacador.escola.isBlank())
            return List.of("Escola é obrigatória");
        return List.of();
    }
}

class NivelMinimo implements ValidacaoDeCacador{

    private final int minimo;
    NivelMinimo(int minimo) { this.minimo = minimo; }

    @Override
    public List<String> validar(CacadorParaRegistro cacador) {
        if (cacador.nivel < minimo)
            return List.of("Nível mínimo exigido: " + minimo + ". Atual: " + cacador.nivel);
        return List.of();
    }
}

class EmailValido implements ValidacaoDeCacador {
    @Override
    public List<String> validar(CacadorParaRegistro cacador) {
        if (cacador.email == null || !cacador.email.contains("@"))
            return List.of("Email inválido: " + cacador.email);
        return List.of();
    }
}

class CredencialValida implements ValidacaoDeCacador {
    @Override
    public List<String> validar(CacadorParaRegistro cacador) {
        if (cacador.credencial == null || !cacador.credencial.matches("[A-Z]\\d{2}-[A-Z]+"))
            return List.of("Credencial inválida: " + cacador.credencial);
        return List.of();
    }
}

/** Combina múltiplas regras — retorna todos os erros de uma vez. */
class ValidadorComposto implements ValidacaoDeCacador {

    private final List<ValidacaoDeCacador> regras;

    ValidadorComposto(List<ValidacaoDeCacador> regras) { this.regras = regras; }

    @Override
    public List<String> validar(CacadorParaRegistro cacador) {
        List<String> erros = new ArrayList<>();
        for (ValidacaoDeCacador regra : regras) {
            erros.addAll(regra.validar(cacador));
        }
        return erros;
    }
}

