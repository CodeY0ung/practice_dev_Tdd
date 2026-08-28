package test.demo;

public interface AccountRepository {

    Account findById(Long id);

    void save(Account account);

    void updateBalance(long id, int amount);
}
