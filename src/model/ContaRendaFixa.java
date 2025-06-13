package model;

import java.time.LocalDate;

public class ContaRendaFixa extends Conta {
    private double rendimentoMensal;

    public ContaRendaFixa(Cliente cliente, double rendimentoMensal) {
        super(cliente);
        this.rendimentoMensal = rendimentoMensal;
    }

    public void aplicarRendimento() {
        saldo *= (1 + rendimentoMensal);
    }

    public void cobrarMensalidade() {
        saldo -= 20.0;
    }

    @Override
    public boolean sacar(double valor) {
        double imposto = saldo * rendimentoMensal * 0.15;
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
