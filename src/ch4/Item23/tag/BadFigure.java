package ch4.Item23.tag;

// 쓸데없는 군더더기들이 많다.
public class BadFigure {
    // 1. 열거 타입 선언
    enum Shape { RECTANGLE, CIRCLE};

    // 2. 태그 필드
    final Shape shape;

    double length;
    double width;

    double radius;

    BadFigure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    public BadFigure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    // 3. 쓸모없는 switch
    double area() {
        return switch (shape) {
            case RECTANGLE -> length * width;
            case CIRCLE -> Math.PI * radius * radius;
            default -> throw new AssertionError(shape);
        };
    }
}
