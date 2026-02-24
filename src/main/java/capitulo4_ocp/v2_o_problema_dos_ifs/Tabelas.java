package capitulo4_ocp.v2_o_problema_dos_ifs;

/**
 * 💰 TABELA PADRÃO (v2)
 * Regra: Desconto padrão por faixas de valor.
 */
class TabelaDePrecoPadrao {
    public double descontoPara(double valor) {
        if (valor > 5000) return 0.03;
        if (valor > 1000) return 0.05;
        return 0;
    }
}

/**
 * 💰 TABELA DIFERENCIADA (v2)
 * Regra: Desconto maior para parceiros comerciais.
 */
class TabelaDePrecoDiferenciada {
    public double descontoPara(double valor) {
        if (valor > 5000) return 0.06;
        if (valor > 1000) return 0.08;
        return 0.01;
    }
}

/**
 * 💰 TABELA VIP (v2)
 * Regra: Descontos exclusivos para clientes premium.
 */
class TabelaDePrecoVip {
    public double descontoPara(double valor) {
        if (valor > 5000) return 0.12;
        if (valor > 1000) return 0.10;
        return 0.05;
    }
}
