package model;

import java.time.LocalDate;

public class ContaInvestimento extends Conta {
    private double rendimentoMensal;

    public ContaInvestimento(Cliente cliente, double rendimentoMensal) {
        super(cliente);
        this.rendimentoMensal = rendimentoMensal;
    }

    public void aplicarRendimento() {
        saldo *= (1 + rendimentoMensal);
        if (rendimentoMensal > 0) {
            saldo -= saldo * rendimentoMensal * 0.01;
        }
    }

    @Override
    public boolean sacar(double valor) {
        double imposto = saldo * rendimentoMensal * 0.225;
        double total = valor + imposto;
        if (total <= saldo) {
            saldo -= total;
            registrarSaque(valor);
            return true;
        }
        return false;
    }

    @Override
    public boolean depositar(double valor) {
        saldo += valor;
        registrarDeposito(valor);
        return true;
    }
}
