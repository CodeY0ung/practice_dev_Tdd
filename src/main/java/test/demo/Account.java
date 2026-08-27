package test.demo;

public class Account {
    //잔액
    private int balance;

    //생성자
    public Account(int balance){
        this.balance = balance;
    }

    //잔액출력
    public int getBalance(){
        return balance;
    }

    // 입금
    public void deposit(int amount){

        if(amount <= 0){
            throw new IllegalArgumentException("입금액은 0원 이상이어야 합니다.");
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
