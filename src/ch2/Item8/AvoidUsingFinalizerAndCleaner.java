package ch2.Item8;

/** === Item8 : Finalizer, Cleaner 사용을 피하라 ===
 * finalizer
 *  - 예측할 수 없고, 상황에 따라 위험할 수 있어 불필요하다.
 *  - 또한 오동작, 낮은 성능, 이식성 문제로 JAVA 9에서 deprecated 되었다..
 *
 * cleaner
 *  - finalizer가 deprecated 되며 cleaner라는 것이 새로 생겨 위험성이 줄어들었다.
 *  - 하지만 여전히 예측 불가능하고, 느리고, 불필요한 작업이 되었다.
 *
 * 단점 1. 예측할 수 없다?
 *  - GC를 위해 마킹은 했지만, 언제 GC가 되는지를 알 수 없다는 것!
 *  - 결국 GC의 대상이 되었다 일 뿐 GC가 즉시 된다는 보장은 못하는 것!
 *
 * 단점 2. 인스턴스 반납이 지연될 수 있다.
 *  - 별도의 Thread로 finalizer 가 실행되는데 문제점은 이 메서드의 우선순위가 매우 낮아 실행이 늦다.
 *  - 계속해서 새로운 테스크가 밀려오면서 우선순위가 뒤로 밀리면, 쌓이고 쌓여 OutOfMemoryException이 발생할 수 있다.
 *
 * 단점 3. 실행이 아예 안될 수 있다.
 *
 * 단점 2, 3으로 메모리 누수 문제가 발생
 *
 * 단점 4. 성능 문제
 *      Try-catch 방식보다 약 50배가 걸린다.
 *
 * 단점 5. 보안 문제
 *  - 인스턴스를 생성 시 예외를 던지게 하며 finalizer를 오버헤딩 해버리면, 해당 메서드가 종료되지 않고 계속해서 finalizer를 던지게 된다.
 *  - 이때 이 finalizer를 이용하면 내부의 static field나 method에 접근이 가능해져 보안 이슈가 발생한다.
 *
 *
 * 그럼 어떻게 해야 하는데?
 *  1. 'Try-Catch-Finally' 사용해서 안전하게 반납
 *
 *  2. 'Try-with-Resource' 사용해서 안전하게 반납.
 *
 *  그럼 도데체 finally와 cleaner은 언제 사용하는데?
 *    1. 안전망
 *      - 원래 자원 반납은 클라이언트 측에서 실시를 해야 한다.
 *      - 하지만 이를 지키지 않은 경우를 대비해서 안전망(safety-net) 삼아 한번 더 closing 을 실시하는 방법을 취한다.
 *
 *    2. 네이티브 피어 정리
 *      - 네이티브 객체들은 일반적인 객체가 아니라 GC가 그 존재를 알지 못한다.
 *      - 그래서 `close` 메서드를 통해 명시를 해야 한다.
 */
public class AvoidUsingFinalizerAndCleaner {
    public static void main(String[] args) throws Throwable {
        AvoidUsingFinalizerAndCleaner avoidUsingFinalizerAndCleaner = new AvoidUsingFinalizerAndCleaner();
        avoidUsingFinalizerAndCleaner.run();
        System.gc();    // 복불복
        Thread.sleep(10000l);
        System.out.println("");

        tryFinallyBlock();
        System.out.println("");
        tryWithResourceBlock();
    }

    private void run() throws Throwable {
        FinalizerAndCleaner finalizerAndCleaner = new FinalizerAndCleaner();
        finalizerAndCleaner.hello();
    }

    private static void tryFinallyBlock() throws Exception {
        AutoCloseableSample tryWIthResourceSample = null;

        try {
            tryWIthResourceSample = new AutoCloseableSample();
            tryWIthResourceSample.hello();
        } finally {
            if (tryWIthResourceSample != null) {
                tryWIthResourceSample.close();
            }
        }
    }

    // try-with-resource를 사용하는 경우에는 알아서 자원 반납까지 해준다.
    private static void tryWithResourceBlock() throws Exception {
        try (AutoCloseableSample autoClosableSample = new AutoCloseableSample()) {
            autoClosableSample.hello();
        }
    }
}
