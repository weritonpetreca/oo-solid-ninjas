package capitulo11_metricminer.witcherminer.domain;

import capitulo11_metricminer.witcherminer.ports.ArquivoDeContratos;

public class RegistroHistoricoGuilda {
    private final String nome;
    private final ArquivoDeContratos arquivo;

    public RegistroHistoricoGuilda(String nome, ArquivoDeContratos arquivo) {
        this.nome = nome; 
        this.arquivo = arquivo;
    }

    public String getNome() { return nome; }
    public ArquivoDeContratos getArquivo() { return arquivo; }
}
