public class Patient extends Person {
    private String diagnosis;

    public Patient(String id, String name, int age, String diagnosis) {
        super(id, name, age);
        this.diagnosis = diagnosis;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
