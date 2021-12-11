package ch3.Item10;

import ch3.Item10.point.ColorPoint;
import ch3.Item10.point.Point;

import java.awt.*;
public class EqualsSample {
    public static void main(String[] args) {
        pointEquals();
        colorPointEquals();
    }

    static void pointEquals() {
        ch3.Item10.point.Point point1 = new ch3.Item10.point.Point(1, 2);
        ch3.Item10.point.Point point2 = new ch3.Item10.point.Point(3, 2);
        ch3.Item10.point.Point point3 = new ch3.Item10.point.Point(1, 2);

        boolean result1 = point1.equals(point2);
        boolean result2 = point1.equals(point3);
        boolean result3 = point2.equals(point3);

        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    static void colorPointEquals() {
        ColorPoint colorPoint1 = new ColorPoint(1, 2, Color.RED);
        ColorPoint colorPoint2 = new ColorPoint(1, 2, Color.RED);

        ch3.Item10.point.Point point1 = new Point(1, 2);

        System.out.println(colorPoint1.equals(colorPoint2));
        System.out.println(point1.equals(colorPoint1)); // Color 정보가 무시된다.
    }

}
