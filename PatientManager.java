import java.io.*;
import java.util.*;

public class PatientManager implements Manageable<Patient> {
    private final String FILE_NAME = "patients.txt";

    @Override
    public void add(Patient p) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true));
        writer.write(p.getId() + "," + p.getName() + "," + p.getAge() + "," + p.getDiagnosis());
        writer.newLine();
        writer.close();
    }

    @Override
    public void update(Patient updated) throws IOException {
        List<Patient> list = getAll();
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {
                list.set(i, updated);
                found = true;
                break;
            }
        }

        if (!found)
            throw new IOException("Patient not found");

        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (Patient p : list) {
            writer.write(p.getId() + "," + p.getName() + "," + p.getAge() + "," + p.getDiagnosis());
            writer.newLine();
        }
        writer.close();
    }

    @Override
    public void delete(String id) throws IOException {
        List<Patient> list = getAll();
        boolean removed = list.removeIf(p -> p.getId().equals(id));
        if (!removed)
            throw new IOException("Patient not found");

        BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME));
        for (Patient p : list) {
            writer.write(p.getId() + "," + p.getName() + "," + p.getAge() + "," + p.getDiagnosis());
            writer.newLine();
        }
        writer.close();
    }

    @Override
    public Patient search(String id) throws IOException {
        List<Patient> list = getAll();
        for (Patient p : list) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public List<Patient> getAll() throws IOException {
        List<Patient> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists())
            return list;

        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", 4);
            if (parts.length == 4) {
                String id = parts[0];
                String name = parts[1];
                int age = Integer.parseInt(parts[2]);
                String diagnosis = parts[3];
                list.add(new Patient(id, name, age, diagnosis));
            }
        }
        reader.close();
        return list;
    }
}
