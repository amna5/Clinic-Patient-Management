public class Patient extends Person {
    private String diagnosis;
    private String doctorAssigned;

    public Patient(String id, String name, int age, String diagnosis,String doctorAssigned) {
        super(id, name, age);
        this.diagnosis = diagnosis;
        this.doctorAssigned = doctorAssigned;
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
    public String getDoctorAssigned() {
        return doctorAssigned;
    }
    public void setDoctorAssigned(String doctorAssigned) {
        this.doctorAssigned = doctorAssigned;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
