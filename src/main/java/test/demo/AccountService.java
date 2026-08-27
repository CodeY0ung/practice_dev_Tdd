package test.demo;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account getAccount(Long id){
        Account account = accountRepository.findById(id);

        if(account==null){
            throw new IllegalArgumentException("계좌가 없습니다");
        }

        return account;
    }
}
