package test.demo;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    // 회원 조회
    public Account getAccount(Long id){
        Account account = accountRepository.findById(id);

        if(account==null){
            throw new IllegalArgumentException("계좌가 없습니다");
        }

        return account;
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
