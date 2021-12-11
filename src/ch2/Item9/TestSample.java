package ch2.Item9;

public class TestSample {
    public static void main(String[] args) throws Exception {
//        beforeTryFinally();
//        singleTryFinally();
//        nestedTryFinally();
//        tryWithResource();
        tryWithResource2();
    }

    private static void tryWithResource2() throws Exception {
        try (Try_Finally resource = new Try_Finally();
             Try_Finally resource2 = new Try_Finally()) {
            resource.doSomething();
            resource2.doSomething();
        }
    }

    private static void beforeTryFinally() throws Exception {
        Try_Finally resource = new Try_Finally();
        resource.doSomething();
        resource.close();
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

    private static void nestedTryFinally() throws Exception {
        Try_Finally resource = null;
        try {
            resource = new Try_Finally();
            resource.doSomething();             // firstError
            resource = null;
            try {
                resource = new Try_Finally();
                resource.doSomething();         // firstError
            } finally {
                if (resource != null) {
                    resource.close();           // secondError
                }
            }
        } finally {
            resource.close();                   // secondError
        }
    }

    private static void tryWithResource() throws Exception {
        try(Try_Finally resource = new Try_Finally()){
            resource.doSomething();
        }
    }
}
