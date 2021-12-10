package ch1.item1;

public class BirdV2 {
    public BirdV2() {
    }

    public static BirdV2 createDove() {
        return new dove();
    }

    public static BirdV2 createPenguin() {
        return new Penguin();
    }

    void fly() {
        System.out.println("날다!");
    }

    public static void main(String[] args) {
        BirdV2 bird = new BirdV2();
        BirdV2 dove = BirdV2.createDove();
        BirdV2 penguin = BirdV2.createPenguin();
    }
}

class dove extends BirdV2 {
    @Override
    void fly() {
        System.out.println("비둘기가 날다!");
    }
}

class Penguin extends BirdV2 {
    @Override
    void fly() {
        System.out.println("펭귄은 못난다...");
    }
}
