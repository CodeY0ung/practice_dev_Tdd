package test.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    Calculator calculator;

    // 매 테스트마다 새로운 calculator 객체 자동 생성
    @BeforeEach
    void setup(){
        calculator = new Calculator();
    }

    @Test
    void addTest(){
        // given
        // Foreach로 공통 객체 자동 생성

        // when
        int result = calculator.add(2,4);

        // then
        assertEquals(6,result);
    }

    @Test
    void subTest(){
        // given
        // Foreach로 공통 객체 자동 생성

        // when
        int result = calculator.subtract(2,4);

        // then
        assertEquals(-2,result);
    }

    // 올바른 예외가 터지는지 test
    @Test
    void divExceptionTest(){

        assertThrows(
                // 이런 예외가 터져야 한다. (예외 종류 일치시키기)
                IllegalArgumentException.class,

                // 이런 경우에
                () -> calculator.div(10,0)
        );

    }
}
