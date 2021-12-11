package ch2.Item1;

public class Account {
    private String owner;
    private int balance;

    public static void main(String[] args) {
        Account account1 = Account.createAccount("테스트1", 15000);
        Account account2 = Account.createAccount("테스트2", -15000);
        account1.showStatus();
        account2.showStatus();
    }

    public Account(String owner, int balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public static Account createAccount(String owner, int balance) {
        if(balance >= 0) return new NormalAccount(owner, balance);
        return new MinusAccount(owner, balance);
    }

    public void showStatus() {
        showInfo();
        System.out.printf("고객명: %s, 잔액: %d\n", owner, balance);
    }

    public void showInfo() { }
}

class NormalAccount extends Account{
    public NormalAccount(String owner, int balance) {
        super(owner, balance);
    }

    public void showInfo() {
        System.out.println("일반 통장");
    }
}

class MinusAccount extends Account{
    public MinusAccount(String owner, int balance) {
        super(owner, balance);
    }

    public void showInfo() {
        System.out.println("마이너스 통장");
    }
}
