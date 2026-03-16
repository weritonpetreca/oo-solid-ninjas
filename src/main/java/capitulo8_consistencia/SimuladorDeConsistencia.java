package capitulo8_consistencia;

import capitulo8_consistencia.v1_construtor_rico.*;
import capitulo8_consistencia.v2_validacao.*;
import capitulo8_consistencia.v3_bom_vizinho.*;
import capitulo8_consistencia.v4_tiny_types.*;
import capitulo8_consistencia.v5_dto.*;
import capitulo8_consistencia.v6_imutabilidade.*;
import capitulo8_consistencia.v7_classes_feias.*;
import capitulo8_consistencia.v9_mundo_real.*;

import java.util.List;

/**
 * ⚔️ CAPÍTULO 8 — Consistência, Objetinhos e Objetões
 * Simulador didático: O Sistema de Registro do Continente
 */
public class SimuladorDeConsistencia {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("=".repeat(62));
        System.out.println("  CAP 8 — CONSISTÊNCIA, OBJETINHOS E OBJETÕES");
        System.out.println("  O Sistema de Registro do Continente");
        System.out.println("=".repeat(62));
        System.out.println();

        simularV1_ConstrutorRico();
        simularV2_Validacao();
        simularV3_BomVizinho();
        simularV4_TinyTypes();
        simularV5_DTO();
        simularV6_Imutabilidade();
        simularV9_MundoReal();
        imprimirResumo();
    }

    // ─── V1: Construtor Rico ──────────────────────────────────────────────────

    static void simularV1_ConstrutorRico() {
        System.out.println("[ V1 ] Construtor Rico — Objetos que nascem válidos");
        System.out.println();

        System.out.println("Tentando criar missão sem construtor rico...");
        MissaoSemConstrutorRico missaoInvalida = new MissaoSemConstrutorRico();
        try {
            System.out.println("    Descrição: " + missaoInvalida.gerarDescricao());
        } catch (NullPointerException e) {
            System.out.println("    ❌ NullPointerException! Monstro e cliente nunca foram setados.");
        }

        System.out.println();
        System.out.println("Criando missão com construtor rico...");
        Missao missao = new Missao("Grifo Real", "Aldeão de Novigrad", 1500.0, NivelDePerigo.ALTO);
        missao.adicionarRequisito("Poção de Swallow");
        missao.aceitar();

        System.out.println("    ✅ " + missao.gerarDescricao());
        System.out.printf("    Taxa da Guilda: R$%.2f | Líquida: R$%.2f%n",
                missao.calcularTaxaDaGuilda(), missao.calcularRecompensaLiquida());
        System.out.println("    Status: " + missao.getStatus());
        System.out.println();
    }

    // ─── V2: Validação ────────────────────────────────────────────────────────

    static void simularV2_Validacao() {
        System.out.println("[ V2 ] Validação — Quatro abordagens");
        System.out.println();

        System.out.println("Abordagem 1: Validação no construtor");
        try {
            new CredencialDeBruxo("invalida");
        } catch (IllegalArgumentException e) {
            System.out.println("    ❌ " + e.getMessage());
        }
        CredencialDeBruxo credValid = new CredencialDeBruxo("G01-LOBO");
        System.out.println("    ✅ Credencial válida: " + credValid);

        System.out.println();
        System.out.println("Abordagem 2: Builder com resultado rico");
        ResultadoDeCriacao resultado = new CredencialBuilder()
                .comLetra("Y")
                .comNumero("22")
                .comEscola("ARETUZA")
                .build();

        if (resultado.isSucesso()) {
            System.out.println("    ✅ " + resultado.getCredencial());
        }

        ResultadoDeCriacao falha = new CredencialBuilder()
                .comLetra("x")        // minúscula
                .comNumero("999")     // três dígitos
                .build();

        System.out.println("    ❌ Erros: " + falha.getErros());

        System.out.println();
        DemoV2.executar();
        System.out.println();
    }

    // ─── V3: Bom Vizinho ──────────────────────────────────────────────────────

    static void simularV3_BomVizinho() {
        System.out.println("[ V3 ] Teorema do Bom Vizinho e Null Object");
        System.out.println();
        DemoV3.executar();
        System.out.println();
    }

    // ─── V4: Tiny Types ───────────────────────────────────────────────────────

    static void simularV4_TinyTypes() {
        System.out.println("[ V4 ] Tiny Types — O Compilador como Guardião");
        System.out.println();

        System.out.println("Sem tiny types — fácil de passar argumentos na ordem errada:");
        CacadorSemTinyTypes errado = new CacadorSemTinyTypes(
                "Escola do Lobo", "Geralt de Rívia",   // ❌ invertido — sem erro de compilação
                "geralt@lobo.com", 85
        );
        System.out.println("    ❌ Nome: " + errado.getNome() + " | Escola: " + errado.getEscola());

        System.out.println();
        System.out.println("Com tiny types — erro de ordem não compila:");
        CacadorComTinyTypes geralt = new CacadorComTinyTypes(
                new NomeDeGuerra("Geralt de Rívia"),
                new EscolaDeCaca("Escola do Lobo"),
                new EmailDeCacador("geralt@lobo.com"),
                new NivelDeExperiencia(85)
        );
        System.out.println("    ✅ " + geralt);

        System.out.println();
        System.out.println("Tiny types com validação embutida:");
        try {
            new NivelDeExperiencia(150); // fora do intervalo
        } catch (IllegalArgumentException e) {
            System.out.println("    ❌ " + e.getMessage());
        }
        try {
            new EmailDeCacador("semArroba");
        } catch (IllegalArgumentException e) {
            System.out.println("    ❌ " + e.getMessage());
        }
        System.out.println();
    }

    // ─── V5: DTO ──────────────────────────────────────────────────────────────

    static void simularV5_DTO() {
        System.out.println("[ V5 ] DTOs do Bem — Transferência Semântica entre Camadas");
        System.out.println();

        MontadorDeResumo montador = new MontadorDeResumo();
        ResumoDeMissaoDTO ficha = montador.montar(
                "Yennefer de Vengerberg", "Aretuza", 92,
                "Missão do Djinn", "Djinn", 5000.0, "EM_ANDAMENTO", 47
        );

        System.out.println("    Caçador:   " + ficha.getNomeCacador() + " (" + ficha.getEscolaCacador() + ")");
        System.out.println("    Missão:    " + ficha.getNomeMissao() + " vs " + ficha.getMonstroAlvo());
        System.out.printf ("    Recompensa: R$%.2f → Líquida: R$%.2f%n",
                ficha.getRecompensa(), ficha.getRecompensaLiquida());
        System.out.println("    Ameaça:    " + ficha.getNivelDeAmeaca());
        System.out.println("    Requisitos:" + ficha.getRequisitosEspeciais());
        System.out.println("    Status:    " + ficha.getStatus());
        System.out.println();
    }

    // ─── V6: Imutabilidade ────────────────────────────────────────────────────

    static void simularV6_Imutabilidade() {
        System.out.println("[ V6 ] Imutabilidade x Mutabilidade");
        System.out.println();

        System.out.println("Objeto mutável — original corrompido:");
        LocalizacaoMutavel loc = new LocalizacaoMutavel("Novigrad", 52.0, 21.0);
        System.out.println("    Original: " + loc.getRegiao());
        loc.moverPara("Oxenfurt", 51.5, 21.5); // ❌ a variável 'loc' mudou de lugar
        System.out.println("    ❌ Após moverPara: " + loc.getRegiao() + " (original perdido)");

        System.out.println();
        System.out.println("Objeto imutável — original preservado:");
        LocalizacaoImutavel kaerMorhen = new LocalizacaoImutavel("Kaer Morhen", 52.0, 21.0);
        LocalizacaoImutavel novaBase   = kaerMorhen.comNovaRegiao("Novigrad"); // nova instância
        System.out.println("    ✅ Original: " + kaerMorhen);
        System.out.println("    ✅ Nova:     " + novaBase);
        System.out.printf ("    Distância entre bases: %.2f%n",
                kaerMorhen.calcularDistanciaAte(novaBase));

        System.out.println();
        System.out.println("Lista interna protegida:");
        RegistroDeRastro rastro = new RegistroDeRastro("Basilisco");
        rastro.adicionarAvistamento(kaerMorhen);
        rastro.adicionarAvistamento(novaBase);

        var avistamentos = rastro.getAvistamentos();
        try {
            avistamentos.add(new LocalizacaoImutavel("Cintra", 50.0, 20.0));
        } catch (UnsupportedOperationException e) {
            System.out.println("    ❌ Tentativa de modificar lista externa bloqueada — encapsulamento OK");
        }
        System.out.println("    Total avistamentos: " + rastro.totalAvistamentos());
        System.out.println();
    }

    // ─── V9: Mundo Real ───────────────────────────────────────────────────────

    static void simularV9_MundoReal() {
        System.out.println("[ V9 ] Mundo Real — Sistema de Registro do Continente");
        System.out.println();
        DemoV9.executar();
        System.out.println();
    }

    // ─── Resumo ───────────────────────────────────────────────────────────────

    static void imprimirResumo() {
        System.out.println("=".repeat(62));
        System.out.println("  RESUMO DO CAPÍTULO 8");
        System.out.println("=".repeat(62));
        System.out.println("  Construtor rico   → objeto nasce válido, permanece válido");
        System.out.println("  Validação         → construtor / builder / validador composto");
        System.out.println("  Bom vizinho       → nunca passe null; use Null Object");
        System.out.println("  Tiny types        → compilador como guardião de tipos");
        System.out.println("  DTOs              → transferência semântica entre camadas");
        System.out.println("  Imutabilidade     → classes que não mudam = sem surpresas");
        System.out.println("  Classes feias     → existem, mas ficam estáveis nas pontas");
        System.out.println("  Nomenclatura      → nomes que revelam intenção");
        System.out.println("=".repeat(62));
    }
}
