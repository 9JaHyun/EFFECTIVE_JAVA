# Item9. try-finally 보다는 try-with-resources 를 사용하라
전통적으로 트랜잭션의 예외처리 방법은 try-catch-finally를 사용했습니다.
```java
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
```
이를 이용하면 