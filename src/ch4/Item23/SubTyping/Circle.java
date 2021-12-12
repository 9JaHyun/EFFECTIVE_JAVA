package ch4.Item23.SubTyping;

public class Circle implements Figure{
    final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * (radius * radius);
    }
}