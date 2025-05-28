import java.awt.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PatientGUI extends JFrame {
    private JTextField txtId, txtName, txtAge, txtDiagnosis, txtSearchId;
    private JComboBox<String> comboDoctor;
    private JTable table;
    private DefaultTableModel tableModel;
    private PatientManager manager;

    public PatientGUI() {
        manager = new PatientManager();
        setTitle("🏥 Clinic Management System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Centers the frame

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
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

        inputPanel.add(new JLabel("Doctor Assigned:"));
        comboDoctor = new JComboBox<>(new String[] { "Dr. Smith", "Dr. Johnson", "Dr. Lee", "Dr. Patel" });
        inputPanel.add(comboDoctor);


        inputPanel.add(new JLabel("Search by ID:"));
        txtSearchId = new JTextField();
        inputPanel.add(txtSearchId);

        add(inputPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
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

        // Table Panel
        String[] columnNames = { "ID", "Name", "Age", "Diagnosis" , "Doctor Assigned"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Patient Records"));

        // Combine buttonPanel and table into one panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // Button Actions
        btnAdd.addActionListener(e -> addPatient());
        btnUpdate.addActionListener(e -> updatePatient());
        btnDelete.addActionListener(e -> deletePatient());
        btnSearch.addActionListener(e -> searchPatient());
        btnRefresh.addActionListener(e -> loadPatients());

        // Table row click listener to populate form
        table.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                txtId.setText(table.getValueAt(table.getSelectedRow(), 0).toString());
                txtName.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
                txtAge.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
                txtDiagnosis.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
                comboDoctor.setSelectedItem(table.getValueAt(table.getSelectedRow(), 4).toString());
            }
        });

        loadPatients();
        setVisible(true);
    }

    private void addPatient() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String diagnosis = txtDiagnosis.getText().trim();
        String DoctorAssigned = comboDoctor.getSelectedItem().toString();

        if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || diagnosis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            Patient patient = new Patient(id, name, age, diagnosis,DoctorAssigned);
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
        String DoctorAssigned = comboDoctor.getSelectedItem().toString();

        if (id.isEmpty() || name.isEmpty() || ageStr.isEmpty() || diagnosis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            Patient patient = new Patient(id, name, age, diagnosis,DoctorAssigned);
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
                comboDoctor.setSelectedItem(patient.getDoctorAssigned());
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
            tableModel.setRowCount(0); // Clear previous data
            for (Patient p : patients) {
                Object[] row = { p.getId(), p.getName(), p.getAge(), p.getDiagnosis(), p.getDoctorAssigned() };
                tableModel.addRow(row);
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
        comboDoctor.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PatientGUI());
    }
}
