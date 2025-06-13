package model;

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
            return true;
        }
        return false;
    }

    @Override
    public boolean depositar(double valor) {
        saldo += valor;
        return true;
    }
}
