package capitulo11_metricminer.witcherminer.adapters;

import capitulo11_metricminer.witcherminer.ports.MecanismoDePersistencia;

public class CSVGuilda implements MecanismoDePersistencia {
    private final String nomeArquivo;
    private final StringBuilder buffer = new StringBuilder();
    private int linhas = 0;

    public CSVGuilda(String nomeArquivo) { 
        this.nomeArquivo = nomeArquivo; 
    }

    @Override
    public void escrever(Object... linha) {
        var row = new StringBuilder();
        for (int i = 0; i < linha.length; i++) {
            if (i > 0) row.append(",");
            row.append(linha[i]);
        }
        buffer.append(row).append("\n");
        linhas++;
        System.out.println("    [" + nomeArquivo + "] " + row);
    }

    @Override
    public void fechar() {
        System.out.println("    [" + nomeArquivo + "] fechado — " + linhas + " registros gravados");
    }

    public String getConteudo() { return buffer.toString(); }
    public int getLinhas() { return linhas; }
}
