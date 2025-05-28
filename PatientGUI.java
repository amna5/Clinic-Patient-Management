import java.awt.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;

public class PatientGUI extends JFrame {
    private JTextField txtId, txtName, txtAge, txtDiagnosis, txtSearchId;
    private JTextArea txtArea;
    private PatientManager manager;

    public PatientGUI() {
        manager = new PatientManager();
        setTitle("Clinic Management System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Patient Details"));

        inputPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Age:"));
        txtAge = new JTextField();
        inputPanel.add(txtAge);

        inputPanel.add(new JLabel("Diagnosis:"));
        txtDiagnosis = new JTextField();
        inputPanel.add(txtDiagnosis);

        inputPanel.add(new JLabel("Search by ID:"));
        txtSearchId = new JTextField();
        inputPanel.add(txtSearchId);

        add(inputPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnSearch = new JButton("Search");
        JButton btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.CENTER);

        // Text Area
        txtArea = new JTextArea();
        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Patient Records"));
        add(scrollPane, BorderLayout.SOUTH);

        // Button Actions
        btnAdd.addActionListener(e -> addPatient());
        btnUpdate.addActionListener(e -> updatePatient());
        btnDelete.addActionListener(e -> deletePatient());
        btnSearch.addActionListener(e -> searchPatient());
        btnRefresh.addActionListener(e -> loadPatients());

        loadPatients();
        setVisible(true);
    }

    private void addPatient() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String diagnosis = txtDiagnosis.getText().trim();

        if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || diagnosis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            Patient patient = new Patient(id, name, age, diagnosis);
            manager.add(patient);
            JOptionPane.showMessageDialog(this, "Patient added successfully.");
            clearFields();
            loadPatients();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updatePatient() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String diagnosis = txtDiagnosis.getText().trim();

        if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || diagnosis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            Patient patient = new Patient(id, name, age, diagnosis);
            manager.update(patient);
            JOptionPane.showMessageDialog(this, "Patient updated successfully.");
            clearFields();
            loadPatients();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a number.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deletePatient() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the ID to delete.");
            return;
        }

        try {
            manager.delete(id);
            JOptionPane.showMessageDialog(this, "Patient deleted successfully.");
            clearFields();
            loadPatients();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void searchPatient() {
        String id = txtSearchId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the ID to search.");
            return;
        }

        try {
            Patient patient = manager.search(id);
            if (patient != null) {
                txtId.setText(patient.getId());
                txtName.setText(patient.getName());
                txtAge.setText(String.valueOf(patient.getAge()));
                txtDiagnosis.setText(patient.getDiagnosis());
            } else {
                JOptionPane.showMessageDialog(this, "Patient not found.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadPatients() {
        try {
            List<Patient> patients = manager.getAll();
            txtArea.setText("");
            for (Patient p : patients) {
                txtArea.append("ID: " + p.getId() + ", Name: " + p.getName() +
                        ", Age: " + p.getAge() + ", Diagnosis: " + p.getDiagnosis() + "\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtDiagnosis.setText("");
        txtSearchId.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientGUI());
    }
}
