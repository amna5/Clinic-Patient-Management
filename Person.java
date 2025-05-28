public abstract class Person {
    protected String id;
    protected String name;
    protected int age;

    public Person(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public abstract String getId();

    public abstract String getName();

    public abstract int getAge();
}
