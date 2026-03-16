package capitulo8_consistencia.v5_dto;

import java.util.Collections;
import java.util.List;

/**
 * CONTEXTO v5: DTOs do bem — Aniche, Cap. 8, seção 8.5.
 *
 * "O problema não é ter DTOs, mas sim só ter DTOs."
 *
 * Cenário: a tela de "Resumo de Missão" exibe dados de fontes diferentes:
 *   - Nome e escola do Cacador
 *   - Monstro e valor da Missao
 *   - Total de contratos completados
 *   - Nível de ameaça calculado
 *
 * Nenhuma classe de domínio sozinha representa isso.
 * Em vez de forçar a view a montar os dados ou de criar um objeto
 * de domínio com responsabilidades demais, usamos um DTO.
 *
 * ✅ DTO representa exatamente o que a tela precisa
 * ✅ Domínio não carrega dados de apresentação
 * ✅ A transferência entre camadas fica semântica e explícita
 */
public class ResumoDeMissaoDTO {

    // Dados do caçador
    private final String nomeCacador;
    private final String escolaCacador;
    private final int nivelCacador;

    // Dados da missão
    private final String nomeMissao;
    private final String monstroAlvo;
    private final double recompensa;
    private final String status;

    // Dados calculados/agregados
    private final int totalContratosConcluidos;
    private final String nivelDeAmeaca;
    private final double recompensaLiquida;
    private final List<String> requisitosEspeciais;

    public ResumoDeMissaoDTO
    (String nomeCacador, String escolaCacador, int nivelCacador,
         String nomeMissao, String monstroAlvo, double recompensa, String status,
         int totalContratosConcluidos, String nivelDeAmeaca, double recompensaLiquida,
         List<String> requisitosEspeciais) {

        this.nomeCacador = nomeCacador;
        this.escolaCacador = escolaCacador;
        this.nivelCacador = nivelCacador;
        this.nomeMissao = nomeMissao;
        this.monstroAlvo = monstroAlvo;
        this.recompensa = recompensa;
        this.status = status;
        this.totalContratosConcluidos = totalContratosConcluidos;
        this.nivelDeAmeaca = nivelDeAmeaca;
        this.recompensaLiquida = recompensaLiquida;
        this.requisitosEspeciais = requisitosEspeciais;
    }

    public String getNomeCacador() { return  nomeCacador; }
    public String getEscolaCacador() { return escolaCacador; }
    public int getNivelCacador() { return nivelCacador; }
    public String getNomeMissao() { return nomeMissao; }
    public String getMonstroAlvo() { return monstroAlvo; }
    public double getRecompensa() { return recompensa; }
    public String getStatus() { return status; }
    public int getTotalContratosConcluidos() { return totalContratosConcluidos; }
    public String getNivelDeAmeaca() { return nivelDeAmeaca; }
    public double getRecompensaLiquida() { return recompensaLiquida; }
    public List<String> getRequisitosEspeciais() { return Collections.unmodifiableList(requisitosEspeciais); }
}
