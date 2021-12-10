package ch1.Item5;

public class Language {
    public void info() {
        System.out.println("언어 사전");
    }
}

class English extends Language{
    @Override
    public void info() {
        System.out.println("영어 사전");
    }
}

class Korean extends Language{
    @Override
    public void info() {
        System.out.println("한국어 사전");
    }
}

class French extends Language{
    @Override
    public void info() {
        System.out.println("프랑스어 사전");
    }
}

class Japanese extends Language{
    @Override
    public void info() {
        System.out.println("일본어 사전");
    }
}
