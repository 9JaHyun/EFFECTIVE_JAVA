package ch4.Item17;

public final class Complex{
    // 불변성 덕분에 자주 쓰이는 값을 상수로 제공할 수 있다. => 정적 팩토리로 제공도 가능.
    public static final Complex Zero = new Complex(0, 0);
    public static final Complex ONE = new Complex(1, 0);    // 실수 1
    public static final Complex I = new Complex(0, 1);      // 허수 i

    // 3, 4. 모든 필드를 final로 선언
    private final double re;
    private final double im;

    private Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static Complex of(double re, double im) {
        return new Complex(re, im);
    }

    // 접근자 메서드
    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    // 새로운 인스턴스 반환 (함수형 인터페이스)
    public Complex plus(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex minus(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    public Complex time(Complex c) {
        return new Complex(re * c.re - im * c.im,
                re * c.im + im * c.re);
    }

    public Complex divideBy(Complex c) {
        double tmp = c.re * c.re + c.im * c.im;
        return new Complex((re * c.re + im * c.im) / tmp,
                (im * c.re + re * c.im) / tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Complex)) return false;
        Complex c = (Complex) o;
        return Double.compare(c.re, re) == 0
                && Double.compare(c.im, im) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }

    @Override
    public String toString() {
        return "(" + re + " + " + im + ")";
    }
}