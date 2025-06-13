package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Conta {
    protected static int proximoNumero = 1;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;
    protected List<String> historicoOperacoes = new ArrayList<>();

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

    public String getSaldoExtrato() {
        return "Conta " + numero + " | Tipo: " + this.getClass().getSimpleName() + " | Saldo: R$ " + String.format("%.2f", saldo);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumero() {
        return numero;
    }
    
    public List<String> getHistoricoOperacoes() {
        return historicoOperacoes;
    }

    protected void registrarDeposito(double valor) {
        historicoOperacoes.add("Depósito: +" + valor + " em " + LocalDate.now());
    }

    protected void registrarSaque(double valor) {
        historicoOperacoes.add("Saque: -" + valor + " em " + LocalDate.now());
    }
}
