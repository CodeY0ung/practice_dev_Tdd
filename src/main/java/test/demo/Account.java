package test.demo;

public class Account {

    private Long id;
    private int balance;

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
            throw new IllegalArgumentException("잔액이 부족합니다.");
        }
        if(amount <= 0){
            throw new IllegalArgumentException("출금 금액은 0보다 커야합니다.");
        }
        balance -= amount;
    }
}
