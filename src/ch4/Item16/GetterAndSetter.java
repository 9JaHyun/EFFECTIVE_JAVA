package ch4.Item16;

public class GetterAndSetter {
    private String name;
    private int age;

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "GetterAndSetter{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

class test {
    public static void main(String[] args) {
        GetterAndSetter member = new GetterAndSetter();
        member.setName("Kim");
        member.setAge(25);
        System.out.println(member); //
    }
}