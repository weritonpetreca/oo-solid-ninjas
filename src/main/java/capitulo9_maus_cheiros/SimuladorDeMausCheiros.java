package capitulo9_maus_cheiros;

import capitulo9_maus_cheiros.livro_original.DemoLivroOriginal;
import capitulo9_maus_cheiros.v1_refused_bequest.DemoV1;
import capitulo9_maus_cheiros.v2_feature_envy.DemoV2;
import capitulo9_maus_cheiros.v3_intimidade_inapropriada.DemoV3;
import capitulo9_maus_cheiros.v4_god_class.DemoV4;
import capitulo9_maus_cheiros.v5_divergent_changes.DemoV5;
import capitulo9_maus_cheiros.v6_shotgun_surgery.DemoV6;
import capitulo9_maus_cheiros.v7_mundo_real.DemoV7;

public class SimuladorDeMausCheiros {

    public static void main(String[] args) {
        infra.Console.consertarAcentuacao();

        System.out.println("=".repeat(62));
        System.out.println("  CAP 9 — MAUS CHEIROS DE CÓDIGO");
        System.out.println("  O Bestiário dos Code Smells");
        System.out.println("=".repeat(62));
        System.out.println();

        simularLivroOriginal();
        simularV1_RefusedBequest();
        simularV2_FeatureEnvy();
        simularV3_IntimidadeInapropriada();
        simularV4_GodClass();
        simularV5_DivergentChanges();
        simularV6_ShotgunSurgery();
        simularV7_MundoReal();
        imprimirResumo();
    }

    static void simularLivroOriginal() {
        System.out.println("[ LIVRO ] Exemplos Originais do Aniche");
        System.out.println();
        DemoLivroOriginal.executar();
        System.out.println();
    }

    static void simularV1_RefusedBequest() {
        System.out.println("[ V1 ] Refused Bequest — Herança que não deveria existir");
        System.out.println();
        DemoV1.executar();
        System.out.println();
    }

    static void simularV2_FeatureEnvy() {
        System.out.println("[ V2 ] Feature Envy — Método no lugar errado");
        System.out.println();
        DemoV2.executar();
        System.out.println();
    }

    static void simularV3_IntimidadeInapropriada() {
        System.out.println("[ V3 ] Intimidade Inapropriada — Tell, Don't Ask");
        System.out.println();
        DemoV3.executar();
        System.out.println();
    }

    static void simularV4_GodClass() {
        System.out.println("[ V4 ] God Class — A classe que faz tudo");
        System.out.println();
        DemoV4.executar();
        System.out.println();
    }

    static void simularV5_DivergentChanges() {
        System.out.println("[ V5 ] Divergent Changes — Muitas razões para mudar");
        System.out.println();
        DemoV5.executar();
        System.out.println();
    }

    static void simularV6_ShotgunSurgery() {
        System.out.println("[ V6 ] Shotgun Surgery — Uma mudança, muitos arquivos");
        System.out.println();
        DemoV6.executar();
        System.out.println();
    }

    static void simularV7_MundoReal() {
        System.out.println("[ V7 ] Mundo Real — GuildaRefatorada (todos os smells corrigidos)");
        System.out.println();
        DemoV7.executar();
        System.out.println();
    }

    static void imprimirResumo() {
        System.out.println("=".repeat(62));
        System.out.println("  RESUMO DO CAPÍTULO 9 — MAUS CHEIROS");
        System.out.println("=".repeat(62));
        System.out.println("  Refused Bequest      → herança que recusa métodos do pai");
        System.out.println("  Feature Envy         → método mais interessado em outro objeto");
        System.out.println("  Intimidade Inaprop.  → classe conhece demais os internos de outra");
        System.out.println("  God Class            → classe que controla tudo e depende de tudo");
        System.out.println("  Divergent Changes    → uma classe, muitas razões para mudar");
        System.out.println("  Shotgun Surgery      → uma mudança, muitos arquivos a editar");
        System.out.println("=".repeat(62));
    }
}
