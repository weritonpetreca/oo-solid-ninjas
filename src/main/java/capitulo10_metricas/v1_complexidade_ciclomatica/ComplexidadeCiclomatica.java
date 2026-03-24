package capitulo10_metricas.v1_complexidade_ciclomatica;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

/**
 * ════════════════════════════════════════════════════════════════
 * MÉTRICAS — Complexidade Ciclomática
 * ════════════════════════════════════════════════════════════════
 *
 * Aniche, Cap. 10, seção 10.1:
 * "Quanto mais controladores de fluxo em um código, como ifs, fors e whiles,
 *  mais caminhos esse código terá e, por consequência, se tornará mais
 *  complicado de ser entendido."
 *
 * CC = número de instruções de desvio + 1
 *
 * No Continente, um bruxo enfrenta muitos tipos de monstros. O código que
 * decide "o que fazer" para cada monstro é o melhor termômetro de CC.
 * Um método com CC = 12 grita: "Refatore-me!"
 */

// ─── PROBLEMA: Estratégia de Caça com Alta CC ────────────────────────────────

/**
 * ❌ EstrategiaDeCAcaComAltaCC — CC = 12.
 * Toma decisões para cada tipo de monstro, clima e arma.
 * Difícil de testar (12 caminhos!), difícil de entender, difícil de manter.
 */
class EstrategiasDeCacaComAltaCC {

    /**
     * ❌ CC = 12 (11 desvios + 1).
     * "Quando você ver um método com CC > 10, coloque na fila de refatoração."
     */
    public String definirEstrategia(String tipoMonstro, String clima, String armaDisponivel) {
        String estrategia = "ESPERAR";

        // Desvio 1
        if ("GRIFO".equals(tipoMonstro)) {
            // Desvio 2
            if ("CHUVA".equals(clima)) {
                estrategia = "USAR_ARMADILHA";
            } else {
                estrategia = "ATAQUE_DIRETO";
            }
            // Desvio 3
        } else if ("STRIGE".equals(tipoMonstro)) {
            // Desvio 4
            if ("NOITE".equals(clima)) {
                // Desvio 5
                if ("ESPADA_PRATA".equals(armaDisponivel)) {
                    estrategia = "ATAQUE_NOTURNO";
                } else {
                    estrategia = "RECUAR";
                }
            } else {
                estrategia = "CACAR_DIURNO";
            }
            // Desvio 6
        } else if ("LOBISOMEM".equals(tipoMonstro)) {
            // Desvio 7
            if ("LUA_CHEIA".equals(clima)) {
                // Desvio 8
                if ("ESPADA_PRATA".equals(armaDisponivel)) {
                    estrategia = "LUTA_DIRETA";
                } else {
                    estrategia = "FUGIR";
                }
            } else {
                estrategia = "CAPTURAR";
            }
            // Desvio 9
        } else if ("BASILISCO".equals(tipoMonstro)) {
            // Desvio 10
            if ("ESPELHO".equals(armaDisponivel)) {
                estrategia = "USAR_ESPELHO";
                // Desvio 11
            } else if ("ESPADA_PRATA".equals(armaDisponivel)) {
                estrategia = "ATACAR_PELAS_COSTAS";
            } else {
                estrategia = "EVITAR_OLHAR";
            }
        }
        return estrategia;
        // CC = 11 + 1 = 12 — impossível de testar todos os caminhos facilmente
    }
}

// ─── SOLUÇÃO: Polimorfismo Elimina IFs → CC = 1 ──────────────────────────────

/**
 * ✅ Cada monstro sabe qual estratégia usar — Tell, Don't Ask.
 * CC do método decide() = 1 (zero desvios).
 * A complexidade foi distribuída para onde ela pertence.
 */
interface TipoMonstro {
    String decidirEstrategia(String clima, String arma);
    String getNome();
}

class Grifo implements TipoMonstro {
    @Override public String getNome() { return "GRIFO"; }

    @Override
    public String decidirEstrategia(String clima, String arma) {
        // CC local = 2 (1 if + 1) — mas coeso e isolado
        return "CHUVA".equals(clima) ? "USAR_ARMADILHA" : "ATAQUE_DIRETO";
    }
}

class Strige implements TipoMonstro {
    @Override public String getNome() { return "STRIGE"; }

    @Override
    public String decidirEstrategia(String clima, String arma) {
        if ("NOITE".equals(clima)) {
            return "ESPADA_PRATA".equals(arma) ? "ATAQUE_NOTURNO" : "RECUAR";
        }
        return "CACAR_DIURNO";
    }
}

class Lobisomem implements TipoMonstro {
    @Override public String getNome() { return "LOBISOMEM"; }

    @Override
    public String decidirEstrategia(String clima, String arma) {
        if ("LUA_CHEIA".equals(clima)) {
            return "ESPADA_PRATA".equals(arma) ? "LUTA_DIRETA" : "FUGIR";
        }
        return "CAPTURAR";
    }
}

/**
 * ✅ BruxoEstrategista — CC do método principal = 1.
 * O polimorfismo substitui todos os IFs.
 * Para um novo monstro: criar nova classe, sem tocar no BruxoEstrategista.
 */
class BruxoEstrategista {
    public String decidir(TipoMonstro monstro, String clima, String arma) {
        return monstro.decidirEstrategia(clima, arma); // CC = 1
    }
}

// ─── Calculadora de CC para demonstração ─────────────────────────────────────

/**
 * Calcula CC de uma classe com base na quantidade de métodos e desvios declarados.
 * Usado pelo Demo para exibir métricas lado a lado.
 */
class RelatorioDeCC {

    record MetodoAnalisado(String nome, int cc, String avaliacao) {
        static MetodoAnalisado de(String nome, int desvios) {
            int cc = desvios + 1;
            String av = cc <= 3 ? "✅ EXCELENTE"
                    : cc <= 5 ? "🟡 ACEITÁVEL"
                    : cc <= 10 ? "🟠 ATENÇÃO"
                    : "🔴 REFATORA";
            return new MetodoAnalisado(nome, cc, av);
        }
    }

    public void imprimir(List<MetodoAnalisado> metodos) {
        System.out.printf("    %-45s | %-4s | %s%n", "Método", "CC", "Avaliação");
        System.out.println("    " + "-".repeat(70));
        for (var m : metodos) {
            System.out.printf("    %-45s | %-4d | %s%n", m.nome(), m.cc(), m.avaliacao());
        }
    }
}