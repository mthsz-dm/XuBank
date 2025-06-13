package model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private String cpf;
    private String senha;
    private List<Conta> contas;

    public Cliente(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.contas = new ArrayList<>();
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public List<Conta> getContas() {
        return contas;
    }

    public double getSaldoTotal() {
        return contas.stream().mapToDouble(Conta::getSaldo).sum();
    }

    public String getExtratos() {
        StringBuilder sb = new StringBuilder();
        for (Conta conta : contas) {
            sb.append(conta.getSaldoExtrato()).append("\n");
        }
        return sb.toString();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}
