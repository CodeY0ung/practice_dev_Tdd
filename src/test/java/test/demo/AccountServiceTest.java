package test.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    Account account;
    @BeforeEach
    void setUp(){
        account = new Account(10000);
    }

    @Test
    @DisplayName("계좌를 조회할 수 있다")
    void findAccountTest(){
        // given
        // BeforeEach

        // stub
        // findById가 db를 조회해서 account를 반환하는게 아니라
        // BeforeEach로 자동 생성된 account 객체를 반환하도록.
        // DB가 없어도 된다.
        when(accountRepository.findById(1l))
                .thenReturn(account);

        // when
        // 아까 생성한 account를 repository가 반환할거임.
        Account result = accountService.getAccount(1l);

        // then
        // 결과 검증
        assertThat(result).isEqualTo(account);

        // 행동 검증
        verify(accountRepository).findById(1l);
    }

    @Test
    @DisplayName("존재하지 않는 계좌는 조회할 수 없다")
    void cannotFindNotExistingAcount(){
        // given
        // BeforeEach

        // when(repository 메소드).thenReturn(반환)
        when(accountRepository.findById(1l))
                .thenReturn(null);

        // when && then
        assertThatThrownBy(()->accountService.getAccount(1l))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("계좌가 없습니다");

        verify(accountRepository).findById(1l);
    }

    @Test
    @DisplayName("계좌를 등록 할 수 있다")
    void createAccountTest(){
        // given
        // BeforeEach

        // when

    }

}
