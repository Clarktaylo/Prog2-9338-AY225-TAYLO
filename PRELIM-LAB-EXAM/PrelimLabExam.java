// Programmer Identifier: Clark Kenneth Taylo 16-2957-903

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class PrelimLabExam extends JFrame {

    private JTable table;
    private DefaultTableModel model, masterModel;

    private JTextField idField, fnField, lnField;
    private JTextField lab1, lab2, lab3, prelim, attendance;

    private JButton viewBtn, addBtn, updateBtn, deleteBtn;
    private JButton saveBtn, backBtn;

    private JPanel gradePanel, crudButtons;

    private boolean isUpdateMode = false;
    private final String CSV_FILE = "PRELIM-LAB-EXAM/MOCK_DATA.csv";

    public PrelimLabExam() {
        setTitle("Student Records - Clark Kenneth Taylo (16-2957-903)");
        setSize(960, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        /* ================= TOP ================= */
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBorder(BorderFactory.createEmptyBorder(15, 15, 8, 15));
        add(wrapper, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        /* BASIC INPUTS */
        JPanel basic = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        idField = new JTextField(10);
        fnField = new JTextField(12);
        lnField = new JTextField(12);

        basic.add(new JLabel("Student ID"));
        basic.add(idField);
        basic.add(new JLabel("First Name"));
        basic.add(fnField);
        basic.add(new JLabel("Last Name"));
        basic.add(lnField);

        /* ================= GRADE PANEL ================= */
        gradePanel = new JPanel(new GridBagLayout());
        gradePanel.setBorder(BorderFactory.createTitledBorder("Grades"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(15, 15, 8, 15);
        g.fill = GridBagConstraints.HORIZONTAL;

        lab1 = new JTextField(6);
        lab2 = new JTextField(6);
        lab3 = new JTextField(6);
        prelim = new JTextField(6);
        attendance = new JTextField(6);

        // Labels
        g.gridy = 0;
        g.gridx = 0; gradePanel.add(new JLabel("WORKLAB 1"), g);
        g.gridx = 1; gradePanel.add(new JLabel("WORKLAB 2"), g);
        g.gridx = 2; gradePanel.add(new JLabel("WORKLAB 3"), g);
        g.gridx = 3; gradePanel.add(new JLabel("PRELIM"), g);
        g.gridx = 4; gradePanel.add(new JLabel("ATTENDANCE"), g);

        // Inputs
        g.gridy = 1;
        g.gridx = 0; gradePanel.add(lab1, g);
        g.gridx = 1; gradePanel.add(lab2, g);
        g.gridx = 2; gradePanel.add(lab3, g);
        g.gridx = 3; gradePanel.add(prelim, g);
        g.gridx = 4; gradePanel.add(attendance, g);

        // Save / Back buttons (centered)
        JPanel gradeBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        saveBtn = new JButton("SAVE");
        backBtn = new JButton("BACK");
        gradeBtns.add(saveBtn);
        gradeBtns.add(backBtn);

        g.gridy = 2;
        g.gridx = 0;
        g.gridwidth = 5;
        gradePanel.add(gradeBtns, g);

        gradePanel.setVisible(false);

        /* ================= CRUD BUTTONS ================= */
        crudButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        viewBtn = new JButton("View");
        addBtn = new JButton("Add");
        updateBtn = new JButton("Update");
        deleteBtn = new JButton("Delete");

        crudButtons.add(viewBtn);
        crudButtons.add(addBtn);
        crudButtons.add(updateBtn);
        crudButtons.add(deleteBtn);

        /* ASSEMBLE */
        inputPanel.add(basic);
        inputPanel.add(Box.createVerticalStrut(8));
        inputPanel.add(gradePanel);
        inputPanel.add(Box.createVerticalStrut(6));
        inputPanel.add(crudButtons);

        wrapper.add(inputPanel, BorderLayout.CENTER);

        /* ================= TABLE ================= */
        String[] cols = {
                "ID", "First Name", "Last Name",
                "WORKLAB 1", "WORKLAB 2", "WORKLAB 3", "PRELIM", "ATTENDANCE"
        };

        model = new DefaultTableModel(cols, 0);
        masterModel = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadCSV();
        refreshTable();

        /* ================= ACTIONS ================= */

        viewBtn.addActionListener(e -> {
            if (allBasicEmpty()) {
                refreshTable();
                return;
            }
            if (!basicComplete()) {
                showBasicError();
                return;
            }
            viewStudent();
        });

        addBtn.addActionListener(e -> {
            if (!basicComplete()) {
                showBasicError();
                return;
            }
            isUpdateMode = false;
            showGradePanel();
        });

        updateBtn.addActionListener(e -> {
            if (!basicComplete()) {
                showBasicError();
                return;
            }
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this,
                        "View first and select a row to update.");
                return;
            }
            isUpdateMode = true;
            lab1.setText(model.getValueAt(row, 3).toString());
            lab2.setText(model.getValueAt(row, 4).toString());
            lab3.setText(model.getValueAt(row, 5).toString());
            prelim.setText(model.getValueAt(row, 6).toString());
            attendance.setText(model.getValueAt(row, 7).toString());
            showGradePanel();
        });

        saveBtn.addActionListener(e -> saveData());

        backBtn.addActionListener(e -> {
            clearGradeFields();
            hideGradePanel();
            refreshTable();
        });

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this,
                        "Please select a row to delete.");
                return;
            }
            masterModel.removeRow(row);
            refreshTable();
            saveCSV();
        });

        setVisible(true);
    }

    /* ================= HELPERS ================= */

    private void showGradePanel() {
        gradePanel.setVisible(true);
        crudButtons.setVisible(false);
        pack();
    }

    private void hideGradePanel() {
        gradePanel.setVisible(false);
        crudButtons.setVisible(true);
        pack();
    }

    private boolean basicComplete() {
        return !idField.getText().trim().isEmpty()
            && !fnField.getText().trim().isEmpty()
            && !lnField.getText().trim().isEmpty();
    }

    private boolean allBasicEmpty() {
        return idField.getText().trim().isEmpty()
            && fnField.getText().trim().isEmpty()
            && lnField.getText().trim().isEmpty();
    }

    private void showBasicError() {
        JOptionPane.showMessageDialog(this,
                "Please complete filling out Student ID, First Name, and Last Name.");
    }

    private boolean gradeIncomplete() {
        return lab1.getText().trim().isEmpty()
            || lab2.getText().trim().isEmpty()
            || lab3.getText().trim().isEmpty()
            || prelim.getText().trim().isEmpty()
            || attendance.getText().trim().isEmpty();
    }

    private void viewStudent() {
        model.setRowCount(0);
        for (int i = 0; i < masterModel.getRowCount(); i++) {
            if (masterModel.getValueAt(i, 0).equals(idField.getText()) &&
                masterModel.getValueAt(i, 1).equals(fnField.getText()) &&
                masterModel.getValueAt(i, 2).equals(lnField.getText())) {

                model.addRow(getRow(masterModel, i));
            }
        }
    }

    private void saveData() {
        if (gradeIncomplete()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all grade fields.");
            return;
        }

        if (isUpdateMode) {
            int row = table.getSelectedRow();
            masterModel.setValueAt(lab1.getText(), row, 3);
            masterModel.setValueAt(lab2.getText(), row, 4);
            masterModel.setValueAt(lab3.getText(), row, 5);
            masterModel.setValueAt(prelim.getText(), row, 6);
            masterModel.setValueAt(attendance.getText(), row, 7);
            JOptionPane.showMessageDialog(this, "Updated successfully!");
        } else {
            masterModel.insertRow(0, new String[]{
                    idField.getText(), fnField.getText(), lnField.getText(),
                    lab1.getText(), lab2.getText(), lab3.getText(),
                    prelim.getText(), attendance.getText()
            });
            JOptionPane.showMessageDialog(this, "Added successfully!");
        }

        saveCSV();
        refreshTable();
        clearGradeFields();
        hideGradePanel();
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (int i = 0; i < masterModel.getRowCount(); i++)
            model.addRow(getRow(masterModel, i));
    }

    private void loadCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null)
                masterModel.addRow(line.split(","));
        } catch (IOException ignored) {}
    }

    private void saveCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_FILE))) {
            pw.println("ID,FIRST,LAST,LAB1,LAB2,LAB3,PRELIM,ATTEND");
            for (int i = 0; i < masterModel.getRowCount(); i++) {
                for (int j = 0; j < 8; j++) {
                    pw.print(masterModel.getValueAt(i, j));
                    if (j < 7) pw.print(",");
                }
                pw.println();
            }
        } catch (IOException ignored) {}
    }

    private String[] getRow(DefaultTableModel m, int i) {
        String[] r = new String[8];
        for (int j = 0; j < 8; j++)
            r[j] = m.getValueAt(i, j).toString();
        return r;
    }

    private void clearGradeFields() {
        lab1.setText("");
        lab2.setText("");
        lab3.setText("");
        prelim.setText("");
        attendance.setText("");
    }

    public static void main(String[] args) {
        new PrelimLabExam();
    }
}
