package test.demo;

public class Calculator {

    int add(int a, int b){
        return a + b;
    }

    int subtract(int a, int b){
        return a - b;
    }

    int multi(int a, int b){
        return a * b;
    }

    int div(int a, int b){

        if(b==0){
            throw new IllegalArgumentException();
        }
        return a / b;

    }
}
