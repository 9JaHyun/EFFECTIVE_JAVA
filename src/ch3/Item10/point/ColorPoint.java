package ch3.Item10.point;

import java.awt.*;
import java.awt.Point;

public class ColorPoint extends Point {
    private Color color;

    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    // 얘를 도데체 어떻게 처리해야 하지...?
    // Case1: 재정의 하지 않는 경우 => Color 정보가 무시된다. (엉터리 + 추이성 위반)
    // Case2: 재정의 하는 경우 => ColorPoint, Point 두개를 비교했을 때 equals()의 구현이 달라서 문제가 발생한다!
//                          => point.equals(colorPoint)의 경우에는 Color 정보를 사용하지 않기 떄문에 참이 나올 수 있는 반면..
//                          => colorPoint.equals(point)의 경우에는 Color 정보를 사용하기 떄문에 거짓이 나올 수 있다.       => 결국 대칭성이 무너지게 된다.

    // 결론 : 인스턴스를 확장하면서 equals() 규약을 위반하지 않는 방법은 없다.
    // 그나마 방법이라면 컴포지션을 이용하는 것!

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
