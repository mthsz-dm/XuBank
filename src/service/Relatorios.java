import model.Cliente;
import model.Conta;

import java.util.*;
import java.util.stream.Collectors;

public class Relatorios {
    
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
    public static void exibirMovimentacoes(List<Cliente> clientes) {
    for (Cliente cliente : clientes) {
        System.out.println("Cliente: " + cliente.getNome());
        for (Conta conta : cliente.getContas()) {
            System.out.println("Conta #" + conta.getNumero() + " (" + conta.getClass().getSimpleName() + ")");
            for (String operacao : conta.getHistoricoOperacoes()) {
                System.out.println(" - " + operacao);
            }
        }
        System.out.println("--------------------------------------------------");
    }
}

    
}
