package test.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import test.demo.domain.Account;
import test.demo.exception.AccountNotFountException;
import test.demo.exception.InsufficientBalanceException;
import test.demo.exception.InvalidAmountException;
import test.demo.repository.AccountRepository;
import test.demo.service.AccountService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    Account account;
    @BeforeEach
    void setUp(){
        account = new Account(1l, 10000);
    }

    @Test
    @DisplayName("계좌를 조회할 수 있다")
    void canSelectAccountTest(){
        //given
        //BeforeEach
        when(accountRepository.findById(1l))
                .thenReturn(account);

        //when
        Account result = accountService.getAccount(1l);

        //then
        assertThat(result).isEqualTo(account);
    }

    @Test
    @DisplayName("존재하지 않는 계좌")
    void notExistAccountTest(){
        //given
        //BeforeEach
        when(accountRepository.findById(999l))
                .thenReturn(null);
        //when&&then
        assertThatThrownBy(()->accountService.getAccount(999l))
                .isInstanceOf(AccountNotFountException.class)
                .hasMessage("계좌가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("정상 입금")
    void depositTest(){
        //given
        //BeforeEach
        when(accountRepository.findById(1l)).thenReturn(account);

        //when
        accountService.deposit(1l,5000);

        //then
        assertThat(account.getBalance()).isEqualTo(15000);
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("존재하지 않는 계좌 입금")
    void notExistAccountDepositTest(){
        //given
        //BeforeEach
        when(accountRepository.findById(999l))
                .thenReturn(null);
        //when && then
        assertThatThrownBy(()->accountService.deposit(999l,5000))
                .isInstanceOf(AccountNotFountException.class)
                .hasMessage("계좌가 존재하지 않습니다.");

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("잘못된 입금 금액")
    void cannotDepositIncorrectAmount(){
        //given
        //BeforeEach
        when(accountRepository.findById(account.getId()))
                .thenReturn(account);

        //when&&then
        assertThatThrownBy(() -> accountService.deposit(account.getId(), 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입금 금액은 0보다 커야 합니다.");

        verify(accountRepository, never()).save(any(Account.class));
    }

    // --------------------- 실습 2 -------------------------

    @Test
    @DisplayName("계좌에서 정상적으로 출금할 수 있다")
    void withdrawTest(){
        //given
        //BeforeEach
        when(accountRepository.findById(1L))
                .thenReturn(account);
        //when
        accountService.withdraw(1L,3000);

        //then
        assertThat(account.getBalance()).isEqualTo(7000);
        verify(accountRepository).save(account);

    }

    @Test
    @DisplayName("잔액 전부를 출금할 수 있다")
    void withdrawAllTest(){
        //given
        //BeforeEach
        when(accountRepository.findById(1L))
                .thenReturn(account);

        //when
        accountService.withdraw(1L,10000);

        //then
        assertThat(account.getBalance()).isEqualTo(0);
        verify(accountRepository).save(account);
    }

    @Test
    @DisplayName("잔액보다 많은 금액을 출금할 수 없다")
    void cannotWithdrawOverThanBalance(){
        //given
        //BeforeEach
        when(accountRepository.findById(1L))
                .thenReturn(account);

        //when&&then
        assertThatThrownBy(()->accountService.withdraw(1L,10001))
                .isInstanceOf(InsufficientBalanceException.class)
                .hasMessage("잔액이 부족합니다.");
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("0원은 출금할 수 없다")
    void cannotWithdrawMoreThanBalance(){
        //given
        //BeforeEach
        when(accountRepository.findById(1L))
                .thenReturn(account);
        //when&&then
        assertThatThrownBy(()->accountService.withdraw(1L,0))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("출금 금액은 0보다 커야합니다.");
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("음수는 출금할 수 없다")
    void cannotWithdrawNegativeTest(){
        //given
        //BeforeEach
        when(accountRepository.findById(1L))
                .thenReturn(account);
        //when&&then
        assertThatThrownBy(()->accountService.withdraw(1L,-1))
                .isInstanceOf(InvalidAmountException.class)
                .hasMessage("출금 금액은 0보다 커야합니다.");
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    @DisplayName("존재하지 않는 계좌에서는 출금할 수 없다")
    void cannotWithdrawFromNotExistAccountTest(){
        //given
        when(accountRepository.findById(1L))
                .thenReturn(null);
        //when&&then
        assertThatThrownBy(()->accountService.withdraw(1L,3000))
                .isInstanceOf(AccountNotFountException.class)
                .hasMessage("계좌가 존재하지 않습니다.");
        verify(accountRepository, never()).save(any(Account.class));
    }

}
