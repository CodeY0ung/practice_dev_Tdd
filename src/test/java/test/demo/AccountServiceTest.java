package test.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

        verify(accountRepository, times(1)).findById(1l);
    }

    @Test
    @DisplayName("계좌를 등록 할 수 있다")
    void createAccountTest(){
        // given
        // BeforeEach

        // when
        accountService.createAccount(account);

        // then
        // 저장/수정/삭제는 보통 void형이라 assertThat을 사용 안함.
        // 함수가 몇 번 호출 됐는지 -> times(n)
        verify(accountRepository, times(1)).save(account);

    }

    @Test
    @DisplayName("createAccount - ArgumentCaptor 테스트")
    void craeteAccountArgumentCaptorTest(){
        //given
        //BeforEach

        //when
        accountService.createAccount(account);

        //then
        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

//        accountRepository.save()에 들어가는 account 객체를 잡아서 captor에 저장하라
        verify(accountRepository).save(captor.capture());
//        cpator에 잡힌 account 객체를 savedAccount에 저장하라
        Account savedAccount = captor.getValue();

        assertThat(savedAccount.getBalance()).isEqualTo(10000);
    }

    @Test
    @DisplayName("createAccount - doTrow 테스트")
    void createAccountDoThrowTest(){
        //given
        //BeforeEach

        //when
        doThrow(new RuntimeException("DB 오류"))
                .when(accountRepository)
                .save(account);

        //then
        assertThatThrownBy(()->accountService.createAccount(account))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("DB 오류");
    }

    @Test
    @DisplayName("updateBalance - ArgumentCaptor 테스트")
    void updateBalanceArgumentCaptorTest(){
        //given
        //BeforeEach

        ArgumentCaptor<Long> idCaaptor = ArgumentCaptor.forClass(Long.class);

        ArgumentCaptor<Integer> amountCaptor = ArgumentCaptor.forClass((Integer.class));

        //when
        accountService.updateBalance(1l,10000);

        //then
        verify(accountRepository).updateBalance(
                idCaaptor.capture(),
                amountCaptor.capture()
        );

        assertThat(idCaaptor.getValue()).isEqualTo(1l);
        assertThat(amountCaptor.getValue()).isEqualTo(10000);
    }

    @Test
    @DisplayName("음수 잔액 정보는 저장하지않는다.")
    void cannotCreateInvalidTest(){
        //given

        Account account1 = new Account(-1);

        assertThatThrownBy(()->accountService.createAccount(account1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("초기 잔액은 음수가 될 수 없습니다");

        //when && then
        //예외 발생시 repository 메소드가 실행되지 않았는지
        verify(accountRepository, never()).save(account1);
    }

}
