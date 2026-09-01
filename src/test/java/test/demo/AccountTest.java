package test.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import test.demo.domain.Account;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AccountTest {

    Account account;

    @BeforeEach
    void setup(){
        account = new Account(1l,10000);
    }

    @Test
    @DisplayName("입금한 금액 만큼 잔액이 증가한다.")
    void depositTest(){
        // given
        // 초기값 10000인 account 객체 새로 자동 생성

        // when
        account.deposit(5000);

        // then
        assertThat(account.getBalance())
                .isEqualTo(15000);
    }

    @Test
    void cannotDepositZero(){
        //given
        // 초기값 10000인 account 객체 새로 자동 생성

        //when && then
        assertThatThrownBy(()->account.deposit(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입금액은 0원 이상이어야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1,-100,-1000})
    void cannotDepositNegative(int amount){
        // given
        // 초기값 10000인 account 객체 새로 자동 생성

        // when && then
        assertThatThrownBy(()->account.deposit(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입금액은 0원 이상이어야 합니다.");

    }

    // 정상출금
    @Test
    void withdraw(){
        // given

        //when
        account.withdraw(3000);

        //then
        assertThat(account.getBalance()).isEqualTo(7000);
    }

    // 전액 출금
    @Test
    void withdrawAllTest(){

        account.withdraw(10000);

        assertThat(account.getBalance()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(ints={10001,100001})
    @DisplayName("잔액 보다 큰 금액을 출금할 수 없다.")
    void cannotWithdrawOverBalanceTest(int amount){

        assertThatThrownBy(()->account.withdraw(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잔액이 부족합니다.");
    }

    @Test
    @DisplayName("0원을 출금할 수 없다")
    void cannotWithdrawZero(){

        assertThatThrownBy(()->account.withdraw(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출금 금액은 0보다 커야합니다.");

    }

    @ParameterizedTest
    @ValueSource(ints = {0,-1,-1000,-10000})
    @DisplayName("출금 금액은 0보다 커야한다")
    void withdrawBiggerThanZero(int amount){

        assertThatThrownBy(()->account.withdraw(amount))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출금 금액은 0보다 커야합니다.");
    }

}
