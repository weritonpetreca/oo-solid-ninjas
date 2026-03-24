package capitulo11_metricminer.witcherminer.core;

public class OpcoesDoWitcherMiner {
    private final String arquivoDeHistorico;
    private final int threads;
    private final String arquivoDeSaida;

    public OpcoesDoWitcherMiner(String arquivoDeHistorico, int threads, String saida) {
        this.arquivoDeHistorico = arquivoDeHistorico; 
        this.threads = threads; 
        this.arquivoDeSaida = saida;
    }

    public String getArquivoDeHistorico() { return arquivoDeHistorico; }
    public int getThreads() { return threads; }
    public String getArquivoDeSaida() { return arquivoDeSaida; }
}
