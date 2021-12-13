package ch5.Item28;

import java.util.ArrayList;
import java.util.List;

public class Covariant {
    // 런타임에서 에러가 난다.
    void test1() {
        Object[] objectArray = new Long[1];
        objectArray[0] = "타입이 달라 넣을 수 없다.";
    }

    // 컴파일러에서 에러가 난다
    void test2() {
//        List<Object> objectList = new ArrayList<Long>();    // 불공변으로 만들어지는 컴파일 에러
//        objectList.add("타입이 달라 넣을 수 없다");
    }
}
