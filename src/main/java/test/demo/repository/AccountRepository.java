package test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import test.demo.domain.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findById(Long id);

    void save(Account account);

    void updateBalance(long id, int amount);
}
