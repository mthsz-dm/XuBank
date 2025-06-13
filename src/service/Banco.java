package service;

import model.*;
import java.util.*;
import java.util.stream.Collectors;

public class Banco {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Conta> contas = new ArrayList<>();

    public void cadastrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public Conta abrirConta(Cliente cliente, String tipo) {
        Conta novaConta = null;
        switch (tipo.toLowerCase()) {
            case "corrente":
                novaConta = new ContaCorrente(cliente, 100);
                break;
            case "poupanca":
                novaConta = new ContaPoupanca(cliente);
                break;
            case "rendafixa":
                double rf = 0.005 + Math.random() * (0.0085 - 0.005);
                novaConta = new ContaRendaFixa(cliente, rf);
                break;
            case "investimento":
                double inv = -0.006 + Math.random() * (0.015 - (-0.006));
                novaConta = new ContaInvestimento(cliente, inv);
                break;
        }
        if (novaConta != null) {
            contas.add(novaConta);
            cliente.adicionarConta(novaConta);
        }
        return novaConta;
    }

    public Cliente getClienteMaiorSaldo() {
        return clientes.stream().max(Comparator.comparingDouble(Cliente::getSaldoTotal)).orElse(null);
    }

    public Cliente getClienteMenorSaldo() {
        return clientes.stream().min(Comparator.comparingDouble(Cliente::getSaldoTotal)).orElse(null);
    }

    public double getSaldoMedio() {
        return clientes.stream().mapToDouble(Cliente::getSaldoTotal).average().orElse(0.0);
    }

    public Map<String, Double> getCustodiaPorTipo() {
        return contas.stream().collect(Collectors.groupingBy(
            conta -> conta.getClass().getSimpleName(),
            Collectors.summingDouble(Conta::getSaldo)
        ));
    }
    public List<Cliente> getClientes() {
        return clientes;
    }
    public List<Conta> getContas() {
        return contas;
    }

}
