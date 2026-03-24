package capitulo11_metricminer.witcherminer.adapters;

import capitulo11_metricminer.witcherminer.domain.RegistroHistoricoGuilda;

public class RegistroHistoricoFactory {
    private RegistroHistoricoFactory() {}

    public static RegistroHistoricoGuilda criarGuilda(String nome) {
        return new RegistroHistoricoGuilda(nome, new ArquivoHistoricoGuildaFake(nome));
    }
}
