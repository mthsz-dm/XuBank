package tests;

import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ContaTest {

    @Test
    public void testDepositoSemSaldoNegativo() {
        Cliente cliente = new Cliente("Ana", "111", "senha");
        ContaCorrente conta = new ContaCorrente(cliente, 100);
        conta.depositar(200);
        Assertions.assertEquals(200, conta.getSaldo(), 0.01);
    }

    @Test
    public void testDepositoComSaldoNegativo() {
        Cliente cliente = new Cliente("Carlos", "222", "senha");
        ContaCorrente conta = new ContaCorrente(cliente, 100);
        conta.sacar(50);
        conta.depositar(100);
        Assertions.assertEquals(100 - 11.5 - 50, conta.getSaldo(), 0.01);
    }

    @Test
    public void testSaqueComLimiteCredito() {
        Cliente cliente = new Cliente("João", "333", "senha");
        ContaCorrente conta = new ContaCorrente(cliente, 100);
        boolean sucesso = conta.sacar(80);
        Assertions.assertTrue(sucesso);
        Assertions.assertEquals(-80, conta.getSaldo(), 0.01);
    }

    @Test
    public void testSaqueExcedeLimite() {
        Cliente cliente = new Cliente("Maria", "444", "senha");
        ContaCorrente conta = new ContaCorrente(cliente, 100);
        boolean sucesso = conta.sacar(200); 
        Assertions.assertFalse(sucesso);
        Assertions.assertEquals(0, conta.getSaldo(), 0.01);
    }
}
