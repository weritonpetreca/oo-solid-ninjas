package capitulo8_consistencia.v3_bom_vizinho;

/**
 * Interface de Caçador — comportamento que qualquer caçador expõe.
 * ✅ Permite Null Object sem quebrar o polimorfismo.
 */
public interface CacadorNullable {
    String getNome();
    String getEscola();
    double calcularBonus();
    boolean isConhecido();  // ✅ semântica clara: é um caçador real ou null object?
}

// ─── Implementação real ──────────────────────────────────────────────────────

class CacadorReal implements CacadorNullable {

    private final String nome;
    private final String escola;
    private final int nivel;

    CacadorReal(String nome, String escola, int nivel) {
        this.nome = nome;
        this.escola = escola;
        this.nivel = nivel;
    }

    @Override
    public String getNome() { return nome; }

    @Override
    public String getEscola() { return escola; }

    @Override
    public double calcularBonus() { return nivel * 10.0; }

    @Override
    public boolean isConhecido() { return true; }
}

/**
 * NULL OBJECT — Aniche, Cap. 8, seção 8.3:
 * "Se você tem dados que realmente podem ser nulos, pense em usar Null Objects."
 *
 * ✅ Implementa a mesma interface com comportamento neutro
 * ✅ O serviço não precisa de if (cacador != null) em lugar nenhum
 * ✅ Comportamento padrão explícito e documentado
 */
class CacadorDesconhecido implements CacadorNullable {

    @Override
    public String getNome() { return "Caçador Anônimo"; }

    @Override
    public String getEscola() { return "Escola Desconhecida"; }

    @Override
    public double calcularBonus() { return 0.0; }

    @Override
    public boolean isConhecido() { return false; }
}

/**
 * SOLUÇÃO v3: Serviço limpo — confia no Null Object, zero null checks.
 *
 * ✅ Teorema do bom vizinho: o chamador garante que passa um objeto válido
 * ✅ Se não tem caçador real, passa CacadorDesconhecido — nunca null
 */
class ServicoDeContratoSeguro {

    public void processarMissao(String nomeMissao, CacadorNullable cacador) {
        System.out.printf("[CONTRATO] %s - Caçador: %s (%s) - Bônus: R$%.2f%n",
                nomeMissao, cacador.getNome(), cacador.getEscola(), cacador.calcularBonus());

        if (!cacador.isConhecido()) {
            System.out.println("⚠ Caçador não identificado — missão registrada sem vínculo.");
        }
    }
}