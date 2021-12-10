package ch1.Item9;

public class Try_Finally implements AutoCloseable{
    public static void main(String[] args) throws Exception {
//        singleTryFinally();
        nestedTryFinally();
    }

    private static void nestedTryFinally() throws Exception {
        Try_Finally resource = new Try_Finally();
        try {
            resource.doSomething();
            resource = null;
            try {
                resource = new Try_Finally();
                resource.doSomething();
            } finally {
                if (resource != null) {
                    resource.close();
                }
            }
        } finally {
            resource.close();   // 반드시 실행
        }
    }

    private static void singleTryFinally() throws Exception {
        Try_Finally resource = null;
        try{
            resource = new Try_Finally();
            resource.doSomething();
        } finally {
            if (resource != null) {
                resource.close();
            }
        }
    }

    public void doSomething() {
        System.out.println("do something");
        throw new FirstException();
    }

    @Override
    public void close() throws Exception {
        System.out.println("clear resource");
        throw new SecondException();
    }
}
