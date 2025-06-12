package model;

public class ContaCorrente extends Conta {
    private double limiteCredito;

    public ContaCorrente(Cliente cliente, double limiteCredito) {
        super(cliente);
        this.limiteCredito = limiteCredito;
    }

    @Override
    public boolean sacar(double valor) {
        if (valor <= saldo + limiteCredito) {
            saldo -= valor;
            return true;
        }
        return false;
    }

    @Override
    public boolean depositar(double valor) {
        if (saldo < 0) {
            double taxa = (Math.abs(saldo) * 0.03) + 10;
            saldo += (valor - taxa);
        } else {
            saldo += valor;
        }
        return true;
    }
}
