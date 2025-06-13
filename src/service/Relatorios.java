package service;

import model.Cliente;
import model.Conta;

import java.util.*;
import java.util.stream.Collectors;

public class Relatorios {

    public static List<Cliente> getTopClientes(List<Cliente> clientes, int n) {
        return clientes.stream()
                .sorted(Comparator.comparingDouble(Cliente::getSaldoTotal).reversed())
                .limit(n)
                .toList();
    }

    public static List<Cliente> getClientesNegativados(List<Cliente> clientes) {
        return clientes.stream()
                .filter(c -> c.getSaldoTotal() < 0)
                .toList();
    }

    public static List<Conta> getContasComSaldoAbaixo(List<Conta> contas, double limite) {
        return contas.stream()
                .filter(c -> c.getSaldo() < limite)
                .toList();
    }

    public static Map<String, Double> getCustodiaPorTipo(List<Conta> contas) {
        return contas.stream().collect(Collectors.groupingBy(
                c -> c.getClass().getSimpleName(),
                Collectors.summingDouble(Conta::getSaldo)
        ));
    }

    public static List<String> getResumoClientes(List<Cliente> clientes) {
        return clientes.stream().map(c -> String.format(
                "Nome: %s | CPF: %s | Contas: %d | Saldo Total: R$ %.2f",
                c.getNome(), c.getCpf(), c.getContas().size(), c.getSaldoTotal()
        )).toList();
    }
}
