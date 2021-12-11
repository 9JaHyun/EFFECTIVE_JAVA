package ch2.Item1;

public class BirdV1 {
    void fly() {
        System.out.println("날다!");
    }

    public static void main(String[] args) {
        BirdV1 birdV1 = new BirdV1();
        BirdV1 dove = new doveV1();
        BirdV1 penguin = new PenguinV1();

        birdV1.fly();
        dove.fly();
        penguin.fly();
    }
}

class doveV1 extends BirdV1 {
    @Override
    void fly() {
        System.out.println("비둘기가 날다!");
    }
}

class PenguinV1 extends BirdV1 {
    @Override
    void fly() {
        System.out.println("펭귄은 못난다...");
    }
}
