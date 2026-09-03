package test.demo.service;

import test.demo.exception.AccountNotFountException;
import test.demo.repository.AccountRepository;
import test.demo.domain.Account;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // 회원 조회
    public Account getAccount(Long id){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new AccountNotFountException("계좌가 존재하지 않습니다."));
        return account;
    }

    // 입금
    public void deposit(Long id, int amount){
        Account account = accountRepository.findById(id)
                        .orElseThrow(()-> new AccountNotFountException("계좌가 존재하지 않습니다."));
        account.deposit(amount);
        accountRepository.save(account);
    }

    //입금
    public void withdraw(Long id, int amount){
        Account account = accountRepository.findById(id)
                .orElseThrow(()-> new AccountNotFountException("계좌가 존재하지 않습니다."));
        account.withdraw(amount);
        accountRepository.save(account);
    }

    // 회원 등록
    public void createAccount(Account account){
        if(account.getBalance() < 0) {
            throw new IllegalArgumentException("초기 잔액은 음수가 될 수 없습니다");
        }
        else{
            accountRepository.save(account);
        }
    }

    // 잔액 수정
    public void updateBalance(Long id, int amount){
        accountRepository.updateBalance(id,amount);
    }
}
