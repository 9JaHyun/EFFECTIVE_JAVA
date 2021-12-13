package ch7.Item42;

import java.util.function.DoubleBinaryOperator;

// 람다는 이름이 따로 없기 때문에 문서화를 하지 못한다.
// 따라서 코드 자체로 동작이 명확히 설명되지 않거나, 코드 줄 수가 너무 많아지면 람다를 사용하면 안된다!
public enum OperationLambda {
    PLUS("+", (x, y) -> x + y),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y);

    private final String symbol;
    // double 연산에 관한 함수형 인터페이스
    private final DoubleBinaryOperator op;

    OperationLambda(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }
}