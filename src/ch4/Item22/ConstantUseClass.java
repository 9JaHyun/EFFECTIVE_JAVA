package ch4.Item22;

public class ConstantUseClass implements WrongConstant{
    public static void main(String[] args) {
        // 이렇게 사용하면 인터페이스에 종속화 되버린다. 그래서...
        double boltzmannConstant = BOLTZMANN_CONSTANT;

        // 유틸리티 클래스에 공개를 한 뒤 상수를 사용하자!
        double electronMass = ConstantListClass.ELECTRON_MASS;
    }

}

// 그래서 유틸리티 클래스에 공개를 해버리자!
class ConstantListClass {
    public static final double AVOGADROS_NUMBER = 6.022_140_857e23;
    public static final double BOLTZMANN_CONSTANT = 1.380_648_52e-23;
    public static final double ELECTRON_MASS = 9.109_383_56e-31;

    private ConstantListClass() {}
}