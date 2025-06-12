package model;

public abstract class Conta {
    protected static int proximoNumero = 1;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;

    public Conta(Cliente cliente) {
        this.numero = proximoNumero++;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    public abstract boolean sacar(double valor);
    public abstract boolean depositar(double valor);

    public double getSaldo() {
        return saldo;
    }

    public String getExtratoMensal() {
        return "Conta " + numero + " | Tipo: " + this.getClass().getSimpleName() + " | Saldo: R$ " + String.format("%.2f", saldo);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumero() {
        return numero;
    }
}
