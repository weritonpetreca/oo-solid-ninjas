package capitulo11_metricminer.witcherminer.domain;

import java.util.Calendar;
import java.util.List;

public class ContratoRegistrado {
    private final String id;
    private final String cacador;
    private final String descricao;
    private final List<AcaoDoCacador> acoes;
    private final Calendar data;

    public ContratoRegistrado(String id, String cacador, String descricao, List<AcaoDoCacador> acoes) {
        this.id = id; 
        this.cacador = cacador; 
        this.descricao = descricao; 
        this.acoes = acoes; 
        this.data = Calendar.getInstance();
    }

    public String getId() { return id; }
    public String getCacador() { return cacador; }
    public String getDescricao() { return descricao; }
    public List<AcaoDoCacador> getAcoes() { return acoes; }
    public Calendar getData() { return data; }
}
