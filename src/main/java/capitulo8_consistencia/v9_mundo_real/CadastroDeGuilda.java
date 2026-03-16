package capitulo8_consistencia.v9_mundo_real;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CadastroDeGuilda — Serviço central do sistema de registro.
 *
 * ✅ Valida antes de aceitar (bom vizinho — só dados válidos entram)
 * ✅ Null Object para buscas sem resultado (sem retorno null)
 * ✅ Nomenclatura clara: verbos de ação + substantivos descritivos
 */
public class CadastroDeGuilda {

    private final List<Cacador> cacadoresRegistrados = new ArrayList<>();

    public ResultadoDeRegistro registrar(String nome, String escola,
                                         String credencial, String regiao, String fortaleza) {
        List<String> erros = new ArrayList<>();

        if (nome == null || nome.isBlank()) erros.add("Nome de guerra é obrigatório");
        if (escola == null || escola.isBlank()) erros.add("Escola de guerra é obrigatória");
        if (credencial == null ||
                credencial.isBlank() ||
                !credencial.matches("[A-Z]\\d{2}-[A-Z]+"))
            erros.add("Credencial é obrigatória");

        if (!erros.isEmpty()) return ResultadoDeRegistro.falha(erros);

        try {
            Cacador cacador = new Cacador(
                    new NomeDeGuerra(nome),
                    new EscolaDeGuerra(escola),
                    new Credencial(credencial),
                    new UltimaLocalizacao(regiao, fortaleza)
            );
            cacadoresRegistrados.add(cacador);
            return ResultadoDeRegistro.sucesso(cacador);

        } catch (IllegalArgumentException e) {
            return ResultadoDeRegistro.falha(List.of(e.getMessage()));
        }
    }

    /**
     * Busca caçador pelo nome. Retorna Optional — sem null.
     * ✅ Nomenclatura: "buscar" comunica que pode não encontrar
     */
    public Optional<Cacador> buscarPorNome(String nome) {
        return cacadoresRegistrados.stream()
                .filter(c -> c.getNome().get().equalsIgnoreCase(nome))
                .findFirst();
    }

    public List<Cacador> listarAtivos() {
        return cacadoresRegistrados.stream()
                .filter(Cacador::isAtivo)
                .toList();
    }

    public int totalRegistrados() { return cacadoresRegistrados.size(); }
}
