import model.*;
import service.Banco;
import service.Relatorios;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Banco banco = new Banco();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n===== XuBank Menu =====");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Abrir Conta");
            System.out.println("3. Realizar Depósito");
            System.out.println("4. Realizar Saque");
            System.out.println("5. Ver Extratos");
            System.out.println("6. Ver Relatórios do Banco");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> abrirConta();
                case 3 -> realizarDeposito();
                case 4 -> realizarSaque();
                case 5 -> verExtratos();
                case 6 -> verRelatorios();
                case 0 -> System.out.println("Encerrando o sistema.");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Cliente cliente = new Cliente(nome, cpf, senha);
        banco.cadastrarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private static Cliente encontrarCliente() {
        System.out.print("Digite o CPF do cliente: ");
        String cpf = scanner.nextLine();

        for (Cliente c : banco.getClientes()) {
            if (c.getCpf().equals(cpf)) {
                return c;
            }
        }
        System.out.println("Cliente não encontrado.");
        return null;
    }

    private static void abrirConta() {
        Cliente cliente = encontrarCliente();
        if (cliente == null) return;

        System.out.print("Tipo da conta (corrente, poupanca, rendafixa, investimento): ");
        String tipo = scanner.nextLine();

        Conta novaConta = banco.abrirConta(cliente, tipo);
        if (novaConta != null) {
            System.out.println("Conta criada com sucesso! Nº " + novaConta.getNumero());
        } else {
            System.out.println("Tipo de conta inválido.");
        }
    }

    private static void realizarDeposito() {
        Cliente cliente = encontrarCliente();
        if (cliente == null) return;

        Conta conta = selecionarConta(cliente);
        if (conta == null) return;

        System.out.print("Valor para depósito: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        conta.depositar(valor);
        System.out.println("Depósito realizado!");
    }

    private static void realizarSaque() {
        Cliente cliente = encontrarCliente();
        if (cliente == null) return;

        Conta conta = selecionarConta(cliente);
        if (conta == null) return;

        System.out.print("Valor para saque: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (conta.sacar(valor)) {
            System.out.println("Saque realizado!");
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    private static Conta selecionarConta(Cliente cliente) {
        var contas = cliente.getContas();
        if (contas.isEmpty()) {
            System.out.println("Cliente não possui contas.");
            return null;
        }

        System.out.println("Contas disponíveis:");
        for (int i = 0; i < contas.size(); i++) {
            System.out.printf("%d. %s - Nº %d\n", i + 1, contas.get(i).getClass().getSimpleName(), contas.get(i).getNumero());
        }

        System.out.print("Escolha a conta: ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        if (escolha < 1 || escolha > contas.size()) {
            System.out.println("Escolha inválida.");
            return null;
        }

        return contas.get(escolha - 1);
    }

    private static void verExtratos() {
        Cliente cliente = encontrarCliente();
        if (cliente == null) return;

        System.out.println("Extratos:");
        System.out.println(cliente.getExtratos());
    }
    private static void verRelatorios() {
        int escolha;
        do {
            System.out.println("\n===== Relatórios =====");
            System.out.println("1. Saldo médio, maior e menor");
            System.out.println("2. Custódia por tipo de conta");
            //System.out.println("3. Top N clientes com maior saldo");
            System.out.println("3. Clientes com saldo negativo");
            System.out.println("4. Contas com saldo abaixo de um valor");
            System.out.println("5. Resumo geral de todos os clientes");
            System.out.println("6. Exibir movimentações dos clientes");
            System.out.println("0. Voltar");
            System.out.print("Escolha: ");
            escolha = scanner.nextInt();
            scanner.nextLine();

            switch (escolha) {
                case 1 -> {
                    System.out.printf("Saldo médio das contas: R$ %.2f\n", banco.getSaldoMedio());
                    Cliente maior = banco.getClienteMaiorSaldo();
                    Cliente menor = banco.getClienteMenorSaldo();
                    if (maior != null) {
                        System.out.printf("Cliente com maior saldo: %s (R$ %.2f)\n", maior.getNome(), maior.getSaldoTotal());
                    }
                    if (menor != null) {
                        System.out.printf("Cliente com menor saldo: %s (R$ %.2f)\n", menor.getNome(), menor.getSaldoTotal());
                    }
                }
                case 2 -> {
                    System.out.println("Custódia por tipo de conta:");
                    banco.getCustodiaPorTipo().forEach((tipo, total) ->
                        System.out.printf("- %s: R$ %.2f\n", tipo, total));
                }

                case 3 -> {
                    System.out.println("Clientes com saldo negativo:");
                    var negativos = Relatorios.getClientesNegativados(banco.getClientes());
                    negativos.forEach(c -> System.out.printf("- %s | R$ %.2f\n", c.getNome(), c.getSaldoTotal()));
                }
                case 4 -> {
                    System.out.print("Informe o valor limite: R$ ");
                    double limite = scanner.nextDouble();
                    scanner.nextLine();
                    var contas = Relatorios.getContasComSaldoAbaixo(banco.getContas(), limite);
                    contas.forEach(c -> System.out.printf("- Conta %d (%s) | Saldo: R$ %.2f\n",
                            c.getNumero(), c.getClass().getSimpleName(), c.getSaldo()));
                }
                case 5 -> {
                    System.out.println("Resumo geral dos clientes:");
                    Relatorios.getResumoClientes(banco.getClientes()).forEach(System.out::println);
                }
                case 6-> {
                    Relatorios.getMovimentacoes(banco.getClientes());
                }
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        } while (escolha != 0);
    }
    
}
