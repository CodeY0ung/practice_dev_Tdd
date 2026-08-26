package test.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    Account account;

    @BeforeEach
    void setup(){
        account = new Account(10000);
    }

    @Test
    void depositTest(){
        // given
        // 초기값 10000인 account 객체 새로 자동 생성

        // when
        account.deposit(5000);

        // then
        assertEquals(15000,account.getBalance());
    }

    @Test
    void cannotDepositZero(){
        //given
        // 초기값 10000인 account 객체 새로 자동 생성

        //when && then
        assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(0)
        );
    }

    @Test
    void cannotDepositNegative(){
        // given
        // 초기값 10000인 account 객체 새로 자동 생성

        // when && then
        assertThrows(
                IllegalArgumentException.class,
                () -> account.deposit(-1)
        );
    }

    // 정상출금
    @Test
    void withdraw(){
        // given

        //when
        account.withdraw(3000);

        //then
        assertEquals(7000,account.getBalance());
    }

    // 전액 출금
    @Test
    void withdrawAllTest(){

        account.withdraw(10000);

        assertEquals(0,account.getBalance());
    }

    @Test
    @DisplayName("잔액 보다 큰 금액을 출금할 수 없다.")
    void cannotWithdrawOverBalanceTest(){

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(10001)
        );

        assertEquals("잔액이 부족합니다.",exception.getMessage());
    }

    @Test
    void cannotAmountZero(){

        assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(0)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = {0,-1,-1000,-10000})
    @DisplayName("출금 금액은 0보다 커야한다")
    void withdrawBiggerThanZero(int amount){


        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> account.withdraw(amount)
        );

        assertEquals("출금 금액은 0보다 커야합니다.", exception.getMessage());
    }

}
