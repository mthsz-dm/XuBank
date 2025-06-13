package model;

import java.time.LocalDate;

public class ContaPoupanca extends Conta {
    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    public void aplicarRendimento() {
        saldo *= 1.006;
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= saldo) {
            saldo -= valor;
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
