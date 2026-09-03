package test.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import test.demo.exception.InsufficientBalanceException;
import test.demo.exception.InvalidAmountException;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int balance;

    protected Account() {
    }

    public Account(int balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void deposit(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "입금 금액은 0보다 커야 합니다."
            );
        }

        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "출금 금액은 0보다 커야 합니다."
            );
        }

        if (amount > balance) {
            throw new InsufficientBalanceException(
                    "잔액이 부족합니다."
            );
        }

        balance -= amount;
    }
}