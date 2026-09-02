package test.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;
import test.demo.exception.InsufficientBalanceException;
import test.demo.exception.InvalidAmountException;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int balance;

    protected Account(){

    }

    public Account(Long id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId(){
        return id;
    }
    public int getBalance(){
        return balance;
    }

    // 입금
    public void deposit(int amount){
        if(amount <= 0){
            throw new IllegalArgumentException("입금 금액은 0보다 커야 합니다.");
        }
        balance += amount;
    }

    // 출금
    public void withdraw(int amount){
        if(amount > balance){
            throw new InsufficientBalanceException("잔액이 부족합니다.");
        }
        else if(amount <= 0){
            throw new InvalidAmountException("출금 금액은 0보다 커야합니다.");
        }
        balance -= amount;
    }
}
