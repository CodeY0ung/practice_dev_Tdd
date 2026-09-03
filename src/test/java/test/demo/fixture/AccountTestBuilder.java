package test.demo.fixture;

import test.demo.domain.Account;

public class AccountTestBuilder {

    private int balance = 10000;

    public static AccountTestBuilder anAccount(){
        return new AccountTestBuilder();
    }

    public AccountTestBuilder balance(int balance){
        this.balance = balance;
        return this;
    }

    public Account build(){
        return new Account(balance);
    }
}
