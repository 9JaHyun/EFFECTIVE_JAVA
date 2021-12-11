package ch2.Item1;

public class Rectangle {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Rectangle createRectangle(int width, int height) {
        return new Rectangle(width, height);
    }

    public static Rectangle createSquare(int width) {
        return new Rectangle(width, width);
    }

    public static void main(String[] args) {
        Rectangle rectangle = Rectangle.createRectangle(10, 5);
        Rectangle square = Rectangle.createSquare(10);
    }
}
