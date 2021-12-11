package ch4.Item15;

import java.util.Arrays;

public class Be_carefulAtCollection {
    public static final int[] CAN_CHANGEABLE_LIST = {1, 2, 3, 4, 5};

}

class DeepCopyCollection {
    private static final int[] IMMUTABLE_ARRAY = {1, 2, 3, 4, 5};

    static int[] getArray() {
        return IMMUTABLE_ARRAY.clone();
    }

    void changeMethod(int index, int value) {
        int[] array = getArray();
        array[index] = value;
    }
}

class Test {
    public static void main(String[] args) {
        notImmutableListTest();
        immutableListTest();
    }

    private static void notImmutableListTest() {
        System.out.println("길이가 0 이상인 배열은 무조건 변경할 수 있다. (불변이 안통함)");
        int[] testList = Be_carefulAtCollection.CAN_CHANGEABLE_LIST;
        System.out.println("Before change = " + Arrays.toString(testList));
        testList[2] = 30;
        System.out.println("After change = " + Arrays.toString(testList));


        int[] testList2 = Be_carefulAtCollection.CAN_CHANGEABLE_LIST;
        System.out.println("Not Immutable! = " + Arrays.toString(testList2));  // expect: {1, 2, 3, 4, 5}  result: [1, 2, 30, 4, 5]
        System.out.println("");
    }

    private static void immutableListTest() {
        System.out.println("길이가 0 이상인 배열은 방어적 복사(깊은 복사)를 통해 불변성을 유지하자.");
        int[] testList = DeepCopyCollection.getArray();
        System.out.println("Before change = " + Arrays.toString(testList));
        testList[2] = 30;
        System.out.println("After change = " + Arrays.toString(testList));


        int[] testList2 = DeepCopyCollection.getArray();
        System.out.println("Immutable! = " + Arrays.toString(testList2));
        ; // expect: {1, 2, 3, 4, 5}  result: [1, 2, 30, 4, 5]
    }
}
