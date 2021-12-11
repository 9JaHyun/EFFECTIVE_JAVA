package ch3.Item10.Q1_Rectangle;

public class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getArea() {
        return width * height;
    }

    public Rectangle resize(int width, int height) {
        return new Rectangle(width, height);
    }
}
